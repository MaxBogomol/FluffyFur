package mod.maxbogomol.fluffy_fur.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.Tesselator;
import mod.maxbogomol.fluffy_fur.client.gui.screen.EnhancedMenuHandler;
import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurModsHandler;
import mod.maxbogomol.fluffy_fur.config.FluffyFurClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.widget.ScrollPanel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScrollPanel.class)
public abstract class ScrollPanelMixin {

    @Inject(at = @At("HEAD"), method = "drawBackground", remap = false)
    public void fluffy_fur$renderEnhancedBackground(GuiGraphics guiGraphics, Tesselator tess, float partialTick, CallbackInfo ci) {
        if (FluffyFurClientConfig.ENHANCED_MENU.get()) {
            if (Minecraft.getInstance().level == null) {
                FluffyFurModsHandler.ACTIVE_PANORAMA.render();
                RenderSystem.enableBlend();
                guiGraphics.blit(FluffyFurModsHandler.ACTIVE_OVERLAY, 0, 0, Minecraft.getInstance().screen.width, Minecraft.getInstance().screen.height, 0.0F, 0.0F, 16, 128, 16, 128);
            }
        }
    }

    @ModifyArg(method = "drawBackground", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V"))
    private ResourceLocation fluffy_fur$replaceDirtBackground(ResourceLocation value) {
        if (FluffyFurClientConfig.ENHANCED_MENU.get()) {
            return EnhancedMenuHandler.MENU_LIST_BACKGROUND_LOCATION;
        }
        return value;
    }
}
