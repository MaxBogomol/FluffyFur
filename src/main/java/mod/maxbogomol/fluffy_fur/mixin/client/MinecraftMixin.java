package mod.maxbogomol.fluffy_fur.mixin.client;

import mod.maxbogomol.fluffy_fur.FluffyFurClient;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

    @Inject(at = @At("RETURN"), method = "isDemo", cancellable = true)
    public void fluffy_fur$isDemo(CallbackInfoReturnable<Boolean> cir) {
        if (FluffyFurClient.optifinePresent) {
            cir.setReturnValue(true);
        }
    }
}
