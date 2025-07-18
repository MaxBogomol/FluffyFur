#version 150

#moj_import <fluffy_fur:common.glsl>

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform int FogShape;

in vec3 Position;
in vec4 Color;

out float vertexDistance;
out vec4 vertexColor;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    vertexDistance = fogDistance(ModelViewMat, Position, FogShape);
    vertexColor = Color;
}
