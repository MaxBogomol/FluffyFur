package mod.maxbogomol.fluffy_fur.client.font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontManager;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class FontHandler {

    public static Font createFont(ResourceLocation resourceLocation, boolean filterFishyGlyphs) {
        return new Font(font -> getFontSets().getOrDefault(resourceLocation, getMissingFontSet()), filterFishyGlyphs);
    }

    public static Font createFont(ResourceLocation resourceLocation) {
        return createFont(resourceLocation, false);
    }

    public static FontManager getFontManager() {
        return Minecraft.getInstance().fontManager;
    }

    public static Map<ResourceLocation, FontSet> getFontSets() {
        return getFontManager().fontSets;
    }

    public static FontSet getMissingFontSet() {
        return getFontManager().missingFontSet;
    }
}
