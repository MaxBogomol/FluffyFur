package mod.maxbogomol.fluffy_fur.mixin.client.sodium;

import com.mojang.blaze3d.vertex.PoseStack;
import me.jellysquid.mods.sodium.client.render.immediate.CloudRenderer;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.config.FluffyFurClientConfig;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CloudRenderer.class)
public abstract class CloudRendererMixin implements IMixinConfigPlugin {
    @Unique
    private float fluffy_fur$partialTick;

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.startsWith("me.jellysquid.mods.sodium.client.render.immediate.CloudRenderer")) {
            return ModList.get().isLoaded("embeddium") || ModList.get().isLoaded("rubidium") || ModList.get().isLoaded("sodium");
        }
        return false;
    }

    @Inject(method = "render", at = @At(value = "HEAD"), remap = false)
    private void fluffy_fur$renderClouds(ClientLevel level, LocalPlayer player, PoseStack poseStack, Matrix4f projectionMatrix, float ticks, float partialTick, double camX, double camY, double camZ, CallbackInfo ci) {
        fluffy_fur$partialTick = partialTick;
    }

    @ModifyVariable(method = "render", at = @At("STORE"), ordinal = 3, remap = false)
    private double fluffy_fur$render(double value) {
        if (FluffyFurClientConfig.DAYLIGHT_CLOUDS.get()) {
            Level level = FluffyFur.proxy.getLevel();
            if (level != null) {
                boolean daylight = level.getGameRules().getRule(GameRules.RULE_DAYLIGHT).get();
                if (!daylight) fluffy_fur$partialTick = 0;
                if (daylight || FluffyFurClientConfig.DAYLIGHT_CLOUDS_IGNORE.get()) {
                    value = ((level.getDayTime() + fluffy_fur$partialTick) * 0.03F);
                }
            }
        }
        return value;
    }
}
