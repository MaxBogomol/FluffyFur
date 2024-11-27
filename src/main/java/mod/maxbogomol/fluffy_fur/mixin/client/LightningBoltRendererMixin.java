package mod.maxbogomol.fluffy_fur.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.client.effect.FluffyFurEffects;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LightningBoltRenderer;
import net.minecraft.world.entity.LightningBolt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningBoltRenderer.class)
public abstract class LightningBoltRendererMixin {

    @Inject(at = @At("HEAD"), method = "render*", cancellable = true)
    public void fluffy_fur$render(LightningBolt entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        ci.cancel();
        FluffyFurEffects.lightningBoltRender(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
