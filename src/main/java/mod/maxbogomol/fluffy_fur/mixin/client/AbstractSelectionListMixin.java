package mod.maxbogomol.fluffy_fur.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.fluffy_fur.client.gui.screen.EnhancedMenuHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSelectionList.class)
public abstract class AbstractSelectionListMixin {

    @Shadow
    private boolean renderBackground;
    @Shadow
    private boolean renderTopAndBottom;

    @Shadow
    protected int x0;
    @Shadow
    protected int y0;
    @Shadow
    protected int x1;
    @Shadow
    protected int y1;

    @Shadow
    public abstract double getScrollAmount();

    @Inject(at = @At("HEAD"), method = "render")
    public void fluffy_fur$renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (renderBackground) {
            RenderSystem.enableBlend();
            guiGraphics.blit(EnhancedMenuHandler.MENU_LIST_BACKGROUND_LOCATION, this.x0, this.y0, (float) this.x1, (float) (this.y1 + (int) this.getScrollAmount()), this.x1 - this.x0, this.y1 - this.y0, 32, 32);
            RenderSystem.disableBlend();
        }
        if (renderTopAndBottom) {
            RenderSystem.enableBlend();
            guiGraphics.blit(EnhancedMenuHandler.HEADER_SEPERATOR_LOCATION, this.x0, this.y0 - 2, 0, 0, this.x1 - this.x0, 2, 16, 2);
            guiGraphics.blit(EnhancedMenuHandler.FOOTER_SEPERATOR_LOCATION, this.x0, this.y1, 0, 0, this.x1 - this.x0, 2, 16, 2);
            RenderSystem.disableBlend();
        }
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"))
    private ResourceLocation fluffy_fur$replaceDirtBackground(ResourceLocation value) {
        return EnhancedMenuHandler.EMPTY_LOCATION;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fillGradient(Lnet/minecraft/client/renderer/RenderType;IIIIIII)V"), index = 1)
    private int fluffy_fur$removeGradientX1(int value) {
        if (renderTopAndBottom) {
            return 0;
        }
        return value;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fillGradient(Lnet/minecraft/client/renderer/RenderType;IIIIIII)V"), index = 2)
    private int fluffy_fur$removeGradientY1(int value) {
        if (renderTopAndBottom) {
            return 0;
        }
        return value;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fillGradient(Lnet/minecraft/client/renderer/RenderType;IIIIIII)V"), index = 3)
    private int fluffy_fur$removeGradientX2(int value) {
        if (renderTopAndBottom) {
            return 0;
        }
        return value;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fillGradient(Lnet/minecraft/client/renderer/RenderType;IIIIIII)V"), index = 4)
    private int fluffy_fur$removeGradientY2(int value) {
        if (renderTopAndBottom) {
            return 0;
        }
        return value;
    }
}
