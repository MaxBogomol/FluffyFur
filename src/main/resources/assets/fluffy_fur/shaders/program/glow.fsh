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