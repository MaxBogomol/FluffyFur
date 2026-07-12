package mod.maxbogomol.fluffy_fur.client.gui.components;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FluffyFurLogoRenderer extends LogoRenderer {
    public ResourceLocation logo = new ResourceLocation(FluffyFur.MOD_ID, "textures/gui/menu/title/fluffy_fur.png");

    public FluffyFurLogoRenderer(ResourceLocation logo, boolean keepLogoThroughFade) {
        super(keepLogoThroughFade);
        this.logo = logo;
    }

    public FluffyFurLogoRenderer() {
        super(false);
    }

    public FluffyFurLogoRenderer setLogo(ResourceLocation logo) {
        this.logo = logo;
        return this;
    }

    @Override
    public void renderLogo(GuiGraphics guiGraphics, int screenWidth, float transparency) {
        this.renderLogo(guiGraphics, screenWidth, transparency, 30);
    }

    @Override
    public void renderLogo(GuiGraphics guiGraphics, int screenWidth, float transparency, int height) {
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.keepLogoThroughFade ? 1.0F : transparency);
        int i = screenWidth / 2 - 128;
        guiGraphics.blit(logo, i, height, 0.0F, 0.0F, 256, 64, 256, 64);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
