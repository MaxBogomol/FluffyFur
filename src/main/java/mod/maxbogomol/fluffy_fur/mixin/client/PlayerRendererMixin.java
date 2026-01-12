package mod.maxbogomol.fluffy_fur.mixin.client;

import mod.maxbogomol.fluffy_fur.client.render.entity.ExtraLayer;
import mod.maxbogomol.fluffy_fur.common.playerskin.PlayerSkinHandler;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin {

    @Inject(at = @At("RETURN"), method = "<init>")
    private void fluffy_fur$PlayerRenderer(EntityRendererProvider.Context context, boolean useSlimModel, CallbackInfo ci) {
        PlayerRenderer self = (PlayerRenderer) ((Object) this);
        self.addLayer(new ExtraLayer<>(self));
    }

    @Inject(at = @At("RETURN"), method = "Lnet/minecraft/client/renderer/entity/player/PlayerRenderer;getRenderOffset(Lnet/minecraft/client/player/AbstractClientPlayer;F)Lnet/minecraft/world/phys/Vec3;", cancellable = true)
    private void fluffy_fur$getRenderOffset(AbstractClientPlayer entity, float partialTicks, CallbackInfoReturnable<Vec3> cir) {
        if (entity.isCrouching()) {
            float scale = PlayerSkinHandler.getPlayerSizeScale(entity);
            Vec3 vec3 = cir.getReturnValue();
            if (scale != 1) cir.setReturnValue(new Vec3(vec3.x(), vec3.y() * scale, vec3.z()));
        }
    }
}
