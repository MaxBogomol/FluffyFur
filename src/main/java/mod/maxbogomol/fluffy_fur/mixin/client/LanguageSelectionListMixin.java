package mod.maxbogomol.fluffy_fur.mixin.client;

import mod.maxbogomol.fluffy_fur.config.FluffyFurClientConfig;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.gui.screens.LanguageSelectScreen$LanguageSelectionList")
public abstract class LanguageSelectionListMixin {

    @Inject(at = @At("HEAD"), method = "renderBackground(Lnet/minecraft/client/gui/GuiGraphics;)V", cancellable = true)
    private void onRenderBackground(GuiGraphics pGuiGraphics, CallbackInfo ci) {
        if (FluffyFurClientConfig.ENHANCED_MENU.get()) {
            ci.cancel();
        }
    }
}
