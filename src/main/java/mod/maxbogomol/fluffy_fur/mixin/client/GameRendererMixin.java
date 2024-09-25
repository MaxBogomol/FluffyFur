package mod.maxbogomol.fluffy_fur.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Inject(at = @At(value = "RETURN"), method = "renderItemInHand")
    private void fluffy_fur$renderItemInHand(PoseStack pPoseStack, Camera pActiveRenderInfo, float pPartialTicks, CallbackInfo ci) {
        for (RenderBuilder builder : FluffyFurRenderTypes.customItemRenderBuilderFirst) {
            builder.endBatch();
        }
        FluffyFurRenderTypes.customItemRenderBuilderFirst.clear();
    }
}
