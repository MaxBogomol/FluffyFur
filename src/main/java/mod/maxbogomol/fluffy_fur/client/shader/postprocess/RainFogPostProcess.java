package mod.maxbogomol.fluffy_fur.client.shader.postprocess;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.config.FluffyFurClientConfig;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LightLayer;

public class RainFogPostProcess extends PostProcess {
    public static final RainFogPostProcess INSTANCE = new RainFogPostProcess();
    public EffectInstance effectInstance;
    public ResourceLocation shader = new ResourceLocation(FluffyFur.MOD_ID, "shaders/post/rain_fog.json");
    public int rainTick = 0;
    public int oldRainTick = 0;
    public int rainLevel = 0;
    public int thunderTick = 0;
    public int oldThunderTick = 0;
    public int thunderLevel = 0;

    @Override
    public void init() {
        super.init();
        if (postChain != null) {
            effectInstance = effects[0];
        }
    }

    @Override
    public void tick() {
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
        if (rainTick == 0 && rainLevel == 0 && thunderTick == 0 && thunderLevel == 0) {
            setActive(false);
        }
        rainLevel = 0;
        thunderLevel = 0;
    }

    public void tickEffect() {
        if (FluffyFurClientConfig.RAIN_FOG_SHADER.get()) {
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

            if (!isActive()) {
                if (rainLevel > 0) setActive(true);
                if (thunderLevel > 0) setActive(true);
            }
        }
    }

    @Override
    public ResourceLocation getPostChainLocation() {
        return shader;
    }

    @Override
    public void beforeProcess(PoseStack viewModelStack) {
        float rainL = 0;
        float thunderL = 0;
        if (minecraft.level != null) {
            rainL = minecraft.level.rainLevel;
            thunderL = minecraft.level.thunderLevel;
        }
        float rain = (Mth.lerp(ClientTickHandler.partialTicks, oldRainTick, rainTick) / (getTickLightLevel() * 15) * rainL);
        float thunder = (Mth.lerp(ClientTickHandler.partialTicks, oldThunderTick, thunderTick) / (getTickLightLevel() * 15) * thunderL);
        effectInstance.safeGetUniform("rainStrength").set(rain);
        effectInstance.safeGetUniform("thunderStrength").set(thunder);
        effectInstance.safeGetUniform("rainIntensity").set(FluffyFurClientConfig.RAIN_FOG_SHADER_INTENSITY.get().floatValue() + (FluffyFurClientConfig.THUNDER_FOG_SHADER_INTENSITY.get().floatValue() * thunder));
        effectInstance.safeGetUniform("thunderIntensity").set(FluffyFurClientConfig.THUNDER_FOG_FADE_SHADER_INTENSITY.get().floatValue());
        effectInstance.safeGetUniform("enabledNoise").set(FluffyFurClientConfig.RAIN_FOG_SHADER_NOISE.get() ? 1f : 0f);
        effectInstance.safeGetUniform("enabledIGN").set(FluffyFurClientConfig.RAIN_FOG_SHADER_IGN.get() ? 1f : 0f);
    }

    @Override
    public void afterProcess() {

    }

    @Override
    public boolean isScreen() {
        return true;
    }

    @Override
    public float getPriority() {
        return 10;
    }

    public int getTickLightLevel() {
        return 3;
    }
}
