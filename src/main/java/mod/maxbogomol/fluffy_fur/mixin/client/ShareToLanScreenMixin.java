package mod.maxbogomol.fluffy_fur.mixin.client;

import mod.maxbogomol.fluffy_fur.client.gui.screen.HardcoreShareToLanScreenHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ShareToLanScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShareToLanScreen.class)
public abstract class ShareToLanScreenMixin {

    @Inject(at = @At("RETURN"), method = "render")
    public void fluffy_fur$render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        ShareToLanScreen self = (ShareToLanScreen) ((Object) this);
        HardcoreShareToLanScreenHandler.render(self, guiGraphics, mouseX, mouseY, partialTick);
    }

    @Inject(at = @At("RETURN"), method = "tick")
    public void fluffy_fur$tick(CallbackInfo ci) {
        ShareToLanScreen self = (ShareToLanScreen) ((Object) this);
        HardcoreShareToLanScreenHandler.tick(self);
    }
}
