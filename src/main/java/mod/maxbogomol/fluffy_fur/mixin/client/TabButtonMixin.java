package mod.maxbogomol.fluffy_fur.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.fluffy_fur.client.gui.screen.EnhancedMenuHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.TabButton;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TabButton.class)
public abstract class TabButtonMixin {

    @Inject(at = @At("HEAD"), method = "renderWidget")
    public void fluffy_fur$renderDirtBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        RenderSystem.enableBlend();
    }

    @ModifyArg(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitNineSliced(Lnet/minecraft/resources/ResourceLocation;IIIIIIIIIIII)V"))
    private ResourceLocation fluffy_fur$replaceTabButton(ResourceLocation value) {
        return EnhancedMenuHandler.TAB_BUTTON_LOCATION;
    }
}
