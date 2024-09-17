package mod.maxbogomol.fluffy_fur.client.particle.behavior;

import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;

public class CubeParticleBehaviorBuilder extends ParticleBehaviorBuilder {

    protected CubeParticleBehaviorBuilder(float xOffset, float yOffset, float zOffset) {
        super(xOffset, yOffset, zOffset);
    }

    public CubeParticleBehaviorBuilder setXSpinData(SpinParticleData xSpinData) {
        this.xSpinData = xSpinData;
        return this;
    }

    public CubeParticleBehaviorBuilder setYSpinData(SpinParticleData ySpinData) {
        this.ySpinData = ySpinData;
        return this;
    }

    public CubeParticleBehaviorBuilder setZSpinData(SpinParticleData zSpinData) {
        this.zSpinData = zSpinData;
        return this;
    }

    public CubeParticleBehaviorBuilder enableSided() {
        return setSided(true);
    }

    public CubeParticleBehaviorBuilder disableSided() {
        return setSided(false);
    }

    public CubeParticleBehaviorBuilder setSided(boolean side) {
        return setFirstSide(side).setSecondSide(side);
    }

    public CubeParticleBehaviorBuilder enableFirstSide() {
        return setFirstSide(true);
    }

    public CubeParticleBehaviorBuilder disableFirstSSide() {
        return setFirstSide(false);
    }

    public CubeParticleBehaviorBuilder setFirstSide(boolean side) {
        this.firstSide = side;
        return this;
    }

    public CubeParticleBehaviorBuilder enableSecondSide() {
        return setSecondSide(true);
    }

    public CubeParticleBehaviorBuilder disableSecondSSide() {
        return setSecondSide(false);
    }

    public CubeParticleBehaviorBuilder setSecondSide(boolean side) {
        this.secondSide = side;
        return this;
    }

    public CubeParticleBehaviorBuilder setSide(boolean firstSide, boolean secondSide) {
        return setFirstSide(firstSide).setSecondSide(secondSide);
    }

    public CubeParticleBehaviorBuilder enableCamera() {
        return setCamera(true);
    }

    public CubeParticleBehaviorBuilder disableCamera() {
        return setCamera(false);
    }

    public CubeParticleBehaviorBuilder setCamera(boolean camera) {
        this.camera = camera;
        return this;
    }

    public CubeParticleBehaviorBuilder setCameraRotation(boolean xRotCam, boolean yRotCam) {
        this.xRotCam = xRotCam;
        this.yRotCam = yRotCam;
        return this;
    }

    public ParticleBehavior build() {
        return new CubeParticleBehavior(xSpinData, ySpinData, zSpinData, xOffset, yOffset, zOffset, firstSide, secondSide, camera, xRotCam, yRotCam);
    }
}
