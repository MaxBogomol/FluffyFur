package mod.maxbogomol.fluffy_fur.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class FluffyFurClientConfig {
    public static ForgeConfigSpec.ConfigValue<Boolean>
            FABULOUS_WEATHER_FIX, FANCY_FOG, FANCY_FOG_SPHERE, DAYLIGHT_CLOUDS, DAYLIGHT_CLOUDS_IGNORE,
            ITEM_PARTICLE, ITEM_GUI_PARTICLE, BLOOD_PARTICLE, LIGHTNING_BOLT_EFFECT, EXPLOSION_EFFECT, EXPLOSION_EFFECT_SCREENSHAKE,
            RAIN_FOG_SHADER, RAIN_FOG_SHADER_NOISE, RAIN_FOG_SHADER_IGN,
            ENHANCED_MENU, MENU_BUTTON, PANORAMA_LOGO, PANORAMA_MUSIC;
    public static ForgeConfigSpec.ConfigValue<Integer>
            MENU_BUTTON_ROW, MENU_BUTTON_ROW_X_OFFSET, MENU_BUTTON_X_OFFSET, MENU_BUTTON_Y_OFFSET;
    public static ForgeConfigSpec.ConfigValue<Double>
            SCREENSHAKE_INTENSITY, FANCY_FOG_INTENSITY,
            RAIN_FOG_SHADER_INTENSITY, THUNDER_FOG_SHADER_INTENSITY, THUNDER_FOG_FADE_SHADER_INTENSITY;
    public static ForgeConfigSpec.ConfigValue<String>
            PANORAMA;

    public FluffyFurClientConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Graphics").translation("config.client.fluffy_fur.graphics").push("graphics");
        SCREENSHAKE_INTENSITY = builder.comment("Intensity of screenshake.").translation("config.client.fluffy_fur.graphics.screenshakeIntensity").defineInRange("screenshakeIntensity", 1d, 0, 10d);
        FABULOUS_WEATHER_FIX = builder.comment("Enable weather render fix in fabulous graphics.").translation("config.client.fluffy_fur.graphics.fabulousWeatherFix").define("fabulousWeatherFix", true);
        FANCY_FOG = builder.comment("Enable fancy fog.").translation("config.client.fluffy_fur.graphics.fancyFog").define("fancyFog", true);
        FANCY_FOG_INTENSITY = builder.comment("Intensity of fancy fog.").translation("config.client.fluffy_fur.graphics.fancyFogntensity").defineInRange("fancyFogntensity", 2d, 0f, 100d);
        FANCY_FOG_SPHERE = builder.comment("Enable sphere shape of fancy fog.").translation("config.client.fluffy_fur.graphics.fancyFogSphere").define("fancyFogSphere", false);
        DAYLIGHT_CLOUDS = builder.comment("Enable dependence of clouds on day time.").translation("config.client.fluffy_fur.graphics.daylightClouds").define("daylightClouds", true);
        DAYLIGHT_CLOUDS_IGNORE = builder.comment("Enable dependence of clouds on day time when daylight cycle disabled.").translation("config.client.fluffy_fur.graphics.daylightCloudsIgnore").define("daylightCloudsIgnore", false);

        builder.comment("Particles").translation("config.client.fluffy_fur.graphics.particles").push("particles");
        ITEM_PARTICLE = builder.comment("Enable dropping items particles.").translation("config.client.fluffy_fur.graphics.particles.itemParticle").define("itemParticle", true);
        ITEM_GUI_PARTICLE = builder.comment("Enable items particles in GUI.").translation("config.client.fluffy_fur.graphics.particles.itemGuiParticle").define("itemGuiParticle", true);
        BLOOD_PARTICLE = builder.comment("Enable blood particles in case of damage.").translation("config.client.fluffy_fur.graphics.particles.bloodParticle").define("bloodParticle", false);
        LIGHTNING_BOLT_EFFECT = builder.comment("Enable custom effect of lightning bolt.").translation("config.client.fluffy_fur.graphics.particles.lightningBoltEffect").define("lightningBoltEffect", true);
        EXPLOSION_EFFECT = builder.comment("Enable custom effect of explosion.").translation("config.client.fluffy_fur.graphics.particles.explosionEffect").define("explosionEffect", true);
        EXPLOSION_EFFECT_SCREENSHAKE = builder.comment("Enable screenshake for custom effect of explosion.").translation("config.client.fluffy_fur.graphics.particles.explosionEffectScreenshake").define("explosionEffectScreenshake", true);
        builder.pop();

        builder.comment("Shaders").translation("config.client.fluffy_fur.graphics.shaders").push("shaders");
        RAIN_FOG_SHADER = builder.comment("Enable Rain Fog effect post process shader.").translation("config.client.fluffy_fur.graphics.shaders.rainFogShader").define("rainFogShader", true);
        RAIN_FOG_SHADER_INTENSITY = builder.comment("Intensity of Rain Fog effect post process shader.").translation("config.client.fluffy_fur.graphics.shaders.rainFogShaderIntensity").defineInRange("rainFogShaderIntensity", 0.3d, 0, 2d);
        THUNDER_FOG_SHADER_INTENSITY = builder.comment("Intensity of Thunder Fog effect post process shader.").translation("config.client.fluffy_fur.graphics.shaders.thunderFogShaderIntensity").defineInRange("thunderFogShaderIntensity", 0.1d, 0, 2d);
        THUNDER_FOG_FADE_SHADER_INTENSITY = builder.comment("Intensity of Thunder Fog fade effect post process shader.").translation("config.client.fluffy_fur.graphics.shaders.thunderFogFadeShaderIntensity").defineInRange("thunderFogFadeShaderIntensity", 0.25d, 0, 1d);
        RAIN_FOG_SHADER_NOISE = builder.comment("Enable noise for Rain Fog effect.").translation("config.client.fluffy_fur.graphics.shaders.rainFogShaderNoise").define("rainFogShaderNoise", true);
        RAIN_FOG_SHADER_IGN = builder.comment("Enable Interleaved Gradient Noise for Rain Fog effect.").translation("config.client.fluffy_fur.graphics.shaders.rainFogShaderIGN").define("rainFogShaderIGN", true);
        builder.pop();
        builder.pop();

        builder.comment("Menu").translation("config.client.fluffy_fur.menu").push("menu");
        ENHANCED_MENU = builder.comment(".").translation("config.client.fluffy_fur.menu.enhancedMenu").define("enhancedMenu", true);
        PANORAMA = builder.comment("Fluffy Fur Panorama.").translation("config.client.fluffy_fur.menu.panorama").define("panorama", "minecraft:vanilla");
        PANORAMA_LOGO = builder.comment("Enable logo in Fluffy Fur Panorama.").translation("config.client.fluffy_fur.menu.panoramaLogo").define("panoramaLogo", true);
        PANORAMA_MUSIC = builder.comment("Enable music in Fluffy Fur Panorama.").translation("config.client.fluffy_fur.menu.panoramaMusic").define("panoramaMusic", true);
        MENU_BUTTON = builder.comment("Enable Fluffy Fur menu button.").translation("config.client.fluffy_fur.menu.menuButton").define("menuButton", true);
        MENU_BUTTON_ROW = builder.comment("Fluffy Fur menu button row.").translation("config.client.fluffy_fur.menu.menuButtonRow").defineInRange("menuButtonRow", 3, 0, 4);
        MENU_BUTTON_ROW_X_OFFSET = builder.comment("Fluffy Fur menu button X offset with row.").translation("config.client.fluffy_fur.menu.menuButtonRowXOffset").define("menuButtonRowXOffset", 4);
        MENU_BUTTON_X_OFFSET = builder.comment("Fluffy Fur menu button X offset.").translation("config.client.fluffy_fur.menu.menuButtonXOffset").define("menuButtonXOffset", 0);
        MENU_BUTTON_Y_OFFSET = builder.comment("Fluffy Fur menu button Y offset.").translation("config.client.fluffy_fur.menu.menuButtonYOffset").define("menuButtonYOffset", 0);
        builder.pop();
    }

    public static final FluffyFurClientConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<FluffyFurClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(FluffyFurClientConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}
