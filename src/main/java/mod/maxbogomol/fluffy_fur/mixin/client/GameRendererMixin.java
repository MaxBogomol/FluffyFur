package mod.maxbogomol.fluffy_fur.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.client.render.LevelRenderHandler;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.client.shader.postprocess.PostProcessHandler;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Shadow public abstract void renderLevel(float pPartialTicks, long pFinishTimeNano, PoseStack pPoseStack);

    @Inject(at = @At(value = "RETURN"), method = "renderItemInHand")
    private void fluffy_fur$renderItemInHand(PoseStack pPoseStack, Camera pActiveRenderInfo, float pPartialTicks, CallbackInfo ci) {
        for (RenderBuilder builder : FluffyFurRenderTypes.customItemRenderBuilderFirst) {
            builder.endBatch();
        }
        FluffyFurRenderTypes.customItemRenderBuilderFirst.clear();
    }

    @Inject(method = "resize", at = @At(value = "HEAD"))
    public void fluffy_fur$injectionResizeListener(int width, int height, CallbackInfo ci) {
        LevelRenderHandler.resize(width, height);
        PostProcessHandler.resize(width, height);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/pipeline/RenderTarget;bindWrite(Z)V"), method = "render")
    public void fluffy_fur$renderScreenPostProcess(float partialTicks, long nanoTime, boolean renderLevel, CallbackInfo ci) {
        PostProcessHandler.onScreenRender((GameRenderer) (Object) this, partialTicks, nanoTime, renderLevel);
    }

    @Inject(at = @At(value = "RETURN"), method = "render")
    public void fluffy_fur$renderWindowPostProcess(float partialTicks, long nanoTime, boolean renderLevel, CallbackInfo ci) {
        PostProcessHandler.onWindowRender((GameRenderer) (Object) this, partialTicks, nanoTime, renderLevel);
    }
}
