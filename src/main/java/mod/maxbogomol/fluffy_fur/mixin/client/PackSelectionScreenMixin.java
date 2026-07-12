package mod.maxbogomol.fluffy_fur.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PackSelectionScreen.class)
public abstract class PackSelectionScreenMixin {

    @Inject(method = "render", at = @At(value = "HEAD"))
    public void fluffy_fur$render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (Minecraft.getInstance().level != null) {
            PackSelectionScreen self = (PackSelectionScreen) ((Object) this);
            RenderSystem.enableBlend();
            self.renderBackground(guiGraphics);
        }
    }
}
