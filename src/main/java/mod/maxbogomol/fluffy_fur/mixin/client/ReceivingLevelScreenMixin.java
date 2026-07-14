package mod.maxbogomol.fluffy_fur.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurModsHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ReceivingLevelScreen.class)
public abstract class ReceivingLevelScreenMixin {

    @Inject(at = @At("HEAD"), method = "render")
    public void fluffy_fur$renderDirtBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        ReceivingLevelScreen self = (ReceivingLevelScreen) ((Object) this);
        FluffyFurModsHandler.ACTIVE_PANORAMA.render();
        RenderSystem.enableBlend();
        guiGraphics.blit(FluffyFurModsHandler.ACTIVE_OVERLAY, 0, 0, self.width, self.height, 0.0F, 0.0F, 16, 128, 16, 128);
    }
}
