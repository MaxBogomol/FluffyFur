package mod.maxbogomol.fluffy_fur.mixin.common;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.HangingEntityItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HangingEntityItem.class)
public abstract class HangingEntityItemMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"), method = "useOn", cancellable = true)
    public void fluffy_fur$useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Level level = context.getLevel();
        ItemStack stack = context.getItemInHand();
        if (!level.isClientSide()) {
            stack.shrink(1);
            cir.setReturnValue(InteractionResult.SUCCESS);
        } else {
            cir.setReturnValue(InteractionResult.FAIL);
        }
    }
}
