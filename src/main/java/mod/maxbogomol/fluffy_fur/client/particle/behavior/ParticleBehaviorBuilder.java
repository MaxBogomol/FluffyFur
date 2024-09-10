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

    public boolean sideLayer = true;
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

    public ParticleBehaviorBuilder enableSideLayer() {
        return setSideLayer(true);
    }

    public ParticleBehaviorBuilder disableSideLayer() {
        return setSideLayer(false);
    }

    public ParticleBehaviorBuilder setSideLayer(boolean sideLayer) {
        this.sideLayer = sideLayer;
        return this;
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
        return new ParticleBehavior(xSpinData, ySpinData, zSpinData, xOffset, yOffset, zOffset, sideLayer, camera, xRotCam, yRotCam);
    }
}
