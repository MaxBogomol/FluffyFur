package mod.maxbogomol.fluffy_fur.mixin.client;

import mod.maxbogomol.fluffy_fur.client.effect.FluffyFurEffects;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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

    @Final
    @Shadow
    private Level level;

    @Inject(method = "finalizeExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
    public void fluffy_fur$finalizeExplosion(boolean spawnParticles, CallbackInfo ci) {
        if (level.isClientSide()) {
            Explosion self = (Explosion) ((Object) this);
            Vec3 pos = self.getPosition();
            FluffyFurEffects.explosionEffect(level, pos, radius);
        }
    }
}
