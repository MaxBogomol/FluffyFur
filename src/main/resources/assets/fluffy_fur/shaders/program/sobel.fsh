#version 150

uniform sampler2D DiffuseSampler;

in vec2 vertexUV;
in vec2 oneTexel;

out vec4 fragColor;

void main(){
    vec4 center = texture(DiffuseSampler, vertexUV);
    vec4 left   = texture(DiffuseSampler, vertexUV - vec2(oneTexel.x, 0.0));
    vec4 right  = texture(DiffuseSampler, vertexUV + vec2(oneTexel.x, 0.0));
    vec4 up     = texture(DiffuseSampler, vertexUV - vec2(0.0, oneTexel.y));
    vec4 down   = texture(DiffuseSampler, vertexUV + vec2(0.0, oneTexel.y));
    vec4 leftDiff  = center - left;
    vec4 rightDiff = center - right;
    vec4 upDiff    = center - up;
    vec4 downDiff  = center - down;
    vec4 total = clamp(leftDiff + rightDiff + upDiff + downDiff, 0.0, 1.0);
    fragColor = vec4(total.rgb, 1.0);
}
