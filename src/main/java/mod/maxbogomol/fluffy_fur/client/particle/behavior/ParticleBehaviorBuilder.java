package mod.maxbogomol.fluffy_fur.client.particle.behavior;

import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.options.GenericParticleOptions;

public class ParticleBehaviorBuilder {
    public SpinParticleData xSpinData = GenericParticleOptions.DEFAULT_SPIN;
    public SpinParticleData ySpinData = GenericParticleOptions.DEFAULT_SPIN;
    public SpinParticleData zSpinData = GenericParticleOptions.DEFAULT_SPIN;

    public float xOffset;
    public float yOffset;
    public float zOffset;

    public boolean firstSide = true;
    public boolean secondSide = true;
    public boolean camera = false;
    public boolean xRotCam = true;
    public boolean yRotCam = true;

    protected ParticleBehaviorBuilder(float xOffset, float yOffset, float zOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
    }

    public ParticleBehaviorBuilder setXSpinData(SpinParticleData xSpinData) {
        this.xSpinData = xSpinData;
        return this;
    }

    public ParticleBehaviorBuilder setYSpinData(SpinParticleData ySpinData) {
        this.ySpinData = ySpinData;
        return this;
    }

    public ParticleBehaviorBuilder setZSpinData(SpinParticleData zSpinData) {
        this.zSpinData = zSpinData;
        return this;
    }

    public ParticleBehaviorBuilder enableSided() {
        return setSided(true);
    }

    public ParticleBehaviorBuilder disableSided() {
        return setSided(false);
    }

    public ParticleBehaviorBuilder setSided(boolean side) {
        return setFirstSide(side).setSecondSide(side);
    }

    public ParticleBehaviorBuilder enableFirstSide() {
        return setFirstSide(true);
    }

    public ParticleBehaviorBuilder disableFirstSide() {
        return setFirstSide(false);
    }

    public ParticleBehaviorBuilder setFirstSide(boolean side) {
        this.firstSide = side;
        return this;
    }

    public ParticleBehaviorBuilder enableSecondSide() {
        return setSecondSide(true);
    }

    public ParticleBehaviorBuilder disableSecondSide() {
        return setSecondSide(false);
    }

    public ParticleBehaviorBuilder setSecondSide(boolean side) {
        this.secondSide = side;
        return this;
    }

    public ParticleBehaviorBuilder setSide(boolean firstSide, boolean secondSide) {
        return setFirstSide(firstSide).setSecondSide(secondSide);
    }

    public ParticleBehaviorBuilder enableCamera() {
        return setCamera(true);
    }

    public ParticleBehaviorBuilder disableCamera() {
        return setCamera(false);
    }

    public ParticleBehaviorBuilder setCamera(boolean camera) {
        this.camera = camera;
        return this;
    }

    public ParticleBehaviorBuilder setCameraRotation(boolean xRotCam, boolean yRotCam) {
        this.xRotCam = xRotCam;
        this.yRotCam = yRotCam;
        return this;
    }

    public ParticleBehavior build() {
        return new ParticleBehavior(xSpinData, ySpinData, zSpinData, xOffset, yOffset, zOffset, firstSide, secondSide, camera, xRotCam, yRotCam);
    }
}
