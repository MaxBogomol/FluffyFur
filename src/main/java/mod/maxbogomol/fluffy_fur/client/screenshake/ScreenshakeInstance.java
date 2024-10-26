package mod.maxbogomol.fluffy_fur.client.screenshake;

import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import net.minecraft.client.Camera;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class ScreenshakeInstance {
    public int progress;
    public final int duration;
    public float intensity1, intensity2, intensity3;
    public Easing intensityCurveStartEasing = Easing.LINEAR, intensityCurveEndEasing = Easing.LINEAR;

    public boolean isNormalize = true;
    public boolean isRotation = true;
    public boolean isPosition = false;
    public boolean isVector = false;
    public boolean isFov = false;
    public boolean isFovNormalize = false;

    public Vec3 vector = Vec3.ZERO;

    public static final Random random = new Random();

    public ScreenshakeInstance(int duration) {
        this.duration = duration;
    }

    public ScreenshakeInstance setIntensity(float intensity) {
        return setIntensity(intensity, intensity);
    }

    public ScreenshakeInstance setIntensity(float intensity1, float intensity2) {
        return setIntensity(intensity1, intensity2, intensity2);
    }

    public ScreenshakeInstance setIntensity(float intensity1, float intensity2, float intensity3) {
        this.intensity1 = intensity1;
        this.intensity2 = intensity2;
        this.intensity3 = intensity3;
        return this;
    }

    public ScreenshakeInstance setEasing(Easing easing) {
        return setEasing(easing, easing);
    }

    public ScreenshakeInstance setEasing(Easing intensityCurveStartEasing, Easing intensityCurveEndEasing) {
        this.intensityCurveStartEasing = intensityCurveStartEasing;
        this.intensityCurveEndEasing = intensityCurveEndEasing;
        return this;
    }

    public ScreenshakeInstance enableNormalize() {
        return setNormalize(true);
    }

    public ScreenshakeInstance disableNormalize() {
        return setNormalize(false);
    }

    public ScreenshakeInstance setNormalize(boolean normalize) {
        this.isNormalize = normalize;
        return this;
    }

    public ScreenshakeInstance enableRotation() {
        return setRotation(true);
    }

    public ScreenshakeInstance disableRotation() {
        return setRotation(false);
    }

    public ScreenshakeInstance setRotation(boolean rotation) {
        this.isRotation = rotation;
        return this;
    }

    public ScreenshakeInstance enablePosition() {
        return setPosition(true);
    }

    public ScreenshakeInstance disablePosition() {
        return setPosition(false);
    }

    public ScreenshakeInstance setPosition(boolean position) {
        this.isPosition = position;
        return this;
    }

    public ScreenshakeInstance enableVector() {
        return setVector(true);
    }

    public ScreenshakeInstance disableVector() {
        return setVector(false);
    }

    public ScreenshakeInstance setVector(boolean vector) {
        this.isVector = vector;
        return this;
    }

    public ScreenshakeInstance setVector(Vec3 vector) {
        this.vector = vector;
        return this;
    }

    public ScreenshakeInstance setRandomVector() {
        double angleA = random.nextDouble() * Math.PI * 2;
        double angleB = random.nextDouble() * Math.PI * 2;
        float x = (float) (Math.cos(angleA) * Math.cos(angleB));
        float y = (float) (Math.sin(angleA) * Math.cos(angleB));
        float z = (float) Math.sin(angleB);
        this.vector = new Vec3(x, y, z);
        return this;
    }

    public ScreenshakeInstance enableFov() {
        return setFov(true);
    }

    public ScreenshakeInstance disableFov() {
        return setFov(false);
    }

    public ScreenshakeInstance setFov(boolean fov) {
        this.isFov = fov;
        return this;
    }

    public ScreenshakeInstance enableFovNormalize() {
        return setFovNormalize(true);
    }

    public ScreenshakeInstance disableFovNormalize() {
        return setFovNormalize(false);
    }

    public ScreenshakeInstance setFovNormalize(boolean fovNormalize) {
        this.isFovNormalize = fovNormalize;
        return this;
    }

    public float updateIntensity(Camera camera) {
        progress++;
        float percentage = progress / (float) duration;
        if (intensity2 != intensity3) {
            if (percentage >= 0.5f) {
                return Mth.lerp(intensityCurveEndEasing.ease(percentage - 0.5f, 0, 1, 0.5f), intensity2, intensity1);
            } else {
                return Mth.lerp(intensityCurveStartEasing.ease(percentage, 0, 1, 0.5f), intensity1, intensity2);
            }
        } else {
            return Mth.lerp(intensityCurveStartEasing.ease(percentage, 0, 1, 1), intensity1, intensity2);
        }
    }
}