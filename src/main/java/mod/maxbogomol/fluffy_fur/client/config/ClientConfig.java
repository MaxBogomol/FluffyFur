package mod.maxbogomol.fluffy_fur.client.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ClientConfig {
    public static ForgeConfigSpec.ConfigValue<Boolean>
            BETTER_LAYERING,
            ITEM_PARTICLE, ITEM_GUI_PARTICLE,
            CUSTOM_PANORAMA, PANORAMA_BUTTON;
    public static ForgeConfigSpec.ConfigValue<Integer>
            PANORAMA_BUTTON_ROW, PANORAMA_BUTTON_ROW_X_OFFSET, PANORAMA_BUTTON_X_OFFSET, PANORAMA_BUTTON_Y_OFFSET;

    public ClientConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Graphics").push("graphics");
        BETTER_LAYERING = builder.comment("Enable better particle/effect layering.")
                .comment("Fixes particles and effects rendering behind clouds and weather.")
                .comment("NOTE: Does NOT work with fabulous graphics mode.")
                .define("betterLayering", true);

        builder.comment("Particles").push("particles");
        ITEM_PARTICLE = builder.comment("Enable dropping items particles.")
                .define("itemParticle", true);
        ITEM_GUI_PARTICLE = builder.comment("Enable items particles in GUI.")
                .define("itemGuiParticle", true);
        builder.pop();
        builder.pop();

        builder.comment("Menu").push("menu");
        CUSTOM_PANORAMA = builder.comment("Enable custom panorama.")
                .define("customPanorama", false);
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

    public static final ClientConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}
