package mod.maxbogomol.fluffy_fur.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurModsHandler;
import mod.maxbogomol.fluffy_fur.config.FluffyFurClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SelectWorldScreen.class)
public abstract class SelectWorldScreenMixin {

    @Inject(at = @At("HEAD"), method = "render")
    public void fluffy_fur$renderEnhancedBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (FluffyFurClientConfig.ENHANCED_MENU.get()) {
            if (Minecraft.getInstance().level == null) {
                SelectWorldScreen self = (SelectWorldScreen) ((Object) this);
                FluffyFurModsHandler.ACTIVE_PANORAMA.render();
                RenderSystem.enableBlend();
                guiGraphics.blit(FluffyFurModsHandler.ACTIVE_OVERLAY, 0, 0, self.width, self.height, 0.0F, 0.0F, 16, 128, 16, 128);
                self.renderDirtBackground(guiGraphics);
            }
        }
    }
}
