#version 150

#moj_import <fluffy_fur:common.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec2 vertexUV;
in vec4 vertexColor;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, vertexUV) * vertexColor * ColorModulator;
    if (color.a == 0.0) {
        discard;
    }
    fragColor = applyFog(color, vertexDistance, FogStart, FogEnd, FogColor);
}
