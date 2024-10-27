package mod.maxbogomol.fluffy_fur.mixin.client;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Fox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FoxRenderer.class)
public abstract class FoxRendererMixin {
    @Unique
    private static final ResourceLocation MAXBOGOMOL_LOCATION = new ResourceLocation(FluffyFur.MOD_ID,"textures/entity/fox/maxbogomol.png");
    @Unique
    private static final ResourceLocation MAXBOGOMOL_SLEEP_LOCATION = new ResourceLocation(FluffyFur.MOD_ID,"textures/entity/fox/maxbogomol_sleep.png");
    @Unique
    private static final ResourceLocation FOXPLANE_LOCATION = new ResourceLocation(FluffyFur.MOD_ID,"textures/entity/fox/foxplane.png");
    @Unique
    private static final ResourceLocation FOXPLANE_SLEEP_LOCATION = new ResourceLocation(FluffyFur.MOD_ID,"textures/entity/fox/foxplane_sleep.png");

    @Inject(at = @At("HEAD"), method = "getTextureLocation*", cancellable = true)
    public void fluffy_fur$getTextureLocation(Fox entity, CallbackInfoReturnable<ResourceLocation> ci) {
        String s = ChatFormatting.stripFormatting(entity.getName().getString());
        if ("MaxBogomol".equals(s)) {
            if (entity.isSleeping()) {
                ci.setReturnValue(MAXBOGOMOL_SLEEP_LOCATION);
            }
            ci.setReturnValue(MAXBOGOMOL_LOCATION);
        }
        if ("FoxPlane".equals(s)) {
            if (entity.isSleeping()) {
                ci.setReturnValue(FOXPLANE_SLEEP_LOCATION);
            }
            ci.setReturnValue(FOXPLANE_LOCATION);
        }
    }
}
