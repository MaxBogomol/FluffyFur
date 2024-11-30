#version 150

#moj_import <fluffy_fur:common.glsl>

uniform sampler2D DiffuseSampler;
uniform sampler2D MainDepthSampler;

uniform samplerBuffer DataBuffer;
uniform int InstanceCount;
uniform mat4 invViewMat;
uniform mat4 invProjMat;
uniform vec3 cameraPos;

in vec2 texCoord;
out vec4 fragColor;

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