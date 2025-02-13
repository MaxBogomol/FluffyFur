package mod.maxbogomol.fluffy_fur.registry.common.item;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

public class FluffyFurCreativeTabs {

    public static void addCreativeTabContent(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(FluffyFurItems.MUSIC_DISC_FLUFFY);
        }

        if (event.getTabKey() == CreativeModeTabs.OP_BLOCKS && event.hasPermissions()) {
            event.accept(FluffyFurItems.TEST_SHRIMP);
            event.accept(FluffyFurItems.THING);
            event.accept(FluffyFurItems.SILLY_TREAT);

            event.accept(FluffyFurItems.MAXBOGOMOL_PLUSH);
            event.accept(FluffyFurItems.ONIXTHECAT_PLUSH);
            event.accept(FluffyFurItems.UNOLOGICALSAMSAR_PLUSH);
            event.accept(FluffyFurItems.SAMMYSEMICOLON_PLUSH);
            event.accept(FluffyFurItems.BOYKISSER_PLUSH);
            event.accept(FluffyFurItems.NANACHI_PLUSH);
            event.accept(FluffyFurItems.SPECKLE_PLUSH);
        }
    }
}
