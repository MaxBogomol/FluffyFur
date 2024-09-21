package mod.maxbogomol.fluffy_fur.client.particle;

import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.options.GenericParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;

public class LeavesParticle extends GenericParticle {

    private float rotSpeed;
    private final float particleRandom;
    private final float spinAcceleration;

    public LeavesParticle(ClientLevel level, GenericParticleOptions options, ParticleEngine.MutableSpriteSet spriteSet, double x, double y, double z, double vx, double vy, double vz) {
        super(level, options, spriteSet, x, y, z, vx, vy, vz);
        this.rotSpeed = (float)Math.toRadians(this.random.nextBoolean() ? -30.0D : 30.0D);
        this.particleRandom = this.random.nextFloat();
        this.spinAcceleration = (float)Math.toRadians(this.random.nextBoolean() ? -5.0D : 5.0D);
        this.lifetime = 300;
        this.gravity = 7.5E-4F;
        float f = this.random.nextBoolean() ? 0.05F : 0.075F;
        this.scaleData = GenericParticleData.create(f).build();
        this.friction = 1.0F;
        this.roll = (float) (this.random.nextFloat() * Math.PI);
        this.oRoll = this.roll;

        ss = pickRandomValue(scaleData.startingValue, scaleData.rs1, scaleData.rs2);
        ms = pickRandomValue(scaleData.middleValue, scaleData.rm1, scaleData.rm2);
        es = pickRandomValue(scaleData.endingValue, scaleData.re1, scaleData.re2);
    }

    @Override
    public void tick() {
        updateTraits();
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            float f = (float) (this.age);
            float f1 = Math.min(f / 300.0F, 1.0F);
            double d0 = Math.cos(Math.toRadians((this.particleRandom * 60.0F))) * 2.0D * Math.pow(f1, 1.25D);
            double d1 = Math.sin(Math.toRadians((this.particleRandom * 60.0F))) * 2.0D * Math.pow(f1, 1.25D);
            this.xd += d0 * (double) 0.0025F;
            this.zd += d1 * (double) 0.0025F;
            this.yd -= this.gravity;
            this.rotSpeed += this.spinAcceleration / 20.0F;
            this.oRoll = this.roll;
            this.roll += this.rotSpeed / 20.0F;
            this.move(this.xd, this.yd, this.zd);
            if (this.onGround) {
                this.remove();
            }

            if (!this.removed) {
                this.xd *= this.friction;
                this.yd *= this.friction;
                this.zd *= this.friction;
            }
        }
        spriteData.tick(this);
    }
}