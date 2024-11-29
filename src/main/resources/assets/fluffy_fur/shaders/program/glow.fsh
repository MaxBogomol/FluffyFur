#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D MainDepthSampler;

uniform samplerBuffer DataBuffer;
uniform int InstanceCount;

uniform mat4 invViewMat;
uniform mat4 invProjMat;
uniform vec3 cameraPos;

in vec2 texCoord;
out vec4 fragColor;

float getDepth(sampler2D DepthBuffer, vec2 uv) {
    return texture(DepthBuffer, uv).r;
}

vec3 getWorldPos(sampler2D DepthBuffer, vec2 texCoord, mat4 invProjMat, mat4 invViewMat, vec3 cameraPos) {
    float z = getDepth(DepthBuffer, texCoord) * 2.0 - 1.0;
    vec4 clipSpacePosition = vec4(texCoord * 2.0 - 1.0, z, 1.0);
    vec4 viewSpacePosition = invProjMat * clipSpacePosition;
    viewSpacePosition /= viewSpacePosition.w;
    vec4 localSpacePosition = invViewMat * viewSpacePosition;
    return cameraPos + localSpacePosition.xyz;
}

float dst(vec3 start, vec3 end, vec3 pos, float maxDistance) {
    vec3 a = start - end;
    vec3 b = start - pos;
    float d = length(cross(a, b)) / length(a);
    vec3 m = mix(start, end, 0.5);
    float s = length(start - end) / 2;
    float t = length(m - pos);
    if (t > s + (maxDistance / 4)) {
        d = length(pos - start);
        float d2 = length(pos - end);
        if (d2 < d) d = d2;
    }
    return d;
}

float fetch(samplerBuffer DataBuffer, int index) {
    return texelFetch(DataBuffer, index).r;
}

vec2 fetch2(samplerBuffer DataBuffer, int startIndex) {
    return vec2(fetch(DataBuffer, startIndex), fetch(DataBuffer, startIndex + 1));
}

vec3 fetch3(samplerBuffer DataBuffer, int startIndex) {
    return vec3(fetch(DataBuffer, startIndex), fetch(DataBuffer, startIndex + 1), fetch(DataBuffer, startIndex + 2));
}

vec4 fetch4(samplerBuffer DataBuffer, int startIndex) {
    return vec4(fetch(DataBuffer, startIndex), fetch(DataBuffer, startIndex + 1), fetch(DataBuffer, startIndex + 2), fetch(DataBuffer, startIndex + 3));
}

bool fetchBool(samplerBuffer DataBuffer, int index) {
    return fetch(DataBuffer, index) > 0.5;
}

void main() {
    vec4 diffuseColor = texture(DiffuseSampler, texCoord);
    vec3 worldPos = getWorldPos(MainDepthSampler, texCoord, invProjMat, invViewMat, cameraPos);

    fragColor = diffuseColor;
    for (int instance = 0; instance < InstanceCount; instance++) {
        int index = instance * 11;
        vec3 center = fetch3(DataBuffer, index);
        vec3 color = fetch3(DataBuffer, index + 3);
        float radius = fetch(DataBuffer, index + 6);
        float intensity = fetch(DataBuffer, index + 7);
        float fade = fetch(DataBuffer, index + 8);

        float distance = length(worldPos - center);
        if (distance <= radius) {
            float falloff = (1.0 - clamp(distance / radius, 0.0, 1.0)) * fade * intensity;
            fragColor.rgb *= (color * falloff + 1.0);
        }
    }
}