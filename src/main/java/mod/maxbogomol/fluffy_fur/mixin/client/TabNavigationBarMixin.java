package mod.maxbogomol.fluffy_fur.mixin.client;

import mod.maxbogomol.fluffy_fur.client.gui.screen.EnhancedMenuHandler;
import mod.maxbogomol.fluffy_fur.config.FluffyFurClientConfig;
import net.minecraft.client.gui.components.tabs.TabNavigationBar;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TabNavigationBar.class)
public abstract class TabNavigationBarMixin {

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"))
    private ResourceLocation fluffy_fur$replaceHeaderSeparator(ResourceLocation value) {
        if (FluffyFurClientConfig.ENHANCED_MENU.get()) {
            return EnhancedMenuHandler.HEADER_SEPERATOR_LOCATION;
        }
        return value;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"), index = 0)
    private int fluffy_fur$removeFIllX1(int value) {
        if (FluffyFurClientConfig.ENHANCED_MENU.get()) {
            return 0;
        }
        return value;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"), index = 1)
    private int fluffy_fur$removeFIllY1(int value) {
        if (FluffyFurClientConfig.ENHANCED_MENU.get()) {
            return 0;
        }
        return value;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"), index = 2)
    private int fluffy_fur$removeFIllX2(int value) {
        if (FluffyFurClientConfig.ENHANCED_MENU.get()) {
            return 0;
        }
        return value;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"), index = 3)
    private int fluffy_fur$removeFIllY2(int value) {
        if (FluffyFurClientConfig.ENHANCED_MENU.get()) {
            return 0;
        }
        return value;
    }
}
