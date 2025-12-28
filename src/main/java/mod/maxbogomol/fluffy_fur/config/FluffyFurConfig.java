package mod.maxbogomol.fluffy_fur.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class FluffyFurConfig {
    public static ForgeConfigSpec.ConfigValue<Boolean>
            TRIDENT_LOYALTY_VOID,
            HARDCORE_SPECTATORS_GENERATE_CHUNKS, HARDCORE_SPECTATORS_GENERATE_CHUNKS_DEDICATED;
    public static ForgeConfigSpec.ConfigValue<Double>
            VOID_HEIGHT;

    public FluffyFurConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Misc").push("misc");
        TRIDENT_LOYALTY_VOID = builder.comment("Enables invulnerability of a trident enchanted to a loyality in the void.").define("tridentLoyalityVoid", true);
        VOID_HEIGHT = builder.comment("Height of void.").define("voidHeight", -32d);
        builder.pop();

        builder.comment("Hardcore").push("hardcore");
        HARDCORE_SPECTATORS_GENERATE_CHUNKS = builder.comment("Enables fix of spectatorsGenerateChunks game rule in hardcore.").define("hardcoreSpectatorsGenerateChunks", true);
        HARDCORE_SPECTATORS_GENERATE_CHUNKS_DEDICATED = builder.comment("Enables fix of spectatorsGenerateChunks game rule in hardcore on dedicated servers.").define("hardcoreSpectatorsGenerateChunksDedicated", false);
        builder.pop();
    }

    public static final FluffyFurConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<FluffyFurConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(FluffyFurConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}
