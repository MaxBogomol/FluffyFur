package mod.maxbogomol.fluffy_fur.client.event;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.FluffyFurClient;
import mod.maxbogomol.fluffy_fur.client.config.ClientConfig;
import mod.maxbogomol.fluffy_fur.client.gui.components.CustomLogoRenderer;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEvents {
    public static ResourceLocation panorama = new ResourceLocation(FluffyFur.MOD_ID, "textures/gui/title/background/panorama_0");
    public static int attributeModifierTooltip = 0;

    @SubscribeEvent
    public void openMainMenu(ScreenEvent.Opening event) {
        if (event.getScreen() instanceof TitleScreen titleScreen) {
            if (ClientConfig.CUSTOM_PANORAMA.get()) {
                boolean setPanorama = false;
                if (FluffyFurClient.firstScreen) {
                    setPanorama = true;
                    FluffyFurClient.firstScreen = false;
                }
                if (!TitleScreen.CUBE_MAP.images[0].equals(panorama)) {
                    setPanorama = true;
                }
                if (setPanorama) {
                    float spin = titleScreen.panorama.spin;
                    float bob = titleScreen.panorama.bob;
                    ResourceLocation base = new ResourceLocation(FluffyFur.MOD_ID, "textures/gui/title/background/panorama");
                    TitleScreen.CUBE_MAP = new CubeMap(base);
                    titleScreen.panorama = new PanoramaRenderer(TitleScreen.CUBE_MAP);
                    titleScreen.logoRenderer = new CustomLogoRenderer(false);
                    titleScreen.panorama.spin = spin;
                    titleScreen.panorama.bob = bob;
                }
            }
        }
    }

    @SubscribeEvent
    public void loggedPlayer(PlayerEvent.PlayerLoggedInEvent event) {

    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onTooltip(ItemTooltipEvent event) {

    }

    @SubscribeEvent
    public void input(MovementInputUpdateEvent event) {

    }
}
