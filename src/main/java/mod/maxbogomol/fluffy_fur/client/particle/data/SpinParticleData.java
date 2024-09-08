package mod.maxbogomol.fluffy_fur.client.particle.data;

import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import net.minecraft.util.RandomSource;

public class SpinParticleData extends GenericParticleData {

    public final float spinOffset;
    public final float rsp1, rsp2;
    public final float rso1, rso2;

    protected SpinParticleData(float spinOffset, float rsp1, float rsp2, float rso1, float rso2, float startingValue, float middleValue, float endingValue, float rs1, float rs2, float rm1, float rm2, float re1, float re2, float coefficient, Easing startToMiddleEasing, Easing middleToEndEasing) {
        super(startingValue, middleValue, endingValue, rs1, rs2, rm1, rm2, re1, re2, coefficient, startToMiddleEasing, middleToEndEasing);
        this.spinOffset = spinOffset;
        this.rsp1 = rsp1;
        this.rsp2 = rsp2;
        this.rso1 = rso1;
        this.rso2 = rso2;
    }

    @Override
    public SpinParticleData copy() {
        return new SpinParticleData(spinOffset, rsp1, rsp2, rso1, rso2, startingValue, middleValue, endingValue, rs1, rs2, rm1, rm2, re1, re2, coefficient, startToMiddleEasing, middleToEndEasing).overrideValueMultiplier(valueMultiplier).overrideCoefficientMultiplier(coefficientMultiplier);
    }

    @Override
    public SpinParticleData bake() {
        return new SpinParticleData(spinOffset, rsp1, rsp2, rso1, rso2, startingValue*valueMultiplier, middleValue*valueMultiplier, endingValue*valueMultiplier, rs1*valueMultiplier, rs2*valueMultiplier, rm1*valueMultiplier, rm2*valueMultiplier, re1*valueMultiplier, re2*valueMultiplier, coefficient*coefficientMultiplier, startToMiddleEasing, middleToEndEasing);
    }

    @Override
    public SpinParticleData overrideValueMultiplier(float valueMultiplier) {
        return (SpinParticleData) super.overrideValueMultiplier(valueMultiplier);
    }

    @Override
    public SpinParticleData overrideCoefficientMultiplier(float coefficientMultiplier) {
        return (SpinParticleData) super.overrideCoefficientMultiplier(coefficientMultiplier);
    }

    public static SpinParticleDataBuilder create() {
        return new SpinParticleDataBuilder(0, 0, 0);
    }

    public static SpinParticleDataBuilder create(float value) {
        return new SpinParticleDataBuilder(value, value, -1);
    }

    public static SpinParticleDataBuilder create(float startingValue, float endingValue) {
        return new SpinParticleDataBuilder(startingValue, endingValue, -1);
    }

    public static SpinParticleDataBuilder create(float startingValue, float middleValue, float endingValue) {
        return new SpinParticleDataBuilder(startingValue, middleValue, endingValue);
    }

    public static SpinParticleDataBuilder createRandomDirection(RandomSource random, float value) {
        value *= random.nextBoolean() ? 1 : -1;
        return new SpinParticleDataBuilder(value, value, -1);
    }

    public static SpinParticleDataBuilder createRandomDirection(RandomSource random, float startingValue, float endingValue) {
        final int direction = random.nextBoolean() ? 1 : -1;
        startingValue *= direction;
        endingValue *= direction;
        return new SpinParticleDataBuilder(startingValue, endingValue, -1);
    }

    public static SpinParticleDataBuilder createRandomDirection(RandomSource random, float startingValue, float middleValue, float endingValue) {
        final int direction = random.nextBoolean() ? 1 : -1;
        startingValue *= direction;
        middleValue *= direction;
        endingValue *= direction;
        return new SpinParticleDataBuilder(startingValue, middleValue, endingValue);
    }
}
