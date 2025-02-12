#version 150

#moj_import <fluffy_fur:common.glsl>

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec2 texCoord0;
in vec4 vertexColor;
in vec4 lightMapColor;

out vec4 fragColor;

void main() {
    vec4 color = vertexColor * ColorModulator;
    if (color.a < 0.001) {
        discard;
    }
    color *= lightMapColor;
    fragColor = applyFog(color, vertexDistance, FogStart, FogEnd, FogColor);
}