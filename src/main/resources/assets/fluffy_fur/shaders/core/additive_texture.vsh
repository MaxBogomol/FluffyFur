#version 150

#moj_import <fluffy_fur:common.glsl>

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform int FogShape;

in vec3 Position;
in vec2 UV0;
in vec4 Color;

out float vertexDistance;
out vec2 vertexUV;
out vec4 vertexColor;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    vertexDistance = fogDistance(ModelViewMat, Position, FogShape);
    vertexUV = UV0;
    vertexColor = Color;
}
