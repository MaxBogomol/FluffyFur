package mod.maxbogomol.fluffy_fur.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class FluffyFurConfig {
    public static ForgeConfigSpec.ConfigValue<Boolean>
            ITEM_PARTICLE;
    public static ForgeConfigSpec.ConfigValue<Integer>
            MENU_BUTTON_ROW;

    public FluffyFurConfig(ForgeConfigSpec.Builder builder) {

    }

    public static final FluffyFurConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<FluffyFurConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(FluffyFurConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}
