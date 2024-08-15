package mod.maxbogomol.fluffy_fur.mixin.client;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin {

    @Inject(at = @At("RETURN"), method = "getFieldOfViewModifier", cancellable = true)
    private void fluffy_fur$evaluateWhichHandsToRender(CallbackInfoReturnable<Float> cir) {
        AbstractClientPlayer self = (AbstractClientPlayer) ((Object) this);
        ItemStack itemstack = self.getUseItem();
        if (self.isUsingItem()) {
            /*
            if (itemstack.is(FluffyFur.ARCANE_WOOD_BOW.get())) {
                float f = cir.getReturnValue();
                int i = self.getTicksUsingItem();
                float f1 = (float)i / 20.0F;
                if (f1 > 1.0F) {
                    f1 = 1.0F;
                } else {
                    f1 *= f1;
                }

                f *= 1.0F - f1 * 0.15F;
                //f = EagleShotArcaneEnchantment.getFOW(self, itemstack, f);
                //f = SplitArcaneEnchantment.getFOW(self, itemstack, f);
                cir.setReturnValue(net.minecraftforge.client.ForgeHooksClient.getFieldOfViewModifier(self, f));
            }*/
        }
    }
}
