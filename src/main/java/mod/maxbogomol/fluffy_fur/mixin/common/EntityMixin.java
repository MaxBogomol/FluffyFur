package mod.maxbogomol.fluffy_fur.mixin.common;

import mod.maxbogomol.fluffy_fur.common.entity.ITouchingFluid;
import mod.maxbogomol.fluffy_fur.common.fluid.CustomFluidType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements ITouchingFluid {

    @Shadow
    private Vec3 position;

    @Shadow
    private float eyeHeight;

    @Unique
    private boolean fluffy_fur$isTouchingFluid = false;

    @Override
    public boolean fluffy_fur$isTouchingFluid() {
        return this.fluffy_fur$isTouchingFluid;
    }

    @Override
    public void fluffy_fur$setTouchingFluid(boolean touchingFluid) {
        this.fluffy_fur$isTouchingFluid = touchingFluid;
    }

    @Inject(method = "updateInWaterStateAndDoWaterCurrentPushing", at = @At("TAIL"))
    private void fluffy_fur$updateInWaterStateAndDoWaterCurrentPushing(CallbackInfo ci) {
        Entity self = (Entity) ((Object) this);
        FluidType fluid = self.level().getFluidState(self.blockPosition()).getFluidType();
        if (fluid instanceof CustomFluidType fluidType) {
            if (!fluffy_fur$isTouchingFluid() && self.tickCount > 0) {
                Entity entity = (Entity) (self.isVehicle() && self.getControllingPassenger() != null ? self.getControllingPassenger() : this);
                float f = entity == self ? 0.2F : 0.9F;
                Vec3 vec3 = entity.getDeltaMovement();
                float f1 = Math.min(1.0F, (float) Math.sqrt(vec3.x * vec3.x * (double) 0.2F + vec3.y * vec3.y + vec3.z * vec3.z * (double) 0.2F) * f);
                if (f1 < 0.25F) {
                    if (fluidType.getSwimSound() != null) self.playSound(fluidType.getSwimSound(), f1, 1.0F + (self.random.nextFloat() - self.random.nextFloat()) * 0.4F);
                } else {
                    if (fluidType.getSplashSound() != null) self.playSound(fluidType.getSplashSound(), f1, 1.0F + (self.random.nextFloat() - self.random.nextFloat()) * 0.4F);
                }

                float f2 = (float) Mth.floor(self.getY());

                if (fluidType.getBubleParticle() != null) {
                    for (int i = 0; (float) i < 1.0F + self.dimensions.width * 20.0F; ++i) {
                        double d0 = (self.random.nextDouble() * 2.0D - 1.0D) * (double) self.dimensions.width;
                        double d1 = (self.random.nextDouble() * 2.0D - 1.0D) * (double) self.dimensions.width;
                        self.level().addParticle(fluidType.getBubleParticle(), self.getX() + d0, (f2 + 1.0F), self.getZ() + d1, vec3.x, vec3.y - self.random.nextDouble() * (double) 0.2F, vec3.z);
                    }
                }

                if (fluidType.getSplashParticle() != null) {
                    for (int j = 0; (float) j < 1.0F + self.dimensions.width * 20.0F; ++j) {
                        double d2 = (self.random.nextDouble() * 2.0D - 1.0D) * (double) self.dimensions.width;
                        double d3 = (self.random.nextDouble() * 2.0D - 1.0D) * (double) self.dimensions.width;
                        self.level().addParticle(fluidType.getSplashParticle(), self.getX() + d2, (f2 + 1.0F), self.getZ() + d3, vec3.x, vec3.y, vec3.z);
                    }
                }

                self.gameEvent(GameEvent.SPLASH);
            }

            fluffy_fur$setTouchingFluid(true);
        } else {
            fluffy_fur$setTouchingFluid(false);
        }
    }

    @Inject(method = "Lnet/minecraft/world/entity/Entity;getEyeHeight()F", at = @At("RETURN"), cancellable = true)
    private void fluffy_fur$getEyeHeight(CallbackInfoReturnable<Float> cir) {
        Entity self = (Entity) ((Object) this);
        if (self instanceof Player player) {
            float f = 1.63f / 1.875f;
            cir.setReturnValue(cir.getReturnValue() * f);
        }
    }

    @Inject(method = "getBbWidth", at = @At("RETURN"), cancellable = true)
    private void fluffy_fur$getBbWidth(CallbackInfoReturnable<Float> cir) {
        Entity self = (Entity) ((Object) this);
        if (self instanceof Player player) {
            float f = 1.63f / 1.875f;
            cir.setReturnValue(cir.getReturnValue() * f);
        }
    }

    @Inject(method = "getBbHeight", at = @At("RETURN"), cancellable = true)
    private void fluffy_fur$getBbHeight(CallbackInfoReturnable<Float> cir) {
        Entity self = (Entity) ((Object) this);
        if (self instanceof Player player) {
            float f = 1.63f / 1.875f;
            cir.setReturnValue(cir.getReturnValue() * f);
        }
    }

    @Inject(method = "getEyeY", at = @At("RETURN"), cancellable = true)
    private void fluffy_fur$getEyeY(CallbackInfoReturnable<Double> cir) {
        Entity self = (Entity) ((Object) this);
        if (self instanceof Player player) {
            float f = 1.63f / 1.875f;
            cir.setReturnValue(this.position.y + (this.eyeHeight * f));
        }
    }
}
