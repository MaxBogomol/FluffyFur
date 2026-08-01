package mod.maxbogomol.fluffy_fur.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class FluffyFurClientConfig {
    public static ForgeConfigSpec.ConfigValue<Boolean>
            FABULOUS_WEATHER_FIX, FANCY_FOG, FANCY_FOG_SPHERE, DAYLIGHT_CLOUDS, DAYLIGHT_CLOUDS_IGNORE,
            ITEM_PARTICLE, ITEM_GUI_PARTICLE, BLOOD_PARTICLE, LIGHTNING_BOLT_EFFECT, LIGHTNING_BOLT_EFFECT_LIGHT, LIGHTNING_BOLT_EFFECT_SCREENSHAKE, EXPLOSION_EFFECT, EXPLOSION_EFFECT_SCREENSHAKE,
            RAIN_FOG_OVERLAY, RAIN_FOG_OVERLAY_NOISE, RAIN_FOG_OVERLAY_IGN,
            ENHANCED_MENU, VANILLA_PANORAMA_CONFLICT_PREVENTION, PANORAMA_LOGO, PANORAMA_MUSIC,
            MENU_BUTTON, PAUSE_BUTTON, PACK_BUTTON;
    public static ForgeConfigSpec.ConfigValue<Integer>
            MENU_BUTTON_ROW, MENU_BUTTON_ROW_X_OFFSET, MENU_BUTTON_X_OFFSET, MENU_BUTTON_Y_OFFSET,
            PAUSE_BUTTON_ROW, PAUSE_BUTTON_ROW_X_OFFSET, PAUSE_BUTTON_X_OFFSET, PAUSE_BUTTON_Y_OFFSET,
            PACK_BUTTON_ROW_X_OFFSET, PACK_BUTTON_X_OFFSET, PACK_BUTTON_Y_OFFSET;
    public static ForgeConfigSpec.ConfigValue<Double>
            SCREENSHAKE_INTENSITY, FANCY_FOG_INTENSITY,
            RAIN_FOG_OVERLAY_INTENSITY, THUNDER_FOG_OVERLAY_INTENSITY, THUNDER_FOG_FADE_OVERLAY_INTENSITY;

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
        LIGHTNING_BOLT_EFFECT_LIGHT = builder.comment("Enable light for custom effect of lightning bolt.").translation("config.client.fluffy_fur.graphics.particles.lightningBoltEffectLight").define("lightningBoltEffectLight", true);
        LIGHTNING_BOLT_EFFECT_SCREENSHAKE = builder.comment("Enable screenshake for custom effect of lightning bolt.").translation("config.client.fluffy_fur.graphics.particles.lightningBoltEffectScreenshake").define("lightningBoltEffectScreenshake", true);
        EXPLOSION_EFFECT = builder.comment("Enable custom effect of explosion.").translation("config.client.fluffy_fur.graphics.particles.explosionEffect").define("explosionEffect", true);
        EXPLOSION_EFFECT_SCREENSHAKE = builder.comment("Enable screenshake for custom effect of explosion.").translation("config.client.fluffy_fur.graphics.particles.explosionEffectScreenshake").define("explosionEffectScreenshake", true);
        builder.pop();
        builder.pop();

        builder.comment("Overlay").translation("config.client.fluffy_fur.graphics.overlay").push("overlay");
        builder.comment("Rain Fog").push("rainFog");
        RAIN_FOG_OVERLAY = builder.comment("Enable Rain Fog overlay effect.").translation("config.client.fluffy_fur.overlay.rainFog.rainFogOverlay").define("rainFogOverlay", true);
        RAIN_FOG_OVERLAY_INTENSITY = builder.comment("Intensity of Rain Fog overlay effect.").translation("config.client.fluffy_fur.overlay.rainFog.rainFogOverlayIntensity").defineInRange("rainFogOverlayIntensity", 0.3d, 0, 2d);
        THUNDER_FOG_OVERLAY_INTENSITY = builder.comment("Intensity of Thunder Fog overlay effect.").translation("config.client.fluffy_fur.overlay.rainFog.thunderFogOverlayIntensity").defineInRange("thunderFogOverlayIntensity", 0.1d, 0, 2d);
        THUNDER_FOG_FADE_OVERLAY_INTENSITY = builder.comment("Intensity of Thunder Fog fade overlay effect.").translation("config.client.fluffy_fur.overlay.rainFog.thunderFogFadeOverlayIntensity").defineInRange("thunderFogFadeOverlayIntensity", 0.25d, 0, 1d);
        RAIN_FOG_OVERLAY_NOISE = builder.comment("Enable noise for Rain Fog overlay effect.").translation("config.client.fluffy_fur.overlay.rainFog.rainFogOverlayNoise").define("rainFogOverlayNoise", true);
        RAIN_FOG_OVERLAY_IGN = builder.comment("Enable Interleaved Gradient Noise for Rain Fog overlay effect.").translation("config.client.fluffy_fur.overlay.rainFog.rainFogOverlayIGN").define("rainFogOverlayIGN", true);
        builder.pop();
        builder.pop();

        builder.comment("Menu").translation("config.client.fluffy_fur.menu").push("menu");
        ENHANCED_MENU = builder.comment(".").translation("config.client.fluffy_fur.menu.enhancedMenu").define("enhancedMenu", true);
        VANILLA_PANORAMA_CONFLICT_PREVENTION = builder.comment(".").translation("config.client.fluffy_fur.menu.vanillaPanoramaConflictPrevention").define("vanillaPanoramaConflictPrevention", true);
        PANORAMA_LOGO = builder.comment("Enable logo in Fluffy Fur Panorama.").translation("config.client.fluffy_fur.menu.panoramaLogo").define("panoramaLogo", true);
        PANORAMA_MUSIC = builder.comment("Enable music in Fluffy Fur Panorama.").translation("config.client.fluffy_fur.menu.panoramaMusic").define("panoramaMusic", true);
        MENU_BUTTON = builder.comment("Enable Fluffy Fur menu button.").translation("config.client.fluffy_fur.menu.menuButton").define("menuButton", true);
        MENU_BUTTON_ROW = builder.comment("Fluffy Fur menu button row.").translation("config.client.fluffy_fur.menu.menuButtonRow").defineInRange("menuButtonRow", 3, 1, 4);
        MENU_BUTTON_ROW_X_OFFSET = builder.comment("Fluffy Fur menu button X offset with row.").translation("config.client.fluffy_fur.menu.menuButtonRowXOffset").define("menuButtonRowXOffset", 4);
        MENU_BUTTON_X_OFFSET = builder.comment("Fluffy Fur menu button X offset.").translation("config.client.fluffy_fur.menu.menuButtonXOffset").define("menuButtonXOffset", 0);
        MENU_BUTTON_Y_OFFSET = builder.comment("Fluffy Fur menu button Y offset.").translation("config.client.fluffy_fur.menu.menuButtonYOffset").define("menuButtonYOffset", 0);
        PAUSE_BUTTON = builder.comment("Enable Fluffy Fur pause screen button.").translation("config.client.fluffy_fur.menu.pauseScreenButton").define("pauseScreenButton", true);
        PAUSE_BUTTON_ROW = builder.comment("Fluffy Fur pause screen button row.").translation("config.client.fluffy_fur.menu.pauseScreenButtonRow").defineInRange("pauseScreenButtonRow", 4, 1, 6);
        PAUSE_BUTTON_ROW_X_OFFSET = builder.comment("Fluffy Fur pause screen button X offset with row.").translation("config.client.fluffy_fur.menu.pauseScreenButtonRowXOffset").define("pauseScreenButtonRowXOffset", 4);
        PAUSE_BUTTON_X_OFFSET = builder.comment("Fluffy Fur pause screen button X offset.").translation("config.client.fluffy_fur.menu.pauseScreenButtonXOffset").define("pauseScreenButtonXOffset", 0);
        PAUSE_BUTTON_Y_OFFSET = builder.comment("Fluffy Fur pause screen button Y offset.").translation("config.client.fluffy_fur.menu.pauseScreenButtonYOffset").define("pauseScreenButtonYOffset", 0);
        PACK_BUTTON = builder.comment("Enable custom pack selection button.").translation("config.client.fluffy_fur.menu.packButton").define("packButton", true);
        PACK_BUTTON_ROW_X_OFFSET = builder.comment("Custom pack selection button X offset with row.").translation("config.client.fluffy_fur.menu.packButtonRowXOffset").define("packButtonRowXOffset", -4);
        PACK_BUTTON_X_OFFSET = builder.comment("Custom pack selection button X offset.").translation("config.client.fluffy_fur.menu.packButtonXOffset").define("packButtonXOffset", 0);
        PACK_BUTTON_Y_OFFSET = builder.comment("Custom pack selection button Y offset.").translation("config.client.fluffy_fur.menu.packButtonYOffset").define("packButtonYOffset", 0);
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
