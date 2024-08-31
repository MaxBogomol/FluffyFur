package mod.maxbogomol.fluffy_fur.util;

import java.awt.*;

public class ColorUtil {
    public static int getAlpha(int packedColor) {
        return packedColor >>> 24;
    }

    public static int getRed(int packedColor) {
        return packedColor >> 16 & 255;
    }

    public static int getGreen(int packedColor) {
        return packedColor >> 8 & 255;
    }

    public static int getBlue(int packedColor) {
        return packedColor & 255;
    }

    public static int packColor(int alpha, int red, int green, int blue) {
        return alpha << 24 | red << 16 | green << 8 | blue;
    }

    public static int packColor(Color color) {
        return color.getAlpha() << 24 | color.getRed() << 16 | color.getGreen() << 8 | color.getBlue();
    }

    public static Color rainbowColor(float ticks) {
        int r = (int) (Math.sin(ticks) * 127 + 128);
        int g = (int) (Math.sin(ticks + Math.PI / 2) * 127 + 128);
        int b = (int) (Math.sin(ticks + Math.PI) * 127 + 128);
        return new Color(r, g, b);
    }
}