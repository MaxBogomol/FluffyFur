package mod.maxbogomol.fluffy_fur.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.LightParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.client.render.WorldRenderHandler;
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

    public final RenderType renderType;

    public final ColorParticleData colorData;
    public final GenericParticleData transparencyData;
    public final GenericParticleData scaleData;
    public final SpinParticleData spinData;
    public final LightParticleData lightData;

    public final boolean shouldCull;
    public float randomSpin;

    float[] hsv1 = new float[3], hsv2 = new float[3];

    public GenericParticle(ClientLevel level, GenericParticleOptions options, double x, double y, double z, double vx, double vy, double vz) {
        super(level, x, y, z, vx, vy, vz);
        this.setPos(x, y, z);
        this.renderType = options.renderType;
        this.colorData = options.colorData;
        this.transparencyData = options.transparencyData;
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
        this.hasPhysics = options.hasPhysics;
        this.randomSpin = 0;
        if (options.randomSpin > 0) this.randomSpin = options.randomSpin * ((random.nextFloat() - 0.5f) * 2);
        Color.RGBtoHSB((int)(255 * Math.min(1.0f, colorData.r1)), (int)(255 * Math.min(1.0f, colorData.g1)), (int)(255 * Math.min(1.0f, colorData.b1)), hsv1);
        Color.RGBtoHSB((int)(255 * Math.min(1.0f, colorData.r2)), (int)(255 * Math.min(1.0f, colorData.g2)), (int)(255 * Math.min(1.0f, colorData.b2)), hsv2);
        updateTraits();
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

    protected void updateTraits() {
        pickColor(colorData.colorCurveEasing.ease(colorData.getProgress(age, lifetime), 0, 1, 1));
        quadSize = scaleData.getValue(age, lifetime);
        alpha = transparencyData.getValue(age, lifetime);
        oRoll = roll;
        roll = roll + spinData.getValue(age, lifetime) + randomSpin;
    }

    @Override
    public void tick() {
        updateTraits();
        super.tick();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return SpriteParticleRenderType.INSTANCE;
    }

    @Override
    public boolean shouldCull() {
        return shouldCull;
    }

    @Override
    protected int getLightColor(float partialTicks) {
        return lightData.getLight(this, this.level, partialTicks);
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTicks) {
        super.render(WorldRenderHandler.getDelayedRender().getBuffer(renderType), camera, partialTicks);
    }
}