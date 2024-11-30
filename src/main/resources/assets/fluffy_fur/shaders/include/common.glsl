#moj_import <fog.glsl>
#moj_import <light.glsl>

float linearizeDepth(float depth) {
    float near = 1.0;
    float far = 100.0;
    return (2.0 * near) / (far + near - depth * (far - near));
}

float fetch(samplerBuffer DataBuffer, int index) {
    return texelFetch(DataBuffer, index).r;
}

vec2 fetch2(samplerBuffer DataBuffer, int startIndex) {
    return vec2(fetch(DataBuffer, startIndex), fetch(DataBuffer, startIndex + 1));
}

vec3 fetch3(samplerBuffer DataBuffer, int startIndex) {
    return vec3(fetch(DataBuffer, startIndex), fetch(DataBuffer, startIndex + 1), fetch(DataBuffer, startIndex + 2));
}

vec4 fetch4(samplerBuffer DataBuffer, int startIndex) {
    return vec4(fetch(DataBuffer, startIndex), fetch(DataBuffer, startIndex + 1), fetch(DataBuffer, startIndex + 2), fetch(DataBuffer, startIndex + 3));
}

bool fetchBool(samplerBuffer DataBuffer, int index) {
    return fetch(DataBuffer, index) > 0.5;
}

float getDepth(sampler2D DepthBuffer, vec2 uv) {
    return texture(DepthBuffer, uv).r;
}

float getDepthProj(sampler2D DepthBuffer, vec4 uv) {
    return textureProj(DepthBuffer, uv).r;
}

float getDepthFromClipSpace(vec4 clipSpacePosition) {
    return (clipSpacePosition.z / clipSpacePosition.w + 1.0) / 2.0;
}

vec3 getWorldPos(sampler2D DepthBuffer, vec2 texCoord, mat4 invProjMat, mat4 invViewMat, vec3 cameraPos) {
    float z = getDepth(DepthBuffer, texCoord) * 2.0 - 1.0;
    vec4 clipSpacePosition = vec4(texCoord * 2.0 - 1.0, z, 1.0);
    vec4 viewSpacePosition = invProjMat * clipSpacePosition;
    viewSpacePosition /= viewSpacePosition.w;
    vec4 localSpacePosition = invViewMat * viewSpacePosition;
    return cameraPos + localSpacePosition.xyz;
}

vec3 viewSpaceFromDepth(float depth, vec2 texCoord, mat4 invProjMat) {
    float z = depth * 2.0 - 1.0;
    vec4 clipSpacePosition = vec4(texCoord * 2.0 - 1.0, z, 1.0);
    vec4 viewSpacePosition = invProjMat * clipSpacePosition;
    return viewSpacePosition.xyz / viewSpacePosition.w;
}

float applyDepthFade(float sceneDepthView, float pixelDepthView, float intensity) {
    float spacing = pixelDepthView - sceneDepthView;
    float fade = clamp(spacing / intensity, 0.0, 1.0);
    return fade;
}

vec4 projectionUVFromLocalSpace(vec4 position) {
    vec4 projection = position * 0.5;
    projection.xy = vec2(projection.x + projection.w, projection.y + projection.w);
    projection.zw = position.zw;
    return projection;
}

vec4 applyFog(vec4 color, float vertexDistance, float FogStart, float FogEnd, vec4 FogColor) {
    return linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}

float fogDistance(mat4 ModelViewMat, vec3 Position, int FogShape) {
    return fog_distance(ModelViewMat, Position, FogShape);
}

vec4 mixLight(vec3 lightDir0, vec3 lightDir1, vec3 normal, vec4 color) {
    return minecraft_mix_light(lightDir0, lightDir1, normal, color);
}

vec4 sampleLightmap(sampler2D lightMap, ivec2 uv) {
    return minecraft_sample_lightmap(lightMap, uv);
}

vec3 hue(float h) {
    float r = abs(h * 6.0 - 3.0) - 1.0;
    float g = 2.0 - abs(h * 6.0 - 2.0);
    float b = 2.0 - abs(h * 6.0 - 4.0);
    return clamp(vec3(r,g,b), 0.0, 1.0);
}

vec3 HSVtoRGB(vec3 hsv) {
    return ((hue(hsv.x) - 1.0) * hsv.y + 1.0) * hsv.z;
}

vec3 RGBtoHSV(vec3 rgb) {
    vec3 hsv = vec3(0.0);
    hsv.z = max(rgb.r, max(rgb.g, rgb.b));
    float min = min(rgb.r, min(rgb.g, rgb.b));
    float c = hsv.z - min;

    if (c != 0.0) {
        hsv.y = c / hsv.z;
        vec3 delta = (hsv.z - rgb) / c;
        delta.rgb -= delta.brg;
        delta.rg += vec2(2.0, 4.0);
        if (rgb.r >= hsv.z) {
            hsv.x = delta.b;
        } else if (rgb.g >= hsv.z) {
            hsv.x = delta.r;
        } else {
            hsv.x = delta.g;
        }
        hsv.x = fract(hsv.x / 6.0);
    }
    return hsv;
}