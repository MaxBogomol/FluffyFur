package mod.maxbogomol.fluffy_fur.client.particle.data;

import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import net.minecraft.util.Mth;

import java.awt.*;

public class ColorParticleData {

    public final float r1, g1, b1, r2, g2, b2;
    public final float rr11, rr12, rb11, rb12, rg11, rg12, rr21, rr22, rb21, rb22, rg21, rg22;
    public final float colorCoefficient;
    public final Easing colorCurveEasing;

    public float coefficientMultiplier = 1;

    protected ColorParticleData(float r1, float g1, float b1, float r2, float g2, float b2, float rr11, float rr12, float rb11, float rb12, float rg11, float rg12, float rr21, float rr22, float rb21, float rb22, float rg21, float rg22, float colorCoefficient, Easing colorCurveEasing) {
        this.r1 = r1;
        this.g1 = g1;
        this.b1 = b1;
        this.r2 = r2;
        this.g2 = g2;
        this.b2 = b2;
        this.rr11 = rr11;
        this.rr12 = rr12;
        this.rb11 = rb11;
        this.rb12 = rb12;
        this.rg11 = rg11;
        this.rg12 = rg12;
        this.rr21 = rr21;
        this.rr22 = rr22;
        this.rb21 = rb21;
        this.rb22 = rb22;
        this.rg21 = rg21;
        this.rg22 = rg22;
        this.colorCoefficient = colorCoefficient;
        this.colorCurveEasing = colorCurveEasing;
    }

    public ColorParticleData multiplyCoefficient(float coefficientMultiplier) {
        this.coefficientMultiplier *= coefficientMultiplier;
        return this;
    }

    public ColorParticleData overrideCoefficientMultiplier(float coefficientMultiplier) {
        this.coefficientMultiplier = coefficientMultiplier;
        return this;
    }

    public float getProgress(float age, float lifetime) {
        return Mth.clamp((age * colorCoefficient * coefficientMultiplier) / lifetime, 0, 1);
    }

    public ColorParticleDataBuilder copy() {
        return create(r1, g1, b1, r2, g2, b2).setCoefficient(colorCoefficient).setEasing(colorCurveEasing);
    }

    public static ColorParticleDataBuilder create(float r1, float g1, float b1, float r2, float g2, float b2) {
        return new ColorParticleDataBuilder(r1, g1, b1, r2, g2, b2);
    }

    public static ColorParticleDataBuilder create(float r, float g, float b) {
        return new ColorParticleDataBuilder(r, g, b, r, g, b);
    }

    public static ColorParticleDataBuilder create(Color start, Color end) {
        return create(start.getRed() / 255f, start.getGreen() / 255f, start.getBlue() / 255f, end.getRed() / 255f, end.getGreen() / 255f, end.getBlue() / 255f);
    }

    public static ColorParticleDataBuilder create(Color color) {
        return create(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
    }

    public static ColorParticleDataBuilder create() {
        return create(0, 0, 0);
    }
}
