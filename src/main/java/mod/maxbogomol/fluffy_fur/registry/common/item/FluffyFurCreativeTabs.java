package mod.maxbogomol.fluffy_fur.registry.common.item;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

public class FluffyFurCreativeTabs {

    public static void addCreativeTabContent(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(FluffyFurItems.MUSIC_DISC_FLUFFY);
        }
    }
}
