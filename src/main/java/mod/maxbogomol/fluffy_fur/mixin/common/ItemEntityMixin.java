package mod.maxbogomol.fluffy_fur.mixin.common;

import mod.maxbogomol.fluffy_fur.common.entity.ItemEntityHandler;
import mod.maxbogomol.fluffy_fur.common.entity.ItemEntityModifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    @Inject(at = @At("HEAD"), method = "tick")
    public void fluffy_fur$tick(CallbackInfo ci) {
        ItemEntity self = (ItemEntity) ((Object) this);
        for (ItemEntityModifier modifier : ItemEntityHandler.getModifiers()) {
            if (modifier.isItem(self.level(), self, self.getItem())) {
                modifier.tick(self.level(), self, self.getItem());
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "hurt", cancellable = true)
    public void fluffy_fur$hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        ItemEntity self = (ItemEntity) ((Object) this);
        for (ItemEntityModifier modifier : ItemEntityHandler.getModifiers()) {
            if (modifier.isItem(self.level(), self, self.getItem())) {
                if (modifier.rejectHurt(self.level(), self, self.getItem(), source, amount)) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}
