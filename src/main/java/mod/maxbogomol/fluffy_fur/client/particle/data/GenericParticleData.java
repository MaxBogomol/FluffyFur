package mod.maxbogomol.fluffy_fur.client.particle.data;

import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import net.minecraft.util.Mth;

public class GenericParticleData {
    public final float startingValue, middleValue, endingValue;
    public final float rs1, rs2, rm1, rm2, re1, re2;
    public final float coefficient;
    public final Easing startToMiddleEasing, middleToEndEasing;

    public float valueMultiplier = 1;
    public float coefficientMultiplier = 1;

    protected GenericParticleData(float startingValue, float middleValue, float endingValue, float rs1, float rs2, float rm1, float rm2, float re1, float re2, float coefficient, Easing startToMiddleEasing, Easing middleToEndEasing) {
        this.startingValue = startingValue;
        this.middleValue = middleValue;
        this.endingValue = endingValue;
        this.rs1 = rs1;
        this.rs2 = rs2;
        this.rm1 = rm1;
        this.rm2 = rm2;
        this.re1 = re1;
        this.re2 = re2;
        this.coefficient = coefficient;
        this.startToMiddleEasing = startToMiddleEasing;
        this.middleToEndEasing = middleToEndEasing;
    }

    public GenericParticleData copy() {
        return new GenericParticleData(startingValue, middleValue, endingValue, rs1, rs2, rm1, rm2, re1, re2, coefficient, startToMiddleEasing, middleToEndEasing).overrideValueMultiplier(valueMultiplier).overrideCoefficientMultiplier(coefficientMultiplier);
    }

    public GenericParticleData bake() {
        return new GenericParticleData(startingValue*valueMultiplier, middleValue*valueMultiplier, endingValue*valueMultiplier, rs1*valueMultiplier, rs2*valueMultiplier, rm1*valueMultiplier, rm2*valueMultiplier, re1*valueMultiplier, re2*valueMultiplier, coefficient*coefficientMultiplier, startToMiddleEasing, middleToEndEasing);
    }

    public GenericParticleData multiplyCoefficient(float coefficientMultiplier) {
        this.coefficientMultiplier *= coefficientMultiplier;
        return this;
    }

    public GenericParticleData multiplyValue(float valueMultiplier) {
        this.valueMultiplier *= valueMultiplier;
        return this;
    }

    public GenericParticleData overrideCoefficientMultiplier(float coefficientMultiplier) {
        this.coefficientMultiplier = coefficientMultiplier;
        return this;
    }

    public GenericParticleData overrideValueMultiplier(float valueMultiplier) {
        this.valueMultiplier = valueMultiplier;
        return this;
    }

    public boolean isTrinary() {
        return endingValue != -1;
    }

    public float getProgress(float age, float lifetime) {
        return Mth.clamp((age * coefficient * coefficientMultiplier) / lifetime, 0, 1);
    }

    public float getValue(float age, float lifetime, float startingValue, float middleValue, float endingValue) {
        float progress = getProgress(age, lifetime);
        float result;
        if (isTrinary()) {
            if (progress >= 0.5f) {
                result = Mth.lerp(middleToEndEasing.ease(progress - 0.5f, 0, 1, 0.5f), middleValue, endingValue);
            } else {
                result = Mth.lerp(startToMiddleEasing.ease(progress, 0, 1, 0.5f), startingValue, middleValue);
            }
        } else {
            result = Mth.lerp(startToMiddleEasing.ease(progress, 0, 1, 1), startingValue, middleValue);
        }
        return result * valueMultiplier;
    }

    public float getValue(float age, float lifetime) {
        return getValue(age, lifetime, startingValue, middleValue, endingValue);
    }

    public static GenericParticleDataBuilder create() {
        return new GenericParticleDataBuilder(-1, -1, -1);
    }

    public static GenericParticleDataBuilder create(float value) {
        return new GenericParticleDataBuilder(value, value, -1);
    }

    public static GenericParticleDataBuilder create(float startingValue, float endingValue) {
        return new GenericParticleDataBuilder(startingValue, endingValue, -1);
    }

    public static GenericParticleDataBuilder create(float startingValue, float middleValue, float endingValue) {
        return new GenericParticleDataBuilder(startingValue, middleValue, endingValue);
    }

    public static GenericParticleData constrictTransparency(GenericParticleData data) {
        float startingValue = Mth.clamp(data.startingValue, 0, 1);
        float middleValue = Mth.clamp(data.middleValue, 0, 1);
        float endingValue = data.endingValue == -1 ? -1 : Mth.clamp(data.endingValue, 0, 1);
        float rs1 = data.rs1 == -1 ? -1 : Mth.clamp(data.rs1, 0, 1);
        float rm1 = data.rm1 == -1 ? -1 : Mth.clamp(data.rm1, 0, 1);
        float re1 = data.re1 == -1 ? -1 : Mth.clamp(data.re1, 0, 1);
        float rs2 = data.rs2 == -1 ? -1 : Mth.clamp(data.rs2, 0, 1);
        float rm2 = data.rm2 == -1 ? -1 : Mth.clamp(data.rm2, 0, 1);
        float re2 = data.re2 == -1 ? -1 : Mth.clamp(data.re2, 0, 1);
        float coefficient = data.coefficient;
        Easing startToMiddleEasing = data.startToMiddleEasing;
        Easing middleToEndEasing = data.middleToEndEasing;
        return new GenericParticleData(startingValue, middleValue, endingValue, rs1, rs2, rm1, rm2, re1, re2, coefficient, startToMiddleEasing, middleToEndEasing);
    }
}
