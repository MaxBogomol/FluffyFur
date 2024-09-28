package mod.maxbogomol.fluffy_fur.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class FluffyFurClientConfig {
    public static ForgeConfigSpec.ConfigValue<Boolean>
            ITEM_PARTICLE, ITEM_GUI_PARTICLE, BLOOD_PARTICLE,
            PANORAMA_BUTTON;
    public static ForgeConfigSpec.ConfigValue<Integer>
            PANORAMA_BUTTON_ROW, PANORAMA_BUTTON_ROW_X_OFFSET, PANORAMA_BUTTON_X_OFFSET, PANORAMA_BUTTON_Y_OFFSET;
    public static ForgeConfigSpec.ConfigValue<Double>
            SCREENSHAKE_INTENSITY;
    public static ForgeConfigSpec.ConfigValue<String>
            CUSTOM_PANORAMA;

    public FluffyFurClientConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Graphics").push("graphics");
        SCREENSHAKE_INTENSITY = builder.comment("Intensity of screenshake.")
                .defineInRange("screenshakeIntensity", 1d, 0, 10d);

        builder.comment("Particles").push("particles");
        ITEM_PARTICLE = builder.comment("Enable dropping items particles.")
                .define("itemParticle", true);
        ITEM_GUI_PARTICLE = builder.comment("Enable items particles in GUI.")
                .define("itemGuiParticle", true);
        BLOOD_PARTICLE = builder.comment("Enable blood particles in case of damage.")
                .define("bloodParticle", false);
        builder.pop();
        builder.pop();

        builder.comment("Menu").push("menu");
        CUSTOM_PANORAMA = builder.comment("Enable custom panorama button.")
                .define("panorama", "minecraft:vanilla");
        PANORAMA_BUTTON = builder.comment("Enable custom panorama button.")
                .define("panoramaButton", true);
        PANORAMA_BUTTON_ROW = builder.comment("Custom panorama button row.")
                .defineInRange("panoramaButtonRow", 3, 0, 4);
        PANORAMA_BUTTON_ROW_X_OFFSET = builder.comment("Custom panorama button X offset with row.")
                .define("panoramaButtonRowXOffset", 4);
        PANORAMA_BUTTON_X_OFFSET = builder.comment("Custom panorama button X offset.")
                .define("panoramaButtonXOffset", 0);
        PANORAMA_BUTTON_Y_OFFSET = builder.comment("Custom panorama button Y offset.")
                .define("panoramaButtonYOffset", 0);
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
