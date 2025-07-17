#version 150

#moj_import <fluffy_fur:common.glsl>

in vec3 Position;
in vec2 UV0;
in vec4 Color;
in ivec2 UV2;
in vec4 UVCap;
in float TimeScale;
in float Amplifier;
in float Offset;

uniform sampler2D Sampler2;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform int FogShape;

out float vertexDistance;
out vec2 texCoord0;
out vec4 vertexColor;
out vec4 lightMapColor;
out vec4 uV;
out float timeScale;
out float amplifier;
out float offsetScale;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    vertexDistance = fogDistance(ModelViewMat, Position, FogShape);
    texCoord0 = UV0;
    vertexColor = Color;
    lightMapColor = sampleLightmap(Sampler2, UV2);

    uV = UVCap;
    timeScale = TimeScale;
    amplifier = Amplifier;
    offsetScale = Offset;
}
