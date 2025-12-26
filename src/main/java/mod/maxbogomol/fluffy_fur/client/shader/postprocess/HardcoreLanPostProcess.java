package mod.maxbogomol.fluffy_fur.client.shader.postprocess;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class HardcoreLanPostProcess extends PostProcess {
    public static final HardcoreLanPostProcess INSTANCE = new HardcoreLanPostProcess();
    public EffectInstance effectInstance;
    public ResourceLocation shader = new ResourceLocation(FluffyFur.MOD_ID, "shaders/post/hardcore_lan.json");
    public int tick = 0;
    public int oldTick = 0;

    public HardcoreLanPostProcess enable() {
        setActive(true);
        tick = 0;
        oldTick = 0;
        return this;
    }

    @Override
    public void init() {
        super.init();
        if (postChain != null) {
            effectInstance = effects[0];
        }
    }

    @Override
    public void tick() {
        if (isActive) {
            oldTick = tick;
            if (tick < getMaxTick()) {
                tick = tick + 1;
            } else {
                setActive(false);
            }
        }
    }

    @Override
    public ResourceLocation getPostChainLocation() {
        return shader;
    }

    @Override
    public void beforeProcess(PoseStack viewModelStack) {

    }

    @Override
    public void afterProcess() {
        float intensity = 1f - (Mth.lerp(ClientTickHandler.partialTicks, oldTick, tick) / getMaxTick());
        effectInstance.safeGetUniform("intensity").set(intensity);
    }

    @Override
    public boolean isScreen() {
        return true;
    }

    @Override
    public boolean isWindow() {
        return true;
    }

    @Override
    public boolean isPaused() {
        return false;
    }

    public int getMaxTick() {
        return 15;
    }
}
