package mod.maxbogomol.fluffy_fur.client.shader.postprocess;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class DepthPostProcess extends PostProcess {
    public static final DepthPostProcess INSTANCE = new DepthPostProcess();
    public EffectInstance effectInstance;
    public ResourceLocation shader = new ResourceLocation(FluffyFur.MOD_ID, "shaders/post/depth.json");
    public int tick = 0;
    public int oldTick = 0;
    public boolean isToggle = false;

    public void toggle() {
        if (isToggle) {
            disable();
        } else {
            enable();
        }
    }

    public void enable() {
        isToggle = true;
        setActive(true);
    }

    public void disable() {
        isToggle = false;
    }

    @Override
    public void init() {
        super.init();
        if (postChain != null) {
            effectInstance = effects[0];
        }
    }

    public void tick() {
        oldTick = tick;
        if (isToggle) {
            if (tick < getMaxTick()) {
                tick = tick + 1;
            }
        } else {
            if (tick <= 0) {
                setActive(false);
            }
            if (tick > 0) {
                tick = tick - 1;
            }
        }
    }

    @Override
    public ResourceLocation getPostChainLocation() {
        return shader;
    }

    @Override
    public void beforeProcess(PoseStack viewModelStack) {
        float fade = (Mth.lerp(ClientTickHandler.partialTicks, oldTick, tick) / getMaxTick());
        effectInstance.safeGetUniform("fade").set(fade);
    }

    @Override
    public void afterProcess() {

    }

    @Override
    public float getPriority() {
        return 1000;
    }

    public int getMaxTick() {
        return 40;
    }
}
