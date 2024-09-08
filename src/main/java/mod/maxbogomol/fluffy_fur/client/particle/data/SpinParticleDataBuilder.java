package mod.maxbogomol.fluffy_fur.client.particle.data;

import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import net.minecraft.util.RandomSource;

import java.util.Random;

public class SpinParticleDataBuilder extends GenericParticleDataBuilder {
    protected float spinOffset;
    protected float rsp1 = 0, rsp2 = 0;
    protected float rso1 = 0, rso2 = 0;

    public static final Random random = new Random();

    protected SpinParticleDataBuilder(float startingValue, float middleValue, float endingValue) {
        super(startingValue, middleValue, endingValue);
    }

    public SpinParticleDataBuilder setSpinOffset(float spinOffset) {
        this.spinOffset = spinOffset;
        return this;
    }

    public SpinParticleDataBuilder randomSpinOffset(RandomSource random) {
        this.spinOffset = random.nextFloat() * 6.28f;
        return this;
    }

    public SpinParticleDataBuilder randomOffset() {
        this.rso2 = 6.28f;
        return this;
    }

    public SpinParticleDataBuilder randomOffset(float spinOffset) {
        this.rso2 = spinOffset;
        return this;
    }

    public SpinParticleDataBuilder randomOffset(float spinOffset1, float spinOffset2) {
        this.rso1 = spinOffset1;
        this.rso2 = spinOffset2;
        return this;
    }

    public SpinParticleDataBuilder randomSpin(float spin) {
        this.rsp2 = spin;
        return this;
    }

    public SpinParticleDataBuilder randomSpin(float spin1, float spin2) {
        this.rsp1 = spin1;
        this.rsp2 = spin2;
        return this;
    }

    @Override
    public SpinParticleDataBuilder setCoefficient(float coefficient) {
        return (SpinParticleDataBuilder) super.setCoefficient(coefficient);
    }

    @Override
    public SpinParticleDataBuilder setEasing(Easing easing) {
        return (SpinParticleDataBuilder) super.setEasing(easing);
    }

    @Override
    public SpinParticleDataBuilder setEasing(Easing easing, Easing middleToEndEasing) {
        return (SpinParticleDataBuilder) super.setEasing(easing, middleToEndEasing);
    }

    @Override
    public SpinParticleData build() {
        return new SpinParticleData(spinOffset, rsp1, rsp2, rso1, rso2, startingValue, middleValue, endingValue, rs1, rs2, rm1, rm2, re1, re2, coefficient, startToMiddleEasing, middleToEndEasing);
    }
}
