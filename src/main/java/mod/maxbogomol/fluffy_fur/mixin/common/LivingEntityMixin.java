package mod.maxbogomol.fluffy_fur.mixin.common;

import mod.maxbogomol.fluffy_fur.registry.common.FluffyFurAttributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "getJumpBoostPower", at = @At("RETURN"), cancellable = true)
    private void fluffy_fur$getJumpBoostPower(CallbackInfoReturnable<Float> cir) {
        Entity self = (Entity) ((Object) this);
        if (self instanceof Player player) {
            AttributeInstance attribute = player.getAttribute(FluffyFurAttributes.JUMP_HEIGHT_AMPLIFIER.get());
            if (attribute != null) cir.setReturnValue(cir.getReturnValue() + ((float) attribute.getValue()));
        }
    }
}
