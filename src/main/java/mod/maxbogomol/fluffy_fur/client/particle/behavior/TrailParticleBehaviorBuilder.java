package mod.maxbogomol.fluffy_fur.client.particle.behavior;

import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.options.GenericParticleOptions;

public class TrailParticleBehaviorBuilder extends ParticleBehaviorBuilder {

    public ColorParticleData colorData = GenericParticleOptions.DEFAULT_COLOR;
    public GenericParticleData transparencyData = GenericParticleOptions.DEFAULT_GENERIC;
    public boolean secondColor = false;
    public int trailSize = 10;

    protected TrailParticleBehaviorBuilder(float xOffset, float yOffset, float zOffset) {
        super(xOffset, yOffset, zOffset);
    }

    public TrailParticleBehaviorBuilder setXSpinData(SpinParticleData xSpinData) {
        this.xSpinData = xSpinData;
        return this;
    }

    public TrailParticleBehaviorBuilder setYSpinData(SpinParticleData ySpinData) {
        this.ySpinData = ySpinData;
        return this;
    }

    public TrailParticleBehaviorBuilder setZSpinData(SpinParticleData zSpinData) {
        this.zSpinData = zSpinData;
        return this;
    }

    public TrailParticleBehaviorBuilder enableSided() {
        return setSided(true);
    }

    public TrailParticleBehaviorBuilder disableSided() {
        return setSided(false);
    }

    public TrailParticleBehaviorBuilder setSided(boolean side) {
        return setFirstSide(side).setSecondSide(side);
    }

    public TrailParticleBehaviorBuilder enableFirstSide() {
        return setFirstSide(true);
    }

    public TrailParticleBehaviorBuilder disableFirstSSide() {
        return setFirstSide(false);
    }

    public TrailParticleBehaviorBuilder setFirstSide(boolean side) {
        this.firstSide = side;
        return this;
    }

    public TrailParticleBehaviorBuilder enableSecondSide() {
        return setSecondSide(true);
    }

    public TrailParticleBehaviorBuilder disableSecondSSide() {
        return setSecondSide(false);
    }

    public TrailParticleBehaviorBuilder setSecondSide(boolean side) {
        this.secondSide = side;
        return this;
    }

    public TrailParticleBehaviorBuilder setSide(boolean firstSide, boolean secondSide) {
        return setFirstSide(firstSide).setSecondSide(secondSide);
    }

    public TrailParticleBehaviorBuilder enableCamera() {
        return setCamera(true);
    }

    public TrailParticleBehaviorBuilder disableCamera() {
        return setCamera(false);
    }

    public TrailParticleBehaviorBuilder setCamera(boolean camera) {
        this.camera = camera;
        return this;
    }

    public TrailParticleBehaviorBuilder setCameraRotation(boolean xRotCam, boolean yRotCam) {
        this.xRotCam = xRotCam;
        this.yRotCam = yRotCam;
        return this;
    }

    public TrailParticleBehaviorBuilder setColorData(ColorParticleData colorData) {
        this.colorData = colorData;
        return this;
    }

    public TrailParticleBehaviorBuilder setTransparencyData(GenericParticleData transparencyData) {
        this.transparencyData = transparencyData;
        return this;
    }

    public TrailParticleBehaviorBuilder enableSecondColor() {
        return setSecondColor(true);
    }

    public TrailParticleBehaviorBuilder disableSecondColor() {
        return setSecondColor(false);
    }

    public TrailParticleBehaviorBuilder setSecondColor(boolean secondColor) {
        this.secondColor = secondColor;
        return this;
    }

    public TrailParticleBehaviorBuilder setTrailSize(int trailSize) {
        this.trailSize = trailSize;
        return this;
    }

    public ParticleBehavior build() {
        return new TrailParticleBehavior(colorData, transparencyData, secondColor, trailSize, xSpinData, ySpinData, zSpinData, xOffset, yOffset, zOffset, firstSide, secondSide, camera, xRotCam, yRotCam);
    }
}
