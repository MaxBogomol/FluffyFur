package mod.maxbogomol.fluffy_fur.client.shader.postprocess;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.resources.ResourceLocation;

public class GlowPostProcess extends PostProcess {
    public static final GlowPostProcess INSTANCE = new GlowPostProcess();
    public final PostProcessInstanceData data = new GlowPostProcessInstanceData();
    public EffectInstance effectInstance;
    public ResourceLocation shader = new ResourceLocation(FluffyFur.MOD_ID, "shaders/post/glow.json");

    public GlowPostProcess addInstance(GlowPostProcessInstance instance) {
        data.addInstance(instance);
        setActive(true);
        return this;
    }

    @Override
    public void init() {
        super.init();
        if (postChain != null) {
            effectInstance = effects[0];
        }
        data.init();
    }

    @Override
    public ResourceLocation getPostChainLocation() {
        return shader;
    }

    @Override
    public void beforeProcess(PoseStack viewModelStack) {
        data.beforeProcess(viewModelStack);
        if (data.isEmpty()) {
            setActive(false);
        } else {
            data.setDataBufferUniform(effectInstance, "DataBuffer", "InstanceCount");
        }
    }

    @Override
    public void afterProcess() {

    }

    public static class GlowPostProcessInstanceData extends PostProcessInstanceData {

        @Override
        public int getMaxInstances() {
            return 1024;
        }

        @Override
        public int getDataSizePerInstance() {
            return 11;
        }
    }
}
