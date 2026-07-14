package mod.maxbogomol.fluffy_fur.mixin.client;

import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurModsHandler;
import mod.maxbogomol.fluffy_fur.config.FluffyFurClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.worldselection.EditGameRulesScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EditGameRulesScreen.class)
public abstract class EditGameRulesScreenMixin {

    @Inject(at = @At("HEAD"), method = "render")
    public void fluffy_fur$renderEnhancedBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (FluffyFurClientConfig.ENHANCED_MENU.get()) {
            if (Minecraft.getInstance().level == null) {
                FluffyFurModsHandler.ACTIVE_PANORAMA.render();

                EditGameRulesScreen self = (EditGameRulesScreen) ((Object) this);
                self.renderDirtBackground(guiGraphics);
            }
        }
    }
}
