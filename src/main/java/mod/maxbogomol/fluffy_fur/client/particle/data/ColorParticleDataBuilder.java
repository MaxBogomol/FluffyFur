package mod.maxbogomol.fluffy_fur.client.particle.data;

import mod.maxbogomol.fluffy_fur.common.easing.Easing;

import java.awt.*;

public class ColorParticleDataBuilder {

    protected float r1, g1, b1, r2, g2, b2;
    protected float rr11 = -1, rr12 = -1, rb11 = -1, rb12 = -1, rg11 = -1, rg12 = -1, rr21 = -1, rr22 = -1, rb21 = -1, rb22 = -1, rg21 = -1, rg22 = -1;
    protected float colorCoefficient = 1f;

    protected Easing colorCurveEasing = Easing.LINEAR;

    protected ColorParticleDataBuilder(float r1, float g1, float b1, float r2, float g2, float b2) {
        this.r1 = r1;
        this.g1 = g1;
        this.b1 = b1;
        this.r2 = r2;
        this.g2 = g2;
        this.b2 = b2;
    }

    public ColorParticleDataBuilder setCoefficient(float coefficient) {
        this.colorCoefficient = coefficient;
        return this;
    }

    public ColorParticleDataBuilder setEasing(Easing easing) {
        this.colorCurveEasing = easing;
        return this;
    }

    public ColorParticleDataBuilder setRandomColor(float rr11, float rr12, float rb11, float rb12, float rg11, float rg12, float rr21, float rr22, float rb21, float rb22, float rg21, float rg22) {
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
        return this;
    }

    public ColorParticleDataBuilder setRandomColor(float rr11, float rr12, float rb11, float rb12, float rg11, float rg12) {
        this.rr11 = rr11;
        this.rr12 = rr12;
        this.rb11 = rb11;
        this.rb12 = rb12;
        this.rg11 = rg11;
        this.rg12 = rg12;
        return this;
    }

    public ColorParticleDataBuilder setRandomColor(Color start1, Color start2, Color end1, Color end2) {
        setRandomColor(start1.getRed() / 255f, start2.getRed() / 255f, start1.getGreen() / 255f, start2.getGreen() / 255f, start1.getBlue() / 255f, start2.getBlue() / 255f, end1.getRed() / 255f, end2.getRed() / 255f, end1.getGreen() / 255f, end2.getGreen() / 255f, end1.getBlue() / 255f, end2.getBlue() / 255f);
        return this;
    }

    public ColorParticleDataBuilder setRandomColor(Color start1, Color start2) {
        setRandomColor(start1.getRed() / 255f, start2.getRed() / 255f, start1.getGreen() / 255f, start2.getGreen() / 255f, start1.getBlue() / 255f, start2.getBlue() / 255f);
        return this;
    }

    public ColorParticleDataBuilder setRandomColor() {
        setRandomColor(0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1);
        return this;
    }

    public ColorParticleDataBuilder setRandomColorFlat() {
        setRandomColor(0, 1, 0, 1, 0, 1);
        return this;
    }

    public ColorParticleData build() {
        return new ColorParticleData(r1, g1, b1, r2, g2, b2, rr11, rr12, rb11, rb12, rg11, rg12, rr21, rr22, rb21, rb22, rg21, rg22, colorCoefficient, colorCurveEasing);
    }
}
