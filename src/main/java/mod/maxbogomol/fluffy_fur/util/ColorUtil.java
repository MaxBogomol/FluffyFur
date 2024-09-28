package mod.maxbogomol.fluffy_fur.util;

import java.awt.*;
import java.util.ArrayList;

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

    public static Color getColor(int color) {
        return new Color(getRed(color), getGreen(color), getBlue(color), getAlpha(color));
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

    public static Color getBlendColor(ArrayList<Color> colors) {
        float f = 0.0F;
        float f1 = 0.0F;
        float f2 = 0.0F;
        int j = 0;

        for(Color color : colors) {
            int k = ColorUtil.packColor(color);
            f += (float) ((k >> 16 & 255)) / 255.0F;
            f1 += (float) ((k >> 8 & 255)) / 255.0F;
            f2 += (float) ((k >> 0 & 255)) / 255.0F;
            j += 1;
        }

        f = f / (float)j * 255.0F;
        f1 = f1 / (float)j * 255.0F;
        f2 = f2 / (float)j * 255.0F;
        return ColorUtil.getColor((int) f << 16 | (int) f1 << 8 | (int) f2);
    }
}