package mod.maxbogomol.fluffy_fur.mixin.client;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.config.FluffyFurClientConfig;
import mod.maxbogomol.fluffy_fur.common.item.IParticleItem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    @Shadow public abstract ItemStack getItem();

    @Inject(at = @At("RETURN"), method = "tick")
    public void fluffy_fur$addParticles(CallbackInfo ci) {
        ItemEntity self = (ItemEntity) ((Object) this);
        if (self.level().isClientSide) {
            if (FluffyFurClientConfig.ITEM_PARTICLE.get()) {
                if (self.getItem().getItem() instanceof IParticleItem) {
                    IParticleItem item = (IParticleItem) self.getItem().getItem();
                    item.addParticles(FluffyFur.proxy.getLevel(), self);
                }
            }
        }
    }
}
