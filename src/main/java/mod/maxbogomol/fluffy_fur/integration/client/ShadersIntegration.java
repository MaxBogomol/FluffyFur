package mod.maxbogomol.fluffy_fur.integration.client;

import net.irisshaders.iris.Iris;
import net.minecraftforge.fml.ModList;

public class ShadersIntegration {
    public static boolean LOADED;

    public static class LoadedOnly {
        public static boolean isShadersEnabled() {
            return Iris.getIrisConfig().areShadersEnabled();
        }
    }

    public static void init() {
        LOADED = ModList.get().isLoaded("oculus");
    }

    public static boolean isLoaded() {
        return LOADED;
    }

    public static boolean isShadersEnabled() {
        if (isLoaded()) {
            return LoadedOnly.isShadersEnabled();
        }
        return false;
    }
}