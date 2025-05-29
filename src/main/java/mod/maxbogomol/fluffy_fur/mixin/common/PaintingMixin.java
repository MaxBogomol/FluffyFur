package mod.maxbogomol.fluffy_fur.mixin.common;

import mod.maxbogomol.fluffy_fur.registry.common.painting.FluffyFurPaintingTags;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Painting.class)
public abstract class PaintingMixin {

    @Shadow public abstract Holder<PaintingVariant> getVariant();

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/decoration/Painting;spawnAtLocation(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;"), method = "dropItem", cancellable = true)
    public void fluffy_fur$dropItem(Entity broken, CallbackInfo ci) {
        Painting self = (Painting) (Object) this;
        if (getVariant().is(FluffyFurPaintingTags.DROPPABLE)) {
            ItemStack paintingItem = Items.PAINTING.getDefaultInstance();
            CompoundTag compoundTag = paintingItem.getOrCreateTagElement("EntityTag");
            Painting.storeVariant(compoundTag, getVariant());
            self.spawnAtLocation(paintingItem);
            ci.cancel();
        }
    }
}
