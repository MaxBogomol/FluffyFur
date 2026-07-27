package mod.maxbogomol.fluffy_fur.client.render;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.config.FluffyFurClientConfig;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurShaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LightLayer;

public class RainFogOverlayHandler {
    public static int rainTick = 0;
    public static int oldRainTick = 0;
    public static int rainLevel = 0;
    public static int thunderTick = 0;
    public static int oldThunderTick = 0;
    public static int thunderLevel = 0;

    public static void tick() {
        if (FluffyFurClientConfig.RAIN_FOG_OVERLAY.get()) {
            oldRainTick = rainTick;
            oldThunderTick = thunderTick;
            if (rainTick < (getTickLightLevel() * rainLevel)) {
                rainTick = rainTick + 1;
            }
            if (rainTick > (getTickLightLevel() * rainLevel)) {
                rainTick = rainTick - 1;
            }
            if (thunderTick < (getTickLightLevel() * thunderLevel)) {
                thunderTick = thunderTick + 1;
            }
            if (thunderTick > (getTickLightLevel() * thunderLevel)) {
                thunderTick = thunderTick - 1;
            }
            rainLevel = 0;
            thunderLevel = 0;

            Minecraft minecraft = Minecraft.getInstance();
            Player player = FluffyFur.proxy.getPlayer();
            if (player != null && minecraft.level != null) {
                BlockPos pos = BlockPos.containing(minecraft.gameRenderer.getMainCamera().getPosition());
                if (minecraft.level.isRaining()) {
                    rainLevel = minecraft.level.getBrightness(LightLayer.SKY, pos);
                }
                if (minecraft.level.isThundering()) {
                    thunderLevel = minecraft.level.getBrightness(LightLayer.SKY, pos);
                }
            }
        }
    }

    public static int getTickLightLevel() {
        return 3;
    }

    public static void renderOverlay(GuiGraphics gui) {
        if (FluffyFurClientConfig.RAIN_FOG_OVERLAY.get()) {
            if (FluffyFur.proxy.getPlayer().isAlive() && FluffyFur.proxy.getLevel() != null) {
                Minecraft minecraft = Minecraft.getInstance();
                float width = (float) minecraft.getWindow().getWidth();
                float height = (float) minecraft.getWindow().getHeight();
                float rainL = 0;
                float thunderL = 0;
                if (minecraft.level != null) {
                    rainL = minecraft.level.rainLevel;
                    thunderL = minecraft.level.thunderLevel;
                }
                float rain = (Mth.lerp(ClientTickHandler.partialTicks, RainFogOverlayHandler.oldRainTick, RainFogOverlayHandler.rainTick) / (RainFogOverlayHandler.getTickLightLevel() * 15) * rainL);
                float thunder = (Mth.lerp(ClientTickHandler.partialTicks, RainFogOverlayHandler.oldThunderTick, RainFogOverlayHandler.thunderTick) / (RainFogOverlayHandler.getTickLightLevel() * 15) * thunderL);
                if (rain > 0 || thunder > 0) {
                    FluffyFurShaders.RAIN_FOG_OVERLAY.safeGetUniform("screenSize").set(width, height);
                    FluffyFurShaders.RAIN_FOG_OVERLAY.safeGetUniform("totalTicks").set(ClientTickHandler.getTotal());
                    FluffyFurShaders.RAIN_FOG_OVERLAY.safeGetUniform("rainStrength").set(rain);
                    FluffyFurShaders.RAIN_FOG_OVERLAY.safeGetUniform("thunderStrength").set(thunder);
                    FluffyFurShaders.RAIN_FOG_OVERLAY.safeGetUniform("rainIntensity").set(FluffyFurClientConfig.RAIN_FOG_OVERLAY_INTENSITY.get().floatValue() + (FluffyFurClientConfig.THUNDER_FOG_OVERLAY_INTENSITY.get().floatValue() * thunder));
                    FluffyFurShaders.RAIN_FOG_OVERLAY.safeGetUniform("thunderIntensity").set(FluffyFurClientConfig.THUNDER_FOG_FADE_OVERLAY_INTENSITY.get().floatValue());
                    FluffyFurShaders.RAIN_FOG_OVERLAY.safeGetUniform("enabledNoise").set(FluffyFurClientConfig.RAIN_FOG_OVERLAY_NOISE.get() ? 1f : 0f);
                    FluffyFurShaders.RAIN_FOG_OVERLAY.safeGetUniform("enabledIGN").set(FluffyFurClientConfig.RAIN_FOG_OVERLAY_IGN.get() ? 1f : 0f);

                    RenderBuilder.create().setRenderType(FluffyFurRenderTypes.RAIN_FOG_OVERLAY)
                            .setAlpha(1f / 3f)
                            .renderQuad(gui.pose(), width, height)
                            .endBatch();
                }
            }
        }
    }
}
