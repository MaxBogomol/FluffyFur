package mod.maxbogomol.fluffy_fur.client.event;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.FluffyFurClient;
import mod.maxbogomol.fluffy_fur.client.bow.BowHandler;
import mod.maxbogomol.fluffy_fur.client.gui.components.SubCreativeTabButton;
import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurModsHandler;
import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurPanorama;
import mod.maxbogomol.fluffy_fur.client.gui.screen.PlayerSkinMenuScreen;
import mod.maxbogomol.fluffy_fur.client.screenshake.ScreenshakeHandler;
import mod.maxbogomol.fluffy_fur.client.shader.postprocess.PostProcessHandler;
import mod.maxbogomol.fluffy_fur.client.shader.postprocess.RainFogPostProcess;
import mod.maxbogomol.fluffy_fur.common.creativetab.MultiCreativeTab;
import mod.maxbogomol.fluffy_fur.common.creativetab.SubCreativeTab;
import mod.maxbogomol.fluffy_fur.common.network.FluffyFurPacketHandler;
import mod.maxbogomol.fluffy_fur.common.network.item.StopUseItemPacket;
import mod.maxbogomol.fluffy_fur.config.FluffyFurClientConfig;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurKeyMappings;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;

public class FluffyFurClientEvents {

    public static ArrayList<SubCreativeTabButton> subCreativeTabButtons = new ArrayList<>();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onOpenScreen(ScreenEvent.Opening event) {
        panoramaScreen(event.getCurrentScreen());
        panoramaScreen(event.getNewScreen());
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onOpenScreenFirst(ScreenEvent.Opening event) {
        resetPanoramaScreen(event.getCurrentScreen());
        resetPanoramaScreen(event.getNewScreen());
    }

    @SubscribeEvent
    public void onScreenInitPost(ScreenEvent.Init.Post event) {
        Screen screen = event.getScreen();
        if (screen instanceof CreativeModeInventoryScreen creativeScreen) {
            subCreativeTabButtons.clear();
            int i = creativeScreen.getGuiLeft();
            int j = creativeScreen.getGuiTop();
            for (CreativeModeTab tab : CreativeModeTabs.allTabs()) {
                if (tab instanceof MultiCreativeTab multiCreativeTab) {
                    int ii = 0;
                    for (SubCreativeTab subTab : multiCreativeTab.getSortedSubTabs()) {
                        SubCreativeTabButton button = new SubCreativeTabButton(creativeScreen, multiCreativeTab, subTab, i - 22, j + 3 + (ii * 22), j + 3);
                        subCreativeTabButtons.add(button);
                        event.addListener(button);
                        ii++;
                    }
                }
            }
        }
    }

    public static void panoramaScreen(Screen screen) {
        if (screen instanceof TitleScreen titleScreen) {
            FluffyFurPanorama panorama = FluffyFurModsHandler.getPanorama(FluffyFurClientConfig.PANORAMA.get());
            if (panorama != null && !panorama.equals(FluffyFurClient.VANILLA_PANORAMA)) {
                boolean setPanorama = !TitleScreen.CUBE_MAP.images[0].equals(panorama.getTexture());
                if (setPanorama) {
                    FluffyFurModsHandler.setOpenPanorama(titleScreen, panorama);
                }
            }
        }
    }

    public static void resetPanoramaScreen(Screen screen) {
        if (screen instanceof TitleScreen titleScreen) {
            FluffyFurPanorama panorama = FluffyFurModsHandler.getPanorama(FluffyFurClientConfig.PANORAMA.get());
            if (panorama != null && panorama.equals(FluffyFurClient.VANILLA_PANORAMA)) {
                FluffyFurModsHandler.setOpenPanorama(titleScreen, FluffyFurClient.VANILLA_PANORAMA);
            }
        }
    }

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientTickHandler.clientTick(event);
        if (event.phase == TickEvent.Phase.END) {
            if (minecraft.isPaused()) {
                return;
            }
            Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
            ScreenshakeHandler.clientTick(camera);
            PostProcessHandler.tick();
            RainFogPostProcess.INSTANCE.tickEffect();
        }
    }

    @SubscribeEvent
    public void renderTick(TickEvent.RenderTickEvent event) {
        ClientTickHandler.renderTick(event);
    }

    @SubscribeEvent
    public void getFovModifier(ComputeFovModifierEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getUseItem();
        if (player.isUsingItem()) {
            for (Item item : BowHandler.getBows()) {
                if (itemStack.is(item)) {
                    float f = event.getFovModifier();
                    if (f != event.getNewFovModifier()) f = event.getNewFovModifier();
                    int i = player.getTicksUsingItem();
                    float f1 = (float) i / 20.0F;
                    if (f1 > 1.0F) {
                        f1 = 1.0F;
                    } else {
                        f1 *= f1;
                    }

                    f *= 1.0F - f1 * 0.15F;
                    event.setNewFovModifier((float) Mth.lerp(Minecraft.getInstance().options.fovEffectScale().get(), 1.0F, f));
                }
            }
        }
        ScreenshakeHandler.fovTick(event);
    }

    @SubscribeEvent
    public void onInput(InputEvent event) {
        if (FluffyFurKeyMappings.SKIN_MENU.isDown()) {
            String name = FluffyFur.proxy.getPlayer().getGameProfile().getName();
            if (name.equals("Dev") || name.equals("MaxBogomol") || name.equals("FurryFoxes")) {
                FluffyFurPacketHandler.sendToServer(new StopUseItemPacket());
                Minecraft.getInstance().setScreen(new PlayerSkinMenuScreen());
            }
        }
    }
}
