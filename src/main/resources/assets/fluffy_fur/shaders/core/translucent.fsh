#version 150

#moj_import <fluffy_fur:common.glsl>

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec4 vertexColor;
in vec4 vertexLight;

out vec4 fragColor;

void main() {
    vec4 color = vertexColor * ColorModulator;
    if (color.a == 0.0) {
        discard;
    }
    color *= vertexLight;
    fragColor = applyFog(color, vertexDistance, FogStart, FogEnd, FogColor);
}