package mod.maxbogomol.fluffy_fur.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class FluffyFurClientConfig {
    public static ForgeConfigSpec.ConfigValue<Boolean>
            FABULOUS_WEATHER_FIX,
            ITEM_PARTICLE, ITEM_GUI_PARTICLE, BLOOD_PARTICLE, LIGHTNING_BOLT_EFFECT, EXPLOSION_EFFECT,
            RAIN_FOG_SHADER, RAIN_FOG_SHADER_NOISE, RAIN_FOG_SHADER_IGN,
            MENU_BUTTON, PANORAMA_LOGO, PANORAMA_MUSIC;
    public static ForgeConfigSpec.ConfigValue<Integer>
            MENU_BUTTON_ROW, MENU_BUTTON_ROW_X_OFFSET, MENU_BUTTON_X_OFFSET, MENU_BUTTON_Y_OFFSET;
    public static ForgeConfigSpec.ConfigValue<Double>
            SCREENSHAKE_INTENSITY,
            RAIN_FOG_SHADER_INTENSITY, THUNDER_FOG_SHADER_INTENSITY, THUNDER_FOG_FADE_SHADER_INTENSITY;
    public static ForgeConfigSpec.ConfigValue<String>
            PANORAMA;

    public FluffyFurClientConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Graphics").push("graphics");
        SCREENSHAKE_INTENSITY = builder.comment("Intensity of screenshake.").defineInRange("screenshakeIntensity", 1d, 0, 10d);
        FABULOUS_WEATHER_FIX = builder.comment("Enable weather render fix in fabulous graphics.").define("fabulousWeatherFix", true);

        builder.comment("Particles").push("particles");
        ITEM_PARTICLE = builder.comment("Enable dropping items particles.").define("itemParticle", true);
        ITEM_GUI_PARTICLE = builder.comment("Enable items particles in GUI.").define("itemGuiParticle", true);
        BLOOD_PARTICLE = builder.comment("Enable blood particles in case of damage.").define("bloodParticle", false);
        LIGHTNING_BOLT_EFFECT = builder.comment("Enable custom effect of lightning bolt.").define("lightningBoltEffect", true);
        EXPLOSION_EFFECT = builder.comment("Enable custom effect of explosion.").define("explosionEffect", true);
        builder.pop();

        builder.comment("Shaders").push("shaders");
        RAIN_FOG_SHADER = builder.comment("Enable Rain Fog effect post process shader.").define("rainFogShader", true);
        RAIN_FOG_SHADER_INTENSITY = builder.comment("Intensity of Rain Fog effect post process shader.").defineInRange("rainFogShaderIntensity", 0.3d, 0, 2d);
        THUNDER_FOG_SHADER_INTENSITY = builder.comment("Intensity of Thunder Fog effect post process shader.").defineInRange("thunderFogShaderIntensity", 0.1d, 0, 2d);
        THUNDER_FOG_FADE_SHADER_INTENSITY = builder.comment("Intensity of Thunder Fog fade effect post process shader.").defineInRange("thunderFogFadeShaderIntensity", 0.25d, 0, 1d);
        RAIN_FOG_SHADER_NOISE = builder.comment("Enable noise for Rain Fog effect.").define("rainFogShaderNoise", true);
        RAIN_FOG_SHADER_IGN = builder.comment("Enable Interleaved Gradient Noise for Rain Fog effect.").define("rainFogShaderIGN", true);
        builder.pop();
        builder.pop();

        builder.comment("Menu").push("menu");
        PANORAMA = builder.comment("Fluffy Fur Panorama.").define("panorama", "minecraft:vanilla");
        MENU_BUTTON = builder.comment("Enable Fluffy Fur menu button.").define("menuButton", true);
        MENU_BUTTON_ROW = builder.comment("Fluffy Fur menu button row.").defineInRange("menuButtonRow", 3, 0, 4);
        MENU_BUTTON_ROW_X_OFFSET = builder.comment("Fluffy Fur menu button X offset with row.").define("menuButtonRowXOffset", 4);
        MENU_BUTTON_X_OFFSET = builder.comment("Fluffy Fur menu button X offset.").define("menuButtonXOffset", 0);
        MENU_BUTTON_Y_OFFSET = builder.comment("Fluffy Fur menu button Y offset.").define("menuButtonYOffset", 0);
        PANORAMA_LOGO = builder.comment("Enable logo in Fluffy Fur Panorama.").define("panoramaLogo", true);
        PANORAMA_MUSIC = builder.comment("Enable music in Fluffy Fur Panorama.").define("panoramaMusic", true);
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
