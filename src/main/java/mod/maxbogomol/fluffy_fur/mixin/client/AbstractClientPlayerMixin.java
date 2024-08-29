package mod.maxbogomol.fluffy_fur.mixin.client;

import mod.maxbogomol.fluffy_fur.client.playerskin.PlayerSkin;
import mod.maxbogomol.fluffy_fur.client.playerskin.PlayerSkinHandler;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin {

    @Inject(at = @At("RETURN"), method = "getSkinTextureLocation", cancellable = true)
    private void fluffy_fur$getSkinTextureLocation(CallbackInfoReturnable<ResourceLocation> cir) {
        AbstractClientPlayer self = (AbstractClientPlayer) ((Object) this);
        PlayerSkin skin = PlayerSkinHandler.getSkin(self);

        if (skin != null) {
            ResourceLocation skinTexture = skin.getSkin(self);
            if (skinTexture != null) {
                cir.setReturnValue(skinTexture);
            }
        }
    }

    @Inject(at = @At("RETURN"), method = "getModelName", cancellable = true)
    private void fluffy_fur$getModelName(CallbackInfoReturnable<String> cir) {
        AbstractClientPlayer self = (AbstractClientPlayer) ((Object) this);
        PlayerSkin skin = PlayerSkinHandler.getSkin(self);

        if (skin != null) {
            if (skin.getSlim(self)) {
                cir.setReturnValue("slim");
            } else {
                cir.setReturnValue("default");
            }
        }
    }
}
