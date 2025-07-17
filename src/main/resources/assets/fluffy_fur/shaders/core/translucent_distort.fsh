#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;

in vec4 vertexColor;
in float vertexDistance;
in vec2 texCoord0;
in vec4 lightMapColor;
in vec4 uV;
in float timeScale;
in float amplifier;
in float offsetScale;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform float GameTime;
uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out vec4 fragColor;

void main() {
    vec2 uv = texCoord0;
    vec4 cap = uV;
    float time = -GameTime * timeScale;

    vec2 uCap = vec2(cap.x, cap.z);
    vec2 vCap = vec2(cap.y, cap.w);
    vec2 uvSize = vec2(uCap.y - uCap.x, vCap.y - vCap.x);
    vec2 noramalUV = vec2(uv.x - uCap.x, uv.y - vCap.x) / uvSize;

    vec2 offset = sin(noramalUV.yx * amplifier + time);
    vec2 intensity = sin(noramalUV.xy * amplifier);
    offset = offset * intensity * offsetScale;
    noramalUV += offset;

    uv = cap.xy + (noramalUV * uvSize);

    vec4 color = texture(Sampler0, uv) * vertexColor * ColorModulator;
    if (color.a == 0.0 || uv.x < uCap.x || uv.x > uCap.y || uv.y < vCap.x || uv.y > vCap.y) discard;

    color *= lightMapColor;
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}