package mod.maxbogomol.fluffy_fur.mixin.client;

import mod.maxbogomol.fluffy_fur.client.effect.FluffyFurEffects;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningBolt.class)
public abstract class LightningBoltMixin {

    @Shadow
    private int life;

    @Inject(at = @At("HEAD"), method = "tick")
    public void fluffy_fur$tick(CallbackInfo ci) {
        LightningBolt self = (LightningBolt) ((Object) this);
        Level level = self.level();
        if (level.isClientSide()) {
            Vec3 pos = self.position();
            if (life == 2) {
                FluffyFurEffects.lightningBoltSpawnEffect(level, pos);
            }
            FluffyFurEffects.lightningBoltTickEffect(level, pos);
        }
    }
}
