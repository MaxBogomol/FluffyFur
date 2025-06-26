package mod.maxbogomol.fluffy_fur.registry.common.item;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.common.creativetab.MultiCreativeTab;
import mod.maxbogomol.fluffy_fur.common.creativetab.SubCreativeTab;
import mod.maxbogomol.fluffy_fur.common.creativetab.SubCreativeTabStack;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.function.Supplier;

public class FluffyFurCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FluffyFur.MOD_ID);

    public static final RegistryObject<CreativeModeTab> FLUFFY_FUR = CREATIVE_MODE_TABS.register("tab",
            () -> MultiCreativeTab.builder().icon(() -> new ItemStack(FluffyFurItems.MAXBOGOMOL_PLUSH.get()))
                    .title(Component.translatable("creative_tab.fluffy_fur"))
                    .withLabelColor(ColorUtil.packColor(255, 55, 48, 54))
                    .withBackgroundLocation(new ResourceLocation(FluffyFur.MOD_ID, "textures/gui/fluffy_fur_item_tab.png"))
                    .withTabsImage(new ResourceLocation(FluffyFur.MOD_ID, "textures/gui/fluffy_fur_tabs.png"))
                    .withSubArrowsImage(new ResourceLocation(FluffyFur.MOD_ID, "textures/gui/fluffy_fur_sub_arrows.png"))
                    .multiBuild());

    public static final SubCreativeTabStack ALL =
            SubCreativeTabStack.create()
                    .subTitle(Component.translatable("creative_tab.fluffy_fur"))
                    .withSubTabImage(new ResourceLocation(FluffyFur.MOD_ID, "textures/gui/fluffy_fur_sub_tab.png"));

    public static final SubCreativeTab SILLY_THINGS =
            SubCreativeTab.create().subIcon(() -> new ItemStack(FluffyFurItems.SILLY_TREAT.get()))
                    .title(Component.translatable("creative_tab.fluffy_fur").append(": ").append(Component.translatable("creative_tab.fluffy_fur.sub.silly_things")))
                    .subTitle(Component.translatable("creative_tab.fluffy_fur.sub.silly_things"))
                    .withSubTabImage(new ResourceLocation(FluffyFur.MOD_ID, "textures/gui/fluffy_fur_sub_tab.png"));

    public static final SubCreativeTab PLUSHIES =
            SubCreativeTab.create().subIcon(() -> new ItemStack(FluffyFurItems.ONIXTHECAT_PLUSH.get()))
                    .title(Component.translatable("creative_tab.fluffy_fur").append(": ").append(Component.translatable("creative_tab.fluffy_fur.sub.plushies")))
                    .subTitle(Component.translatable("creative_tab.fluffy_fur.sub.plushies"))
                    .withSubTabImage(new ResourceLocation(FluffyFur.MOD_ID, "textures/gui/fluffy_fur_sub_tab.png"));

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    public static void init() {
        if (FluffyFurCreativeTabs.FLUFFY_FUR.get() instanceof MultiCreativeTab multiCreativeTab) {
            multiCreativeTab.addSubTab(ALL);
            multiCreativeTab.addSubTab(SILLY_THINGS);
            multiCreativeTab.addSubTab(PLUSHIES);
        }
    }

    public static void addCreativeTabContent(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == FluffyFurCreativeTabs.FLUFFY_FUR.getKey()) {
            addInSub(event, SILLY_THINGS, FluffyFurItems.MUSIC_DISC_FLUFFY);

            if (event.hasPermissions()) {
                SILLY_THINGS.subIcon(() -> new ItemStack(FluffyFurItems.SILLY_TREAT.get()));
            } else {
                SILLY_THINGS.subIcon(() -> new ItemStack(FluffyFurItems.MUSIC_DISC_FLUFFY.get()));
            }
            PLUSHIES.setSubShow(event::hasPermissions);
        }

        if (event.getTabKey() == FluffyFurCreativeTabs.FLUFFY_FUR.getKey() && event.hasPermissions()) {
            addInSub(event, SILLY_THINGS, FluffyFurItems.TEST_SHRIMP);
            addInSub(event, SILLY_THINGS, FluffyFurItems.THING);
            addInSub(event, SILLY_THINGS, FluffyFurItems.SILLY_TREAT);

            addInSub(event, PLUSHIES, FluffyFurItems.MAXBOGOMOL_PLUSH);
            addInSub(event, PLUSHIES, FluffyFurItems.ONIXTHECAT_PLUSH);
            addInSub(event, PLUSHIES, FluffyFurItems.UNOLOGICALSAMSAR_PLUSH);
            addInSub(event, PLUSHIES, FluffyFurItems.FOXAIRPLANE_PLUSH);
            addInSub(event, PLUSHIES, FluffyFurItems.ONJERLAY_PLUSH);
            addInSub(event, PLUSHIES, FluffyFurItems.SAMMYSEMICOLON_PLUSH);
            addInSub(event, PLUSHIES, FluffyFurItems.BIVEROM_PLUSH);
            addInSub(event, PLUSHIES, FluffyFurItems.BOYKISSER_PLUSH);
            addInSub(event, PLUSHIES, FluffyFurItems.NANACHI_PLUSH);
            addInSub(event, PLUSHIES, FluffyFurItems.NIKO_PLUSH);
            addInSub(event, PLUSHIES, FluffyFurItems.PURO_PLUSH);
            addInSub(event, PLUSHIES, FluffyFurItems.TUNIC_THE_FOX_PLUSH);
            addInSub(event, PLUSHIES, FluffyFurItems.DARK_PRINCE);
            addInSub(event, PLUSHIES, FluffyFurItems.SPECKLE_PLUSH);
            addInSub(event, PLUSHIES, FluffyFurItems.SEADRIVE_PLUSH);
        }
    }

    public static void addInSub(BuildCreativeModeTabContentsEvent event, SubCreativeTab subTab, Supplier<? extends ItemLike> item) {
        event.accept(item);
        subTab.addDisplayItem(item.get());
    }

    public static void addInSub(BuildCreativeModeTabContentsEvent event, SubCreativeTab subTab, ItemStack item) {
        event.accept(item);
        subTab.addDisplayItem(item);
    }

    public static void addInSub(BuildCreativeModeTabContentsEvent event, SubCreativeTab subTab, Collection<ItemStack> items) {
        event.acceptAll(items);
        subTab.addDisplayItems(items);
    }
}
