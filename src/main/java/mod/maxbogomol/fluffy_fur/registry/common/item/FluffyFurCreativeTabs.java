package mod.maxbogomol.fluffy_fur.registry.common.item;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class FluffyFurCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FluffyFur.MOD_ID);

    public static final RegistryObject<CreativeModeTab> FLUFFY_FUR = CREATIVE_MODE_TABS.register("tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(FluffyFurItems.MAXBOGOMOL_PLUSH.get()))
                    .title(Component.translatable("creative_tab.fluffy_fur"))
                    .withLabelColor(ColorUtil.packColor(255, 55, 48, 54))
                    .withBackgroundLocation(new ResourceLocation(FluffyFur.MOD_ID, "textures/gui/fluffy_fur_item_tab.png"))
                    .withTabsImage(new ResourceLocation(FluffyFur.MOD_ID, "textures/gui/fluffy_fur_tabs.png"))
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    public static void addCreativeTabContent(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(FluffyFurItems.MUSIC_DISC_FLUFFY);
        }

        if (event.getTabKey() == FluffyFurCreativeTabs.FLUFFY_FUR.getKey() && event.hasPermissions()) {
            event.accept(FluffyFurItems.MUSIC_DISC_FLUFFY);

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
