package mod.maxbogomol.fluffy_fur.client.particle.behavior;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.maxbogomol.fluffy_fur.client.particle.GenericParticle;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.component.SparkParticleBehaviorComponent;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import net.minecraft.client.Camera;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class SparkParticleBehavior extends ParticleBehavior {

    public ColorParticleData colorData;
    public GenericParticleData transparencyData;
    public boolean secondColor;

    float[] hsv1 = new float[3], hsv2 = new float[3];

    public SparkParticleBehavior(ColorParticleData colorData, GenericParticleData transparencyData, boolean secondColor, SpinParticleData xSpinData, SpinParticleData ySpinData, SpinParticleData zSpinData, float xOffset, float yOffset, float zOffset, boolean firstSide, boolean secondSide, boolean camera, boolean xRotCam, boolean yRotCam) {
        super(xSpinData, ySpinData, zSpinData, xOffset, yOffset, zOffset, firstSide, secondSide, camera, xRotCam, yRotCam);
        this.colorData = colorData;
        this.transparencyData = transparencyData;
        this.secondColor = secondColor;
    }

    public SparkParticleBehavior copy() {
        return new SparkParticleBehavior(colorData, transparencyData, secondColor, xSpinData, ySpinData, zSpinData, xOffset, yOffset, zOffset, firstSide, secondSide, camera, xRotCam, yRotCam);
    }

    public static SparkParticleBehaviorBuilder create() {
        return new SparkParticleBehaviorBuilder(0, 0, 0);
    }

    public static SparkParticleBehaviorBuilder create(float xOffset, float yOffset, float zOffset) {
        return new SparkParticleBehaviorBuilder((float) Math.toRadians(xOffset), (float) Math.toRadians(yOffset), (float) Math.toRadians(zOffset));
    }

    public SparkParticleBehaviorComponent getComponent() {
        return new SparkParticleBehaviorComponent();
    }

    public SparkParticleBehaviorComponent getSparkComponent(GenericParticle particle) {
        return (SparkParticleBehaviorComponent) particle.behaviorComponent;
    }

    public void init(GenericParticle particle) {
        super.init(particle);
        SparkParticleBehaviorComponent component = getSparkComponent(particle);

        float r1 = GenericParticle.pickRandomValue(colorData.r1, colorData.rr11, colorData.rr12);
        float g1 = GenericParticle.pickRandomValue(colorData.g1, colorData.rg11, colorData.rg12);
        float b1 = GenericParticle.pickRandomValue(colorData.b1, colorData.rb11, colorData.rb12);
        float r2 = GenericParticle.pickRandomValue(colorData.r2, colorData.rr21, colorData.rr22);
        float g2 = GenericParticle.pickRandomValue(colorData.g2, colorData.rg21, colorData.rg22);
        float b2 = GenericParticle.pickRandomValue(colorData.b2, colorData.rb21, colorData.rb21);

        component.st = GenericParticle.pickRandomValue(transparencyData.startingValue, transparencyData.rs1, transparencyData.rs2);
        component.mt = GenericParticle.pickRandomValue(transparencyData.middleValue, transparencyData.rm1, transparencyData.rm2);
        component.et = GenericParticle.pickRandomValue(transparencyData.endingValue, transparencyData.re1, transparencyData.re2);

        Color.RGBtoHSB((int)(255 * Math.min(1.0f, r1)), (int)(255 * Math.min(1.0f, g1)), (int)(255 * Math.min(1.0f, b1)), hsv1);
        Color.RGBtoHSB((int)(255 * Math.min(1.0f, r2)), (int)(255 * Math.min(1.0f, g2)), (int)(255 * Math.min(1.0f, b2)), hsv2);
    }

    public void pickColor(GenericParticle particle, float coeff) {
        float h = Mth.rotLerp(coeff, 360 * hsv1[0], 360 * hsv2[0]) / 360;
        float s = Mth.lerp(coeff, hsv1[1], hsv2[1]);
        float v = Mth.lerp(coeff, hsv1[2], hsv2[2]);
        int packed = Color.HSBtoRGB(h, s, v);
        float r = FastColor.ARGB32.red(packed) / 255.0f;
        float g = FastColor.ARGB32.green(packed) / 255.0f;
        float b = FastColor.ARGB32.blue(packed) / 255.0f;
        setColor(particle, r, g, b);
    }

    public void setColor(GenericParticle particle, float r, float g, float b) {
        SparkParticleBehaviorComponent component = getSparkComponent(particle);
        component.r = r;
        component.g = g;
        component.b = b;
    }

    @Override
    public void updateTraits(GenericParticle particle) {
        SparkParticleBehaviorComponent component = getSparkComponent(particle);
        component.xd = particle.xd;
        component.yd = particle.yd;
        component.zd = particle.zd;
        pickColor(particle, colorData.colorCurveEasing.ease(colorData.getProgress(particle.age, particle.lifetime), 0, 1, 1));
        component.a = transparencyData.getValue(particle.age, particle.lifetime, particle.st, particle.mt, particle.et);
    }

    @Override
    public void updateRenderTraits(GenericParticle particle, float partialTicks) {
        SparkParticleBehaviorComponent component = getSparkComponent(particle);
        float time = particle.age + partialTicks;
        pickColor(particle, colorData.colorCurveEasing.ease(colorData.getProgress(time, particle.lifetime), 0, 1, 1));
        component.a = transparencyData.getValue(time, particle.lifetime, particle.st, particle.mt, particle.et);
    }

    @Override
    public void render(GenericParticle particle, VertexConsumer vertexConsumer, Camera renderInfo, float partialTicks) {
        if (particle.shouldRenderTraits) updateRenderTraits(particle, partialTicks);

        Vec3 pos = getPosition(particle, renderInfo, partialTicks);

        SparkParticleBehaviorComponent component = getSparkComponent(particle);
        float x = (float) ((Mth.lerp(partialTicks, component.xd, particle.xd)));
        float y = (float) ((Mth.lerp(partialTicks, component.yd, particle.yd)));
        float z = (float) ((Mth.lerp(partialTicks, component.zd, particle.zd)));

        float width = particle.getQuadSize(partialTicks);

        Vec3 from = new Vec3(-x, -y, -z).add(pos);
        Vec3 to = new Vec3(x, y, z).add(pos);
        RenderBuilder builder = RenderBuilder.create().setFormat(DefaultVertexFormat.PARTICLE).setVertexConsumer(vertexConsumer)
                .setUV(particle.getU0(), particle.getV0(), particle.getU1(), particle.getV1())
                .setColorRaw(particle.getRed(), particle.getGreen(), particle.getBlue())
                .setAlpha(particle.getAlpha());
        if (secondColor) {
            builder.setSecondColorRaw(component.r, component.g, component.b)
                    .setSecondAlpha(component.a);
        }
        builder.renderBeam(null, from, to, width, Vec3.ZERO);
    }
}
