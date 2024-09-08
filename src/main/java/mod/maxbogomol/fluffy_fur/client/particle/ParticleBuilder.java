package mod.maxbogomol.fluffy_fur.client.particle;

import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.LightParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.options.GenericParticleOptions;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

import java.util.Random;

public class ParticleBuilder {

    public static final Random random = new Random();

    public final GenericParticleOptions options;

    double vx = 0, vy = 0, vz = 0;
    double fvx = 0, fvy = 0, fvz = 0;
    double fdx = 0, fdy = 0, fdz = 0;
    double maxXSpeed = 0, maxYSpeed = 0, maxZSpeed = 0;
    double maxXDist = 0, maxYDist = 0, maxZDist = 0;

    boolean force = true;
    boolean distanceSpawn = true;
    double distance = 100;

    protected ParticleBuilder(GenericParticleOptions options) {
        this.options = options;
    }

    public GenericParticleOptions getParticleOptions() {
        return options;
    }

    public ParticleBuilder setRenderType(RenderType renderType) {
        options.renderType = renderType;
        return this;
    }

    public ParticleBuilder setParticleRenderType(ParticleRenderType particleRenderType) {
        options.renderType = null;
        options.particleRenderType = particleRenderType;
        return this;
    }

    public ParticleBuilder setColorData(ColorParticleData colorData) {
        options.colorData = colorData;
        return this;
    }

    public ParticleBuilder setTransparencyData(GenericParticleData transparencyData) {
        options.transparencyData = transparencyData;
        return this;
    }

    public ParticleBuilder setScaleData(GenericParticleData scaleData) {
        options.scaleData = scaleData;
        return this;
    }

    public ParticleBuilder setSpinData(SpinParticleData spinData) {
        options.spinData = spinData;
        return this;
    }

    public ParticleBuilder setLightData(LightParticleData lightData) {
        options.lightData = lightData;
        return this;
    }

    public ParticleBuilder setLifetime(int lifetime) {
        options.lifetime = lifetime;
        return this;
    }

    public ParticleBuilder setLifetime(int lifetime, int additionalLifetime) {
        options.lifetime = lifetime;
        options.additionalLifetime = additionalLifetime;
        return this;
    }

    public ParticleBuilder setGravity(float gravity) {
        options.gravity = gravity;
        return this;
    }

    public ParticleBuilder setGravity(int gravity, int additionalGravity) {
        options.gravity = gravity;
        options.additionalGravity = additionalGravity;
        return this;
    }

    public ParticleBuilder setFriction(float friction) {
        options.friction = friction;
        return this;
    }

    public ParticleBuilder setFriction(int friction, int additionalFriction) {
        options.friction = friction;
        options.additionalFriction = additionalFriction;
        return this;
    }

    public ParticleBuilder enableCull() {
        return setShouldCull(true);
    }

    public ParticleBuilder disableCull() {
        return setShouldCull(false);
    }

    public ParticleBuilder setShouldCull(boolean shouldCull) {
        options.shouldCull = shouldCull;
        return this;
    }

    public ParticleBuilder enableRenderTraits() {
        return setShouldRenderTraits(true);
    }

    public ParticleBuilder disableRenderTraits() {
        return setShouldRenderTraits(false);
    }

    public ParticleBuilder setShouldRenderTraits(boolean shouldRenderTraits) {
        options.shouldRenderTraits = shouldRenderTraits;
        return this;
    }

    public ParticleBuilder enablePhysics() {
        return setHasPhysics(true);
    }

    public ParticleBuilder disablePhysics() {
        return setHasPhysics(false);
    }

    public ParticleBuilder setHasPhysics(boolean hasPhysics) {
        options.hasPhysics = hasPhysics;
        return this;
    }

    public ParticleBuilder randomVelocity(double maxSpeed) {
        randomVelocity(maxSpeed, maxSpeed, maxSpeed);
        return this;
    }

    public ParticleBuilder randomVelocity(double maxHSpeed, double maxVSpeed) {
        randomVelocity(maxHSpeed, maxVSpeed, maxHSpeed);
        return this;
    }

    public ParticleBuilder randomVelocity(double maxXSpeed, double maxYSpeed, double maxZSpeed) {
        this.maxXSpeed = maxXSpeed;
        this.maxYSpeed = maxYSpeed;
        this.maxZSpeed = maxZSpeed;
        return this;
    }

    public ParticleBuilder flatRandomVelocity(double fvx, double fvy, double fvz) {
        this.fvx = fvx;
        this.fvy = fvy;
        this.fvz = fvz;
        return this;
    }

    public ParticleBuilder addVelocity(double vx, double vy, double vz) {
        this.vx += vx;
        this.vy += vy;
        this.vz += vz;
        return this;
    }

    public ParticleBuilder setVelocity(double vx, double vy, double vz) {
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        return this;
    }

    public ParticleBuilder randomOffset(double maxDistance) {
        randomOffset(maxDistance, maxDistance, maxDistance);
        return this;
    }

    public ParticleBuilder randomOffset(double maxHDist, double maxVDist) {
        randomOffset(maxHDist, maxVDist, maxHDist);
        return this;
    }

    public ParticleBuilder randomOffset(double maxXDist, double maxYDist, double maxZDist) {
        this.maxXDist = maxXDist;
        this.maxYDist = maxYDist;
        this.maxZDist = maxZDist;
        return this;
    }

    public ParticleBuilder flatRandomOffset(double fdx, double fdy, double fdz) {
        this.fdx = fdx;
        this.fdy = fdy;
        this.fdz = fdz;
        return this;
    }

    public ParticleBuilder enableForce() {
        return setForce(true);
    }

    public ParticleBuilder disableForce() {
        return setForce(false);
    }

    public ParticleBuilder setForce(boolean force) {
        this.force = force;
        return this;
    }

    public ParticleBuilder setDistance(double distance) {
        this.distance = distance;
        return this;
    }

    public ParticleBuilder enableDistanceSpawn() {
        return setDistanceSpawn(true);
    }

    public ParticleBuilder disableDistanceSpawn() {
        return setDistanceSpawn(false);
    }

    public ParticleBuilder setDistanceSpawn(boolean distanceSpawn) {
        this.distanceSpawn = distanceSpawn;
        return this;
    }

    public ParticleBuilder spawn(Level world, double x, double y, double z) {
        double yaw = random.nextFloat() * Math.PI * 2, pitch = random.nextFloat() * Math.PI - Math.PI / 2,
                xSpeed = random.nextFloat() * maxXSpeed, ySpeed = random.nextFloat() * maxYSpeed, zSpeed = random.nextFloat() * maxZSpeed;
        double vx = this.vx + Math.sin(yaw) * Math.cos(pitch) * xSpeed;
        double vy = this.vy + Math.sin(pitch) * ySpeed;
        double vz = this.vz + Math.cos(yaw) * Math.cos(pitch) * zSpeed;
        double yaw2 = random.nextFloat() * Math.PI * 2, pitch2 = random.nextFloat() * Math.PI - Math.PI / 2,
                xDist = random.nextFloat() * maxXDist, yDist = random.nextFloat() * maxYDist, zDist = random.nextFloat() * maxZDist;
        double dx = Math.sin(yaw2) * Math.cos(pitch2) * xDist;
        double dy = Math.sin(pitch2) * yDist;
        double dz = Math.cos(yaw2) * Math.cos(pitch2) * zDist;
        double fdx = (random.nextFloat() * 2 - 1f) * this.fdx;
        double fdy = (random.nextFloat() * 2 - 1f) * this.fdy;
        double fdz = (random.nextFloat() * 2 - 1f) * this.fdz;
        double fvx = (random.nextFloat() * 2 - 1f) * this.fvx;
        double fvy = (random.nextFloat() * 2 - 1f) * this.fvy;
        double fvz = (random.nextFloat() * 2 - 1f) * this.fvz;

        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        if (!distanceSpawn || Math.sqrt(camera.getPosition().distanceToSqr(x + dx + fdx, y + dy + fdy, z + dz + fdz)) <= distance) {
            world.addParticle(options, force, x + dx + fdx, y + dy + fdy, z + dz + fdz, vx + fvx, vy + fvy, vz + fvz);
        }

        return this;
    }

    public ParticleBuilder repeat(Level world, double x, double y, double z, int n) {
        for (int i = 0; i < n; i ++) spawn(world, x, y, z);
        return this;
    }

    public ParticleBuilder repeat(Level world, double x, double y, double z, int n, float chance) {
        for (int i = 0; i < n; i ++) {
            if (random.nextFloat() < chance) spawn(world, x, y, z);
        }
        return this;
    }

    public static ParticleBuilder create(ParticleType<?> type) {
        return new ParticleBuilder(new GenericParticleOptions(type));
    }

    public static ParticleBuilder create(RegistryObject<?> type) {
        return new ParticleBuilder(new GenericParticleOptions((ParticleType<?>)type.get()));
    }

    public static ParticleBuilder create(GenericParticleOptions options) {
        return new ParticleBuilder(options);
    }
}
