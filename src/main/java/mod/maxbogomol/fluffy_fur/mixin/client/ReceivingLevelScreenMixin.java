package mod.maxbogomol.fluffy_fur.mixin.client;

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
        FluffyFurModsHandler.ACTIVE_PANORAMA.render();
    }
}
