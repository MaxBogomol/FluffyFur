package mod.maxbogomol.fluffy_fur.mixin.client;

import mod.maxbogomol.fluffy_fur.client.gui.screen.EnhancedMenuHandler;
import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurModsHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ScreenMixin {

    @Inject(at = @At("HEAD"), method = "renderDirtBackground")
    public void fluffy_fur$renderDirtBackground(GuiGraphics guiGraphics, CallbackInfo ci) {
        if (Minecraft.getInstance().level == null) {
            FluffyFurModsHandler.ACTIVE_PANORAMA.render();
        }
    }

    @ModifyArg(method = "renderDirtBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIFFIIII)V"))
    private ResourceLocation fluffy_fur$replaceDirtBackground(ResourceLocation value) {
        if (Minecraft.getInstance().level != null) {
            Screen self = (Screen) ((Object) this);
            if (self instanceof PackSelectionScreen) {
                return EnhancedMenuHandler.EMPTY_LOCATION;
            }
        }
        return EnhancedMenuHandler.MENU_BACKGROUND_LOCATION;
    }
}
