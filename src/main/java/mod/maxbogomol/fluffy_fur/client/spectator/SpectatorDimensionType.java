package mod.maxbogomol.fluffy_fur.client.spectator;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class SpectatorDimensionType {
    public ResourceKey<Level> dimension;
    public ResourceLocation texture = new ResourceLocation(FluffyFur.MOD_ID, "textures/spectator_dimension/other.png");

    public SpectatorDimensionType(ResourceKey<Level> dimension, ResourceLocation texture) {
        this.dimension = dimension;
        this.texture = texture;
    }

    public SpectatorDimensionType(ResourceLocation texture) {
        this.texture = texture;
    }

    public SpectatorDimensionType() {

    }

    public void renderIcon(GuiGraphics guiGraphics, float shadeColor, int alpha) {
        guiGraphics.blit(texture, 0, 0, 0, 0, 16, 16, 16, 16);
    }
}
