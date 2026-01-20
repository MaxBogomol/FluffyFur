package mod.maxbogomol.fluffy_fur.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class FluffyFurServerConfig {
    public static ForgeConfigSpec.ConfigValue<Boolean>
            HARDCORE_SPECTATORS_GENERATE_CHUNKS, HARDCORE_SPECTATORS_GENERATE_CHUNKS_DEDICATED,
            TELEPORT_TO_SPAWN_SPECTATOR, TELEPORT_TO_DIMENSION_SPAWN_SPECTATOR, TELEPORT_TO_DIMENSION_SPECTATOR;

    public FluffyFurServerConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Hardcore").push("hardcore");
        HARDCORE_SPECTATORS_GENERATE_CHUNKS = builder.comment("Enables fix of spectatorsGenerateChunks game rule in hardcore.").define("hardcoreSpectatorsGenerateChunks", true);
        HARDCORE_SPECTATORS_GENERATE_CHUNKS_DEDICATED = builder.comment("Enables fix of spectatorsGenerateChunks game rule in hardcore on dedicated servers.").define("hardcoreSpectatorsGenerateChunksDedicated", false);
        builder.pop();

        builder.comment("Spectator").push("spectator");
        TELEPORT_TO_SPAWN_SPECTATOR = builder.comment("Enables teleport to spawn in spectator menu.").define("teleportToSpawnSpectator", true);
        TELEPORT_TO_DIMENSION_SPAWN_SPECTATOR = builder.comment("Enables teleport to dimension spawn in spectator menu.").define("teleportToDimensionSpawnSpectator", true);
        TELEPORT_TO_DIMENSION_SPECTATOR = builder.comment("Enables teleport to dimension in spectator menu.").define("teleportToDimensionSpectator", true);
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
