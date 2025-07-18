#version 150

#moj_import <fluffy_fur:common.glsl>

uniform sampler2D Sampler2;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform int FogShape;

in vec3 Position;
in vec2 UV0;
in vec4 Color;
in ivec2 UV2;
in vec4 UVCap;
in float Time;
in float TimeOffset;
in float Amplifier;
in float Offset;

out float vertexDistance;
out vec2 vertexUV;
out vec4 vertexColor;
out vec4 vertexLight;
out vec4 vertexUVCap;
out float vertexTime;
out float vertexTimeOffset;
out float vertexAmplifier;
out float vertexOffset;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    vertexDistance = fogDistance(ModelViewMat, Position, FogShape);
    vertexUV = UV0;
    vertexColor = Color;
    vertexLight = sampleLightmap(Sampler2, UV2);

    vertexUVCap = UVCap;
    vertexTime = Time;
    vertexTimeOffset = TimeOffset;
    vertexAmplifier = Amplifier;
    vertexOffset = Offset;
}
