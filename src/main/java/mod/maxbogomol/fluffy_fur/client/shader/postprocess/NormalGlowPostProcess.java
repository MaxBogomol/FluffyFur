package mod.maxbogomol.fluffy_fur.client.shader.postprocess;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import net.minecraft.resources.ResourceLocation;

public class NormalGlowPostProcess extends GlowPostProcess {
    public static final NormalGlowPostProcess INSTANCE = new NormalGlowPostProcess();
    public ResourceLocation shader = new ResourceLocation(FluffyFur.MOD_ID, "shaders/post/normal_glow.json");

    public NormalGlowPostProcess addInstance(GlowPostProcessInstance instance) {
        data.addInstance(instance);
        setActive(true);
        return this;
    }

    @Override
    public ResourceLocation getPostChainLocation() {
        return shader;
    }
}
