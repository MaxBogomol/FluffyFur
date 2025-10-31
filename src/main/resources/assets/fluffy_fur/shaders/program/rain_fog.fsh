#version 150

uniform sampler2D DiffuseSampler;

uniform vec2 ScreenSize;

uniform float rainStrength;
uniform float thunderStrength;
uniform float rainIntensity;
uniform float thunderIntensity;
uniform float totalTicks;

in vec2 vertexUV;

out vec4 fragColor;

const float ZOOM = 1.;
const int OCTAVES = 4;
const float INTENSITY = 5.;

const vec3 COLOR = vec3(0.42, 0.40, 0.47);
const vec3 BG = vec3(0.0, 0.0, 0.0);

float random(vec2 st) {
    return fract(sin(dot(st.xy, vec2(12.9818,79.279)))*43758.5453123);
}

vec2 random2(vec2 st){
    st = vec2( dot(st,vec2(127.1,311.7)), dot(st,vec2(269.5,183.3)) );
    return -1.0 + 2.0 * fract(sin(st) * 7.);
}

float noise(vec2 st) {
    vec2 i = floor(st);
    vec2 f = fract(st);

    vec2 u = f*f*(3.0-2.0*f);

    return mix( mix( dot( random2(i + vec2(0.0,0.0) ), f - vec2(0.0,0.0) ),
                     dot( random2(i + vec2(1.0,0.0) ), f - vec2(1.0,0.0) ), u.x),
                mix( dot( random2(i + vec2(0.0,1.0) ), f - vec2(0.0,1.0) ),
                     dot( random2(i + vec2(1.0,1.0) ), f - vec2(1.0,1.0) ), u.x), u.y);
}

float fractal_brownian_motion(vec2 coord) {
    float value = 0.0;
    float scale = 0.2;
    for (int i = 0; i < 4; i++) {
        value += noise(coord) * scale;
        coord *= 2.0;
        scale *= 0.5;
    }
    return value + 0.2;
}

float interleaved_gradient_noise(vec2 p) {
    vec3 magic = vec3( 0.06711056f, 0.00583715f, 52.9829189f );
    return fract(magic.z * fract(dot(vec2(p), magic.xy)));
}

void main() {
    float time = totalTicks / 20;
    vec2 st = vertexUV.xy * vec2(ScreenSize.x / 3, ScreenSize.y / 3) + vec2(time * 50.0, time * 10.0);
    st *= 0.0025;
    vec2 pos = vec2(st);
    vec2 motion = vec2(fractal_brownian_motion(pos + vec2(time * -0.5, time * -0.3)));
    float final = fractal_brownian_motion(pos + motion) * INTENSITY;
    final = final + (interleaved_gradient_noise(vertexUV * ScreenSize) * min(INTENSITY, 1) * 0.05f);
    final = max(final, 0);

    float a = rainIntensity * rainStrength * (final / 255.0);
    float r = mix(texture(DiffuseSampler, vertexUV).r, 255.0 / 3.0, a);
    float g = mix(texture(DiffuseSampler, vertexUV).g, 255.0 / 3.0, a);
    float b = mix(texture(DiffuseSampler, vertexUV).b, 255.0 / 3.0, a);

    a = thunderIntensity * thunderStrength;
    r = mix(r, 0.0, a);
    g = mix(g, 0.0, a);
    b = mix(b, 0.0, a);

    fragColor = vec4(r, g, b, 1.0);
}
