package mod.maxbogomol.fluffy_fur.mixin.client;

import mod.maxbogomol.fluffy_fur.config.FluffyFurClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderStateShard;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderStateShard.class)
public class RenderStateShardMixin {

    @Shadow @Final protected String name;

    @Inject(at = @At("HEAD"), method = "setupRenderState", cancellable = true)
    private void fluffy_fur$setupRenderState(CallbackInfo ci) {
        if (Minecraft.useShaderTransparency()) {
            if (FluffyFurClientConfig.FABULOUS_WEATHER_FIX.get()) {
                if (name.equals("weather_target")) {
                    ci.cancel();
                }
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "clearRenderState", cancellable = true)
    private void fluffy_fur$clearRenderState(CallbackInfo ci) {
        if (Minecraft.useShaderTransparency()) {
            if (FluffyFurClientConfig.FABULOUS_WEATHER_FIX.get()) {
                if (name.equals("weather_target")) {
                    ci.cancel();
                }
            }
        }
    }
}