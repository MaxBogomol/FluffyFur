package mod.maxbogomol.fluffy_fur.mixin.common;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Inject(at = @At("RETURN"), method = "<init>")
    private void fluffy_fur$Player(Level level, BlockPos pos, float yRot, GameProfile gameProfile, CallbackInfo ci) {
        Player self = (Player) ((Object) this);
        self.refreshDimensions();
    }

    @Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
    private void fluffy_fur$getDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        Player self = (Player) ((Object) this);
        EntityDimensions entityDimensions = cir.getReturnValue();
        float f = 1.63f / 1.875f;
        cir.setReturnValue(new EntityDimensions(entityDimensions.width * f, entityDimensions.height * f, false));
    }
}
