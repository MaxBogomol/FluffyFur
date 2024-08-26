package mod.maxbogomol.fluffy_fur.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ServerConfig {

    public ServerConfig(ForgeConfigSpec.Builder builder) {

    }

    public static final ServerConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}
