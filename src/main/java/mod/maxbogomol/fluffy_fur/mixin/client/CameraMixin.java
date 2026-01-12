package mod.maxbogomol.fluffy_fur.mixin.client;

import mod.maxbogomol.fluffy_fur.client.screenshake.ScreenshakeHandler;
import mod.maxbogomol.fluffy_fur.registry.common.FluffyFurAttributes;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Shadow
    protected abstract void move(double distanceOffset, double verticalOffset, double horizontalOffset);

    @Inject(method = "setup", at = @At("RETURN"))
    private void fluffy_fur$setup(BlockGetter area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        Camera self = (Camera) ((Object) this);
        if (thirdPerson) {
            if (focusedEntity instanceof Player player) {
                AttributeInstance attribute = player.getAttribute(FluffyFurAttributes.CAMERA_DISTANCE_AMPLIFIER.get());
                if (attribute != null) this.move(-self.getMaxZoom(attribute.getValue()), 0, 0);
            }
        }

        ScreenshakeHandler.cameraTick(self);
    }
}