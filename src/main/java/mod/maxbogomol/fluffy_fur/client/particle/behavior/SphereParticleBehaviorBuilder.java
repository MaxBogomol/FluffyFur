package mod.maxbogomol.fluffy_fur.client.particle.behavior;

import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;

public class SphereParticleBehaviorBuilder extends ParticleBehaviorBuilder {

    public int longs = 16;
    public int lats = 8;

    protected SphereParticleBehaviorBuilder(float xOffset, float yOffset, float zOffset) {
        super(xOffset, yOffset, zOffset);
    }

    public SphereParticleBehaviorBuilder setXSpinData(SpinParticleData xSpinData) {
        this.xSpinData = xSpinData;
        return this;
    }

    public SphereParticleBehaviorBuilder setYSpinData(SpinParticleData ySpinData) {
        this.ySpinData = ySpinData;
        return this;
    }

    public SphereParticleBehaviorBuilder setZSpinData(SpinParticleData zSpinData) {
        this.zSpinData = zSpinData;
        return this;
    }

    public SphereParticleBehaviorBuilder enableSided() {
        return setSided(true);
    }

    public SphereParticleBehaviorBuilder disableSided() {
        return setSided(false);
    }

    public SphereParticleBehaviorBuilder setSided(boolean side) {
        return setFirstSide(side).setSecondSide(side);
    }

    public SphereParticleBehaviorBuilder enableFirstSide() {
        return setFirstSide(true);
    }

    public SphereParticleBehaviorBuilder disableFirstSide() {
        return setFirstSide(false);
    }

    public SphereParticleBehaviorBuilder setFirstSide(boolean side) {
        this.firstSide = side;
        return this;
    }

    public SphereParticleBehaviorBuilder enableSecondSide() {
        return setSecondSide(true);
    }

    public SphereParticleBehaviorBuilder disableSecondSide() {
        return setSecondSide(false);
    }

    public SphereParticleBehaviorBuilder setSecondSide(boolean side) {
        this.secondSide = side;
        return this;
    }

    public SphereParticleBehaviorBuilder setSide(boolean firstSide, boolean secondSide) {
        return setFirstSide(firstSide).setSecondSide(secondSide);
    }

    public SphereParticleBehaviorBuilder enableCamera() {
        return setCamera(true);
    }

    public SphereParticleBehaviorBuilder disableCamera() {
        return setCamera(false);
    }

    public SphereParticleBehaviorBuilder setCamera(boolean camera) {
        this.camera = camera;
        return this;
    }

    public SphereParticleBehaviorBuilder setCameraRotation(boolean xRotCam, boolean yRotCam) {
        this.xRotCam = xRotCam;
        this.yRotCam = yRotCam;
        return this;
    }

    public SphereParticleBehaviorBuilder setSphereSize(int longs, int lats) {
        this.longs = longs;
        this.lats = lats;
        return this;
    }

    public SphereParticleBehaviorBuilder setSphereSize(int longs) {
        this.longs = longs;
        this.lats = longs;
        return this;
    }

    public ParticleBehavior build() {
        return new SphereParticleBehavior(longs, lats, xSpinData, ySpinData, zSpinData, xOffset, yOffset, zOffset, firstSide, secondSide, camera, xRotCam, yRotCam);
    }
}
