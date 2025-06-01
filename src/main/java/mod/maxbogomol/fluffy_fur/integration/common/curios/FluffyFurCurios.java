package mod.maxbogomol.fluffy_fur.integration.common.curios;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;

public class FluffyFurCurios {
    public static boolean LOADED;
    public static String MOD_ID = "curios";

    public static class ClientLoadedOnly {

    }

    public static void init(IEventBus eventBus) {
        LOADED = ModList.get().isLoaded(MOD_ID);
    }

    public static boolean isLoaded() {
        return LOADED;
    }
}
