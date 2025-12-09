package mod.maxbogomol.fluffy_fur.registry.client;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.font.FontHandler;
import net.minecraft.client.gui.Font;
import net.minecraft.resources.ResourceLocation;

public class FluffyFurFonts {
    public static ResourceLocation FISHII_LOCATION = new ResourceLocation(FluffyFur.MOD_ID, "fishii");
    public static ResourceLocation ICONS_LOCATION = new ResourceLocation(FluffyFur.MOD_ID, "icons");
    public static Font FISHII = FontHandler.createFont(FISHII_LOCATION);
    public static Font ICONS = FontHandler.createFont(ICONS_LOCATION);
}
