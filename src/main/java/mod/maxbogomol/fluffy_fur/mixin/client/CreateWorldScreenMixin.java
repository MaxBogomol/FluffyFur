package mod.maxbogomol.fluffy_fur.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.gui.screen.EnhancedMenuHandler;
import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurModsHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin {

    @Inject(at = @At("HEAD"), method = "renderDirtBackground")
    public void fluffy_fur$renderDirtBackground(GuiGraphics guiGraphics, CallbackInfo ci) {
        if (Minecraft.getInstance().level == null) {
            FluffyFurModsHandler.ACTIVE_PANORAMA.render();
        }
        CreateWorldScreen self = (CreateWorldScreen) ((Object) this);
        RenderSystem.enableBlend();
        guiGraphics.blit(EnhancedMenuHandler.MENU_LIST_BACKGROUND_LOCATION, 0, 24, 0.0F, 0.0F, self.width, self.height - 36 - 25, 32, 32);
    }

    @ModifyArg(method = "renderDirtBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIFFIIII)V"))
    private ResourceLocation fluffy_fur$replaceDirtBackground(ResourceLocation value) {
        return EnhancedMenuHandler.MENU_BACKGROUND_LOCATION;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"))
    private ResourceLocation fluffy_fur$replaceFooterSeparator(ResourceLocation value) {
        return EnhancedMenuHandler.FOOTER_SEPERATOR_LOCATION;
    }
}
