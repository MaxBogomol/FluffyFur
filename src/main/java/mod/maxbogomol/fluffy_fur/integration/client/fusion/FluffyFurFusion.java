package mod.maxbogomol.fluffy_fur.integration.client.fusion;

import com.supermartijn642.fusion.api.predicate.FusionPredicateRegistry;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.integration.client.fusion.predicates.MatchBlockTagConnectionPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;

public class FluffyFurFusion {
    public static boolean LOADED;

    public static class ClientLoadedOnly {
        public static void init() {
            FusionPredicateRegistry.registerConnectionPredicate(new ResourceLocation(FluffyFur.MOD_ID, "match_block_tag"), MatchBlockTagConnectionPredicate.SERIALIZER);
        }
    }

    public static void init(IEventBus eventBus) {
        LOADED = ModList.get().isLoaded("fusion");
    }

    public static void setup() {
        if (isLoaded()) {
            ClientLoadedOnly.init();
        }
    }

    public static boolean isLoaded() {
        return LOADED;
    }
}
