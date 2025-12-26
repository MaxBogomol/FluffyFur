package mod.maxbogomol.fluffy_fur.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.client.gui.components.HardcoreLanCommandsButton;
import mod.maxbogomol.fluffy_fur.client.gui.components.HardcoreLanGameModeButton;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.ShareToLanScreen;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.GameType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.util.Random;

public class HardcoreShareToLanScreenHandler {

    public static boolean isDumbAss = false;
    public static int isDumbAssTicks = 0;
    public static long dumbAssSeed = 0L;
    public static Color dumbAssColor = new Color(0xff074d);

    @OnlyIn(Dist.CLIENT)
    public static void tick(ShareToLanScreen screen) {
        if (isDumbAss) {
            isDumbAssTicks++;
            if (isDumbAssTicks % 2 == 0) {
                dumbAssSeed++;
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void render(ShareToLanScreen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (isDumbAss) {
            Font font = Minecraft.getInstance().font;
            PoseStack poseStack = guiGraphics.pose();

            String string = Component.translatable("gui.fluffy_fur.menu.hardcore_lan").getString();
            Random random = new Random(dumbAssSeed);

            float ticks = (isDumbAssTicks + partialTick) * 0.1f;
            poseStack.pushPose();
            poseStack.translate(screen.width / 2f, screen.height / 2f, 0);
            float w = 2;
            if (screen.width / 2 < font.width(string)) w = 1;
            poseStack.scale(w, w, w);
            poseStack.translate(-font.width(string) / 2f, 0, 0);
            int offset = 0;
            int i = 0;
            for (char c : string.toCharArray()) {
                float size = ticks - (i * 0.1f);
                String s = String.valueOf(c);
                if (size > 0) {
                    if (size > 1) size = 1;
                    size = Easing.QUARTIC_IN_OUT.ease(size, 0, 1, 1);
                    poseStack.pushPose();
                    poseStack.translate(0, (1f - size) * 25 * w, 0);
                    poseStack.translate((random.nextFloat() * 2f) - 0.5f, (random.nextFloat() * 2f) - 0.5f, 0);
                    poseStack.scale(size, size, size);
                    guiGraphics.drawString(font, s, offset, -font.lineHeight - 1, ColorUtil.packColor(dumbAssColor));
                    poseStack.popPose();
                }
                offset = offset + font.width(s);
                i++;
            }
            poseStack.popPose();
        }
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT)
    public static class HardcoreLanHandler {
        @SubscribeEvent
        public static void onGuiInit(ScreenEvent.Init event) {
            Screen screen = event.getScreen();

            if (screen instanceof ShareToLanScreen shareToLanScreen) {
                isDumbAss = false;
                IntegratedServer integratedServer = Minecraft.getInstance().getSingleplayerServer();
                if (integratedServer != null && integratedServer.isHardcore()) {
                    if (shareToLanScreen.renderables.size() >= 1) {
                        Renderable renderable = shareToLanScreen.renderables.get(0);
                        if (renderable instanceof CycleButton<?> cycleButton) {
                            GameType gameMode = integratedServer.getDefaultGameType();
                            HardcoreLanGameModeButton gameModeButton = new HardcoreLanGameModeButton(cycleButton.getX(), cycleButton.getY(), cycleButton.getWidth(), cycleButton.getHeight(), gameMode);
                            shareToLanScreen.renderables.remove(0);
                            shareToLanScreen.children().remove(0);
                            event.addListener(gameModeButton);
                        }
                    }
                    if (shareToLanScreen.renderables.size() >= 1) {
                        Renderable renderable = shareToLanScreen.renderables.get(0);
                        if (renderable instanceof CycleButton<?> cycleButton) {
                            HardcoreLanCommandsButton commandsButton = new HardcoreLanCommandsButton(cycleButton.getX(), cycleButton.getY(), cycleButton.getWidth(), cycleButton.getHeight());
                            shareToLanScreen.renderables.remove(0);
                            shareToLanScreen.children().remove(0);
                            event.addListener(commandsButton);
                        }
                    }
                }
            }
        }
    }
}
