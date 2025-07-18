#version 150

uniform sampler2D DiffuseSampler;

uniform vec4 ColorModulate;

in vec2 vertexUV;

out vec4 fragColor;

void main(){
    fragColor = texture(DiffuseSampler, vertexUV) * ColorModulate;
}
