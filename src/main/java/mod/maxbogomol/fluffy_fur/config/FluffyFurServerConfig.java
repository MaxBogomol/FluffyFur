package mod.maxbogomol.fluffy_fur.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class FluffyFurServerConfig {
    public static ForgeConfigSpec.ConfigValue<Boolean>
            HARDCORE_SPECTATORS_GENERATE_CHUNKS, HARDCORE_SPECTATORS_GENERATE_CHUNKS_DEDICATED;

    public FluffyFurServerConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Hardcore").push("hardcore");
        HARDCORE_SPECTATORS_GENERATE_CHUNKS = builder.comment("Enables fix of spectatorsGenerateChunks game rule in hardcore.").define("hardcoreSpectatorsGenerateChunks", true);
        HARDCORE_SPECTATORS_GENERATE_CHUNKS_DEDICATED = builder.comment("Enables fix of spectatorsGenerateChunks game rule in hardcore on dedicated servers.").define("hardcoreSpectatorsGenerateChunksDedicated", false);
        builder.pop();
    }

    public static final FluffyFurServerConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<FluffyFurServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(FluffyFurServerConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}
