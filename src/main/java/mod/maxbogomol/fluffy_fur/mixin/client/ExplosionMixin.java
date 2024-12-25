package mod.maxbogomol.fluffy_fur.mixin.client;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.effect.FluffyFurEffects;
import mod.maxbogomol.fluffy_fur.config.FluffyFurClientConfig;
import net.minecraft.world.level.Explosion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {

    @Final
    @Shadow
    private float radius;

    @Inject(at = @At("RETURN"), method = "finalizeExplosion")
    public void fluffy_fur$tick(boolean spawnParticles, CallbackInfo ci) {
        if (FluffyFur.proxy.getLevel().isClientSide()) {
            if (FluffyFurClientConfig.EXPLOSION_EFFECT.get()) {
                if (spawnParticles) {
                    Explosion self = (Explosion) ((Object) this);
                    FluffyFurEffects.explosionEffect(FluffyFur.proxy.getLevel(), self.getPosition(), radius);
                }
            }
        }
    }
}
