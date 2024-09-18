package mod.maxbogomol.fluffy_fur.client.particle.behavior;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.maxbogomol.fluffy_fur.client.particle.GenericParticle;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.component.TrailParticleBehaviorComponent;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.client.render.trail.TrailPoint;
import mod.maxbogomol.fluffy_fur.client.render.trail.TrailPointBuilder;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TrailParticleBehavior extends ParticleBehavior implements ICustomBehaviorParticleRender {

    public ColorParticleData colorData;
    public GenericParticleData transparencyData;
    public boolean secondColor;

    public TrailParticleBehavior(ColorParticleData colorData, GenericParticleData transparencyData, boolean secondColor, SpinParticleData xSpinData, SpinParticleData ySpinData, SpinParticleData zSpinData, float xOffset, float yOffset, float zOffset, boolean firstSide, boolean secondSide, boolean camera, boolean xRotCam, boolean yRotCam) {
        super(xSpinData, ySpinData, zSpinData, xOffset, yOffset, zOffset, firstSide, secondSide, camera, xRotCam, yRotCam);
        this.colorData = colorData;
        this.transparencyData = transparencyData;
        this.secondColor = secondColor;
    }

    public TrailParticleBehavior copy() {
        return new TrailParticleBehavior(colorData, transparencyData, secondColor, xSpinData, ySpinData, zSpinData, xOffset, yOffset, zOffset, firstSide, secondSide, camera, xRotCam, yRotCam);
    }

    public static TrailParticleBehaviorBuilder create() {
        return new TrailParticleBehaviorBuilder(0, 0, 0);
    }

    public static TrailParticleBehaviorBuilder create(float xOffset, float yOffset, float zOffset) {
        return new TrailParticleBehaviorBuilder((float) Math.toRadians(xOffset), (float) Math.toRadians(yOffset), (float) Math.toRadians(zOffset));
    }

    public TrailParticleBehaviorComponent getComponent() {
        return new TrailParticleBehaviorComponent();
    }

    public TrailParticleBehaviorComponent getTrailComponent(GenericParticle particle) {
        return (TrailParticleBehaviorComponent) particle.behaviorComponent;
    }

    @Override
    public void init(GenericParticle particle) {
        super.init(particle);
        TrailParticleBehaviorComponent component = getTrailComponent(particle);
        component.trailPointBuilder = TrailPointBuilder.create(10);

        float r1 = GenericParticle.pickRandomValue(colorData.r1, colorData.rr11, colorData.rr12);
        float g1 = GenericParticle.pickRandomValue(colorData.g1, colorData.rg11, colorData.rg12);
        float b1 = GenericParticle.pickRandomValue(colorData.b1, colorData.rb11, colorData.rb12);
        float r2 = GenericParticle.pickRandomValue(colorData.r2, colorData.rr21, colorData.rr22);
        float g2 = GenericParticle.pickRandomValue(colorData.g2, colorData.rg21, colorData.rg22);
        float b2 = GenericParticle.pickRandomValue(colorData.b2, colorData.rb21, colorData.rb21);

        component.st = GenericParticle.pickRandomValue(transparencyData.startingValue, transparencyData.rs1, transparencyData.rs2);
        component.mt = GenericParticle.pickRandomValue(transparencyData.middleValue, transparencyData.rm1, transparencyData.rm2);
        component.et = GenericParticle.pickRandomValue(transparencyData.endingValue, transparencyData.re1, transparencyData.re2);

        Color.RGBtoHSB((int)(255 * Math.min(1.0f, r1)), (int)(255 * Math.min(1.0f, g1)), (int)(255 * Math.min(1.0f, b1)), component.hsv1);
        Color.RGBtoHSB((int)(255 * Math.min(1.0f, r2)), (int)(255 * Math.min(1.0f, g2)), (int)(255 * Math.min(1.0f, b2)), component.hsv2);
    }

    public void pickColor(GenericParticle particle, float coeff) {
        TrailParticleBehaviorComponent component = getTrailComponent(particle);
        float h = Mth.rotLerp(coeff, 360 * component.hsv1[0], 360 * component.hsv2[0]) / 360;
        float s = Mth.lerp(coeff, component.hsv1[1], component.hsv2[1]);
        float v = Mth.lerp(coeff, component.hsv1[2], component.hsv2[2]);
        int packed = Color.HSBtoRGB(h, s, v);
        float r = FastColor.ARGB32.red(packed) / 255.0f;
        float g = FastColor.ARGB32.green(packed) / 255.0f;
        float b = FastColor.ARGB32.blue(packed) / 255.0f;
        setColor(particle, r, g, b);
    }

    public void setColor(GenericParticle particle, float r, float g, float b) {
        TrailParticleBehaviorComponent component = getTrailComponent(particle);
        component.r = r;
        component.g = g;
        component.b = b;
    }

    @Override
    public void updateTraits(GenericParticle particle) {
        TrailParticleBehaviorComponent component = getTrailComponent(particle);
        component.trailPointBuilder.addTrailPoint(particle.getPosition());
        component.trailPointBuilder.tickTrailPoints();

        pickColor(particle, colorData.colorCurveEasing.ease(colorData.getProgress(particle.age, particle.lifetime), 0, 1, 1));
        component.a = transparencyData.getValue(particle.age, particle.lifetime, particle.st, particle.mt, particle.et);
    }

    @Override
    public void render(GenericParticle particle, VertexConsumer vertexConsumer, Camera renderInfo, float partialTicks) {
        if (particle.shouldRenderTraits) updateRenderTraits(particle, partialTicks);
        FluffyFurParticles.addBehaviorParticleList(particle, this);
    }

    @Override
    public void render(GenericParticle particle, PoseStack poseStack, MultiBufferSource buffer, float partialTicks) {
        TrailParticleBehaviorComponent component = getTrailComponent(particle);

        List<TrailPoint> trail = new ArrayList<>(component.trailPointBuilder.getTrailPoints());
        if (trail.size() > 2 && particle.getAge() > component.trailPointBuilder.trailLength.get()) {
            TrailPoint position = trail.get(0);
            TrailPoint nextPosition = trail.get(1);
            float x = (float) Mth.lerp(partialTicks, position.getPosition().x, nextPosition.getPosition().x);
            float y = (float) Mth.lerp(partialTicks, position.getPosition().y, nextPosition.getPosition().y);
            float z = (float) Mth.lerp(partialTicks, position.getPosition().z, nextPosition.getPosition().z);
            trail.set(0, new TrailPoint(new Vec3(x, y, z)));
        }

        float x = (float) (Mth.lerp(partialTicks, particle.xo, particle.x));
        float y = (float) (Mth.lerp(partialTicks, particle.yo, particle.y));
        float z = (float) (Mth.lerp(partialTicks, particle.zo, particle.z));

        if (trail.size() > 0) {
            trail.set(trail.size() - 1, new TrailPoint(new Vec3(x, y, z)));
        }

        RenderBuilder builder = RenderBuilder.create().setRenderType(particle.renderType).setSided(firstSide, secondSide)
                .setUV(particle.getU0(), particle.getV0(), particle.getU1(), particle.getV1())
                .setColorRaw(particle.getRed(), particle.getGreen(), particle.getBlue())
                .setAlpha(particle.getAlpha())
                .setLight(particle.getLightColor(partialTicks));
        if (secondColor) {
            builder.setSecondColorRaw(component.r, component.g, component.b)
                    .setSecondAlpha(component.a);
        }
        builder.renderTrail(poseStack, trail, (f) -> {return f * (particle.getSize() / 2f);});
    }
}
