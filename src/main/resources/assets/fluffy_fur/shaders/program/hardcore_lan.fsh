#version 150

uniform sampler2D DiffuseSampler;

uniform float intensity;

in vec2 vertexUV;

out vec4 fragColor;

void main() {
    float a = intensity;
    float r = mix(texture(DiffuseSampler, vertexUV).r, 1.0, a);
    float g = mix(texture(DiffuseSampler, vertexUV).g, 0.0, a);
    float b = mix(texture(DiffuseSampler, vertexUV).b, 0.0, a);

    fragColor = vec4(r, g, b, 1.0);
    if (a == 1.0) {
        fragColor = vec4(1.0, 1.0, 1.0, 1.0);
    }
}
