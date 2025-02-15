package mod.maxbogomol.fluffy_fur.mixin.common;

import mod.maxbogomol.fluffy_fur.common.network.FluffyFurPacketHandler;
import mod.maxbogomol.fluffy_fur.common.network.effect.ExplosionPacket;
import net.minecraft.core.BlockPos;
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

    @Inject(at = @At("RETURN"), method = "finalizeExplosion")
    public void fluffy_fur$finalizeExplosion(boolean spawnParticles, CallbackInfo ci) {
        if (!level.isClientSide()) {
            if (spawnParticles) {
                Explosion self = (Explosion) ((Object) this);
                Vec3 pos = self.getPosition();
                FluffyFurPacketHandler.sendToTracking(level, BlockPos.containing(pos), new ExplosionPacket(pos, radius));
            }
        }
    }
}
