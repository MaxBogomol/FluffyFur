package mod.maxbogomol.fluffy_fur.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.ParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.component.ParticleBehaviorComponent;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.LightParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.options.GenericParticleOptions;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

import java.awt.*;
import java.util.Random;

public class GenericParticle extends TextureSheetParticle {

    public static final Random random = new Random();

    public RenderType renderType;
    public ParticleRenderType particleRenderType;

    public ParticleBehavior behavior;
    public ParticleBehaviorComponent behaviorComponent;

    public ColorParticleData colorData;
    public GenericParticleData transparencyData;
    public GenericParticleData scaleData;
    public SpinParticleData spinData;
    public LightParticleData lightData;

    public boolean shouldCull;
    public boolean shouldRenderTraits;

    public float st;
    public float mt;
    public float et;
    public float ss;
    public float ms;
    public float es;
    public float sr;
    public float mr;
    public float er;

    public float randomSpin;

    float[] hsv1 = new float[3], hsv2 = new float[3];

    public GenericParticle(ClientLevel level, GenericParticleOptions options, double x, double y, double z, double vx, double vy, double vz) {
        super(level, x, y, z, vx, vy, vz);
        this.setPos(x, y, z);
        this.renderType = options.renderType;
        this.particleRenderType = options.particleRenderType;
        this.colorData = options.colorData;
        this.transparencyData = GenericParticleData.constrictTransparency(options.transparencyData);
        this.scaleData = options.scaleData;
        this.spinData = options.spinData;
        this.lightData = options.lightData;
        this.xd = vx;
        this.yd = vy;
        this.zd = vz;
        this.setLifetime(options.lifetime + random.nextInt(options.additionalLifetime + 1));
        this.gravity = options.gravity;
        if (options.additionalGravity > 0) this.gravity = this.gravity + random.nextFloat(options.additionalGravity);
        this.friction = options.friction;
        if (options.additionalFriction > 0) this.friction = this.friction + random.nextFloat(options.additionalFriction);
        this.shouldCull = options.shouldCull;
        this.shouldRenderTraits = options.shouldRenderTraits;
        this.hasPhysics = options.hasPhysics;
        this.roll = spinData.spinOffset + spinData.startingValue;
        this.randomSpin = (pickRandomValue(0, spinData.rsp1, spinData.rsp2));
        if (random.nextBoolean()) this.randomSpin = -this.randomSpin;
        this.roll = this.roll + pickRandomRollValue(0, spinData.rso1, spinData.rso2);

        float r1 = pickRandomValue(colorData.r1, colorData.rr11, colorData.rr12);
        float g1 = pickRandomValue(colorData.g1, colorData.rg11, colorData.rg12);
        float b1 = pickRandomValue(colorData.b1, colorData.rb11, colorData.rb12);
        float r2 = pickRandomValue(colorData.r2, colorData.rr21, colorData.rr22);
        float g2 = pickRandomValue(colorData.g2, colorData.rg21, colorData.rg22);
        float b2 = pickRandomValue(colorData.b2, colorData.rb21, colorData.rb21);

        st = pickRandomValue(transparencyData.startingValue, transparencyData.rs1, transparencyData.rs2);
        mt = pickRandomValue(transparencyData.middleValue, transparencyData.rm1, transparencyData.rm2);
        et = pickRandomValue(transparencyData.endingValue, transparencyData.re1, transparencyData.re2);

        ss = pickRandomValue(scaleData.startingValue, scaleData.rs1, scaleData.rs2);
        ms = pickRandomValue(scaleData.middleValue, scaleData.rm1, scaleData.rm2);
        es = pickRandomValue(scaleData.endingValue, scaleData.re1, scaleData.re2);

        sr = pickRandomValue(spinData.startingValue, spinData.rs1, spinData.rs2);
        mr = pickRandomValue(spinData.middleValue, spinData.rm1, spinData.rm2);
        er = pickRandomValue(spinData.endingValue, spinData.re1, spinData.re2);

        behavior = options.behavior;
        if (behavior != null) behavior.init(this);

        Color.RGBtoHSB((int)(255 * Math.min(1.0f, r1)), (int)(255 * Math.min(1.0f, g1)), (int)(255 * Math.min(1.0f, b1)), hsv1);
        Color.RGBtoHSB((int)(255 * Math.min(1.0f, r2)), (int)(255 * Math.min(1.0f, g2)), (int)(255 * Math.min(1.0f, b2)), hsv2);
        updateTraits();
    }

    public static float pickRandomValue(float value, float value1, float value2) {
        if (value1 >= 0 && value2 >= 0) {
            return (value1 != value2) ? random.nextFloat(Math.min(value1, value2), Math.max(value1, value2)) : value1;
        }
        return value;
    }

    public static float pickRandomRollValue(float value, float value1, float value2) {
        if (value1 != 0 && value2 != 0) {
            return (value1 != value2) ? random.nextFloat(Math.min(value1, value2), Math.max(value1, value2)) : value1;
        }
        return value;
    }

    public void pickColor(float coeff) {
        float h = Mth.rotLerp(coeff, 360 * hsv1[0], 360 * hsv2[0]) / 360;
        float s = Mth.lerp(coeff, hsv1[1], hsv2[1]);
        float v = Mth.lerp(coeff, hsv1[2], hsv2[2]);
        int packed = Color.HSBtoRGB(h, s, v);
        float r = FastColor.ARGB32.red(packed) / 255.0f;
        float g = FastColor.ARGB32.green(packed) / 255.0f;
        float b = FastColor.ARGB32.blue(packed) / 255.0f;
        setColor(r, g, b);
    }

    public void updateTraits() {
        pickColor(colorData.colorCurveEasing.ease(colorData.getProgress(age, lifetime), 0, 1, 1));
        quadSize = scaleData.getValue(age, lifetime, ss, ms, es);
        alpha = transparencyData.getValue(age, lifetime, st, mt, et);
        oRoll = roll;
        roll = roll + spinData.getValue(age, lifetime, sr, mr, er) + randomSpin;

        if (behavior != null) behavior.updateTraits(this);
    }

    @Override
    public void tick() {
        updateTraits();
        super.tick();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return particleRenderType;
    }

    @Override
    public boolean shouldCull() {
        return shouldCull;
    }

    @Override
    public int getLightColor(float partialTicks) {
        return lightData.getLight(this, this.level, partialTicks);
    }

    @Override
    public float getU0() {
        return this.sprite.getU0();
    }

    @Override
    public float getU1() {
        return this.sprite.getU1();
    }

    @Override
    public float getV0() {
        return this.sprite.getV0();
    }

    @Override
    public float getV1() {
        return this.sprite.getV1();
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTicks) {
        if (shouldRenderTraits) updateRenderTraits(partialTicks);
        if (behavior == null) {
            super.render(renderType != null ? FluffyFurRenderTypes.getDelayedRender().getBuffer(renderType) : vertexConsumer, camera, partialTicks);
        } else {
            behavior.render(this, renderType != null ? FluffyFurRenderTypes.getDelayedRender().getBuffer(renderType) : vertexConsumer, camera, partialTicks);
        }
    }

    public void updateRenderTraits(float partialTicks) {
        float time = age + partialTicks;
        pickColor(colorData.colorCurveEasing.ease(colorData.getProgress(time, lifetime), 0, 1, 1));
        quadSize = scaleData.getValue(time, lifetime, ss, ms, es);
        alpha = transparencyData.getValue(time, lifetime, st, mt, et);
    }
}