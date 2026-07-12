package mod.maxbogomol.fluffy_fur.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.fluffy_fur.client.gui.screen.EnhancedMenuHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.worldselection.ExperimentsScreen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperimentsScreen.class)
public abstract class ExperimentsScreenMixin {

    @Final
    @Shadow
    private HeaderAndFooterLayout layout;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V"))
    public void fluffy_fur$renderDirtBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        ExperimentsScreen self = (ExperimentsScreen) ((Object) this);
        RenderSystem.enableBlend();
        guiGraphics.blit(EnhancedMenuHandler.HEADER_SEPERATOR_LOCATION, 0, this.layout.getHeaderHeight() - 2, 0, 0, self.width, 2, 16, 2);
        guiGraphics.blit(EnhancedMenuHandler.FOOTER_SEPERATOR_LOCATION, 0, self.height - this.layout.getFooterHeight(), 0, 0, self.width, 2, 16, 2);
        RenderSystem.disableBlend();
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"))
    private ResourceLocation fluffy_fur$replaceDirtBackground(ResourceLocation value) {
        return EnhancedMenuHandler.MENU_LIST_BACKGROUND_LOCATION;
    }
}
