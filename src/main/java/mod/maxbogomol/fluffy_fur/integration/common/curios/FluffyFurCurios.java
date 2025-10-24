package mod.maxbogomol.fluffy_fur.integration.common.curios;

import mod.maxbogomol.fluffy_fur.registry.common.item.FluffyFurItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class FluffyFurCurios {
    public static boolean LOADED;
    public static String MOD_ID = "curios";

    public static class ClientLoadedOnly {
        public static void init() {
            CuriosRendererRegistry.register(FluffyFurItems.MAXBOGOMOL_PLUSH.get(), PlushHeadRenderer::new);
            CuriosRendererRegistry.register(FluffyFurItems.ONIXTHECAT_PLUSH.get(), PlushHeadRenderer::new);
            CuriosRendererRegistry.register(FluffyFurItems.UNOLOGICALSAMSAR_PLUSH.get(), PlushHeadRenderer::new);
            CuriosRendererRegistry.register(FluffyFurItems.FOXAIRPLANE_PLUSH.get(), PlushHeadRenderer::new);
            CuriosRendererRegistry.register(FluffyFurItems.ONJERLAY_PLUSH.get(), PlushHeadRenderer::new);
            CuriosRendererRegistry.register(FluffyFurItems.SAMMYSEMICOLON_PLUSH.get(), PlushHeadRenderer::new);
            CuriosRendererRegistry.register(FluffyFurItems.VIOLUNAE_PLUSH.get(), PlushHeadRenderer::new);
            CuriosRendererRegistry.register(FluffyFurItems.BOYKISSER_PLUSH.get(), PlushHeadRenderer::new);
            CuriosRendererRegistry.register(FluffyFurItems.NANACHI_PLUSH.get(), PlushHeadRenderer::new);
            CuriosRendererRegistry.register(FluffyFurItems.NIKO_PLUSH.get(), PlushHeadRenderer::new);
            CuriosRendererRegistry.register(FluffyFurItems.PURO_PLUSH.get(), PlushHeadRenderer::new);
            CuriosRendererRegistry.register(FluffyFurItems.TUNIC_THE_FOX_PLUSH.get(), PlushHeadRenderer::new);
            CuriosRendererRegistry.register(FluffyFurItems.SPECKLE_PLUSH.get(), PlushHeadRenderer::new);
            CuriosRendererRegistry.register(FluffyFurItems.DARK_PRINCE_PLUSH.get(), PlushHeadRenderer::new);
            CuriosRendererRegistry.register(FluffyFurItems.RALSEI_PLUSH.get(), PlushHeadRenderer::new);
            CuriosRendererRegistry.register(FluffyFurItems.SPECKLE_PLUSH.get(), PlushHeadRenderer::new);
            CuriosRendererRegistry.register(FluffyFurItems.SEADRIVE_PLUSH.get(), PlushHeadRenderer::new);
            CuriosRendererRegistry.register(FluffyFurItems.YONKABLOCK.get(), PlushHeadRenderer::new);
        }
    }

    public static void init(IEventBus eventBus) {
        LOADED = ModList.get().isLoaded(MOD_ID);
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
