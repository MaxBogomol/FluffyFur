#version 150

#moj_import <fluffy_fur:common.glsl>

uniform sampler2D DiffuseSampler;
uniform sampler2D MainDepthSampler;

uniform mat4 invViewMat;
uniform mat4 invProjMat;
uniform float fade;

in vec2 vertexUV;

out vec4 fragColor;

void main() {
    vec4 diffuseColor = texture(DiffuseSampler, vertexUV);
    vec3 cameraPos = vec3(0.0, 0.0, 0.0);
    vec3 worldPos = getWorldPos(MainDepthSampler, vertexUV, invProjMat, invViewMat, cameraPos);

    fragColor = diffuseColor;
    float distance = length(worldPos);
    float d = 1.0 - (distance / 250.0);
    vec3 c = vec3(d, d, d);
    vec3 f = mix(diffuseColor.rgb, c, fade);
    fragColor = vec4(f, 1.0);
}