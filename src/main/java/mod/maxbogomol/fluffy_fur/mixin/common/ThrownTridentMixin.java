package mod.maxbogomol.fluffy_fur.mixin.common;

import mod.maxbogomol.fluffy_fur.config.FluffyFurConfig;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.projectile.ThrownTrident;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownTrident.class)
public abstract class ThrownTridentMixin {

    @Shadow
    private boolean dealtDamage;

    @Final
    @Shadow
    private static EntityDataAccessor<Byte> ID_LOYALTY;

    @Inject(at = @At("HEAD"), method = "tick")
    public void fluffy_fur$tick(CallbackInfo ci) {
        if (FluffyFurConfig.TRIDENT_LOYALTY_VOID.get()) {
            ThrownTrident self = (ThrownTrident) ((Object) this);
            int i = self.getEntityData().get(ID_LOYALTY);
            if (i > 0 && self.position().y() < self.level().dimensionType().minY() + FluffyFurConfig.VOID_HEIGHT.get()) {
                dealtDamage = true;
            }
        }
    }
}
