package mod.maxbogomol.fluffy_fur.mixin.common;

import mod.maxbogomol.fluffy_fur.common.item.ICustomBlockEntityDataItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {

    @Inject(at = @At("RETURN"), method = "getBlockEntityData", cancellable = true)
    private static void fluffy_fur$getBlockEntityData(ItemStack stack, CallbackInfoReturnable<CompoundTag> cir) {
        if (stack.getItem() instanceof ICustomBlockEntityDataItem customBlockEntityDataItem) {
            CompoundTag tileNbt = cir.getReturnValue();
            if (tileNbt == null) tileNbt = new CompoundTag();
            cir.setReturnValue(customBlockEntityDataItem.getCustomBlockEntityData(stack, tileNbt));
        }
    }
}
