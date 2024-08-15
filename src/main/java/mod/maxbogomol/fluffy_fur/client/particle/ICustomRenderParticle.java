package mod.maxbogomol.fluffy_fur.client.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;

public interface ICustomRenderParticle {
    void render(PoseStack stack, MultiBufferSource buffer, float partialTicks);
}
