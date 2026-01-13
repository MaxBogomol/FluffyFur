package mod.maxbogomol.fluffy_fur.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class FluffyFurConfig {
    public static ForgeConfigSpec.ConfigValue<Boolean>
            LEADER_ZOMBIE_FIX;
    public static ForgeConfigSpec.ConfigValue<Boolean>
            TRIDENT_LOYALTY_VOID;
    public static ForgeConfigSpec.ConfigValue<Double>
            VOID_HEIGHT;

    public FluffyFurConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Misc").push("misc");
        TRIDENT_LOYALTY_VOID = builder.comment("Enables invulnerability of a trident enchanted to a loyalty in the void.").define("tridentLoyaltyVoid", true);
        VOID_HEIGHT = builder.comment("Height of void.").define("voidHeight", -32d);
        LEADER_ZOMBIE_FIX = builder.comment("Enable leader zombie health fix.").define("leaderZombieFix", true);
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
