package mod.maxbogomol.fluffy_fur.client.effect;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.SparkParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.SphereParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.TrailParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.client.screenshake.PositionedScreenshakeInstance;
import mod.maxbogomol.fluffy_fur.client.screenshake.ScreenshakeHandler;
import mod.maxbogomol.fluffy_fur.client.shader.postprocess.GlowPostProcess;
import mod.maxbogomol.fluffy_fur.client.shader.postprocess.GlowPostProcessInstance;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.awt.*;

public class FluffyFurEffects {

    public static Color lightningBoltBlueColor = new Color(159, 250, 255);
    public static Color lightningBoltPurpleColor = new Color(247, 78, 255);
    public static Color explosionRedColor = new Color(196, 46, 34);
    public static Color explosionYellowColor = new Color(239, 180, 81);

    public static void lightningBoltSpawnEffect(Level level, Vec3 pos) {
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(lightningBoltPurpleColor).build())
                .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                .setScaleData(GenericParticleData.create(0.75f, 1f, 0f).setEasing(Easing.QUARTIC_OUT).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.4f).build())
                .setLifetime(50, 10)
                .randomVelocity(0.05f)
                .setFriction(0.9f)
                .disableDistanceSpawn()
                .repeat(level, pos, 10);
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(lightningBoltPurpleColor).build())
                .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                .setScaleData(GenericParticleData.create(0.5f, 0.75f, 0f).setEasing(Easing.QUARTIC_OUT).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.4f).build())
                .setLifetime(50, 10)
                .randomVelocity(0.1f)
                .disableDistanceSpawn()
                .repeat(level, pos, 10);
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(Color.WHITE).build())
                .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                .setScaleData(GenericParticleData.create(0.4f, 0.5f, 0f).setEasing(Easing.QUARTIC_OUT).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.4f).build())
                .setLifetime(50, 10)
                .randomVelocity(0.05f)
                .disableDistanceSpawn()
                .repeat(level, pos, 10);
        ParticleBuilder.create(FluffyFurParticles.SQUARE)
                .setRenderType(FluffyFurRenderTypes.ADDITIVE_PARTICLE_TEXTURE)
                .setBehavior(SphereParticleBehavior.create().disableSecondSide().setSphereSize(8, 4).build())
                .setColorData(ColorParticleData.create(lightningBoltBlueColor).build())
                .setTransparencyData(GenericParticleData.create(0.25f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0f, 5f).setEasing(Easing.QUARTIC_OUT).build())
                .setLifetime(100)
                .disableDistanceSpawn()
                .spawn(level, pos);
        ScreenshakeHandler.addScreenshake(new PositionedScreenshakeInstance(40, pos, 0, 25).setIntensity(0.6f, 0).setEasing(Easing.QUINTIC_IN_OUT).disableNormalize());
        GlowPostProcess.INSTANCE.addInstance(new GlowPostProcessInstance(pos.toVector3f(), new Vector3f(1, 1, 1)).setRadius(10).setIntensity(2).setFadeTime(20));
    }

    public static void lightningBoltTickEffect(Level level, Vec3 pos) {
        ParticleBuilder.create(FluffyFurParticles.SQUARE)
                .setRenderType(FluffyFurRenderTypes.ADDITIVE_PARTICLE_TEXTURE)
                .setBehavior(TrailParticleBehavior.create().setTrailSize(5).setWidthFunction(RenderUtil.LINEAR_IN_ROUND_WIDTH_FUNCTION).build())
                .setColorData(ColorParticleData.create(Color.WHITE).build())
                .setTransparencyData(GenericParticleData.create(0.5f, 0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.15f, 0.3f,  0).setEasing(Easing.ELASTIC_OUT).build())
                .setLifetime(20)
                .randomVelocity(0.75f)
                .addVelocity(0, 0.4f, 0)
                .randomOffset(0.25f)
                .setFriction(0.88f)
                .setGravity(1f)
                .disableDistanceSpawn()
                .repeat(level, pos, 10, 0.8f);
        ParticleBuilder.create(FluffyFurParticles.SQUARE)
                .setColorData(ColorParticleData.create(lightningBoltBlueColor).build())
                .setTransparencyData(GenericParticleData.create(0.5f, 0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.1f, 0.2f,  0).setEasing(Easing.ELASTIC_OUT).build())
                .setSpinData(SpinParticleData.create().randomSpin(1f).build())
                .setLifetime(40)
                .randomVelocity(0.7f)
                .addVelocity(0, 0.3f, 0)
                .randomOffset(0.25f)
                .setFriction(0.9f)
                .setGravity(1f)
                .disableDistanceSpawn()
                .spawn(level, pos);
    }

    public static void lightningBoltRender(LightningBolt entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        RenderBuilder builder = RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE);

        float[] afloat = new float[8];
        float[] afloat1 = new float[8];
        float f = 0.0F;
        float f1 = 0.0F;
        RandomSource randomsource = RandomSource.create(entity.seed);

        for(int i = 7; i >= 0; --i) {
            afloat[i] = f;
            afloat1[i] = f1;
            f += (float)(randomsource.nextInt(11) - 5);
            f1 += (float)(randomsource.nextInt(11) - 5);
        }

        RandomSource randomsource1 = RandomSource.create(entity.seed);

        for(int k = 0; k < 3; ++k) {
            int l = 7;
            int i1 = 0;
            if (k > 0) {
                l = 7 - k;
            }

            if (k > 0) {
                i1 = l - 2;
            }

            float f2 = afloat[l] - f;
            float f3 = afloat1[l] - f1;

            for(int j1 = l; j1 >= i1; --j1) {
                float f4 = f2;
                float f5 = f3;
                if (k == 0) {
                    f2 += (float)(randomsource1.nextInt(11) - 5);
                    f3 += (float)(randomsource1.nextInt(11) - 5);
                } else {
                    f2 += (float)(randomsource1.nextInt(31) - 15);
                    f3 += (float)(randomsource1.nextInt(31) - 15);
                }

                double dX = f4 - f2;
                double dY = (j1 * 16 + 16) - (j1 * 16);
                double dZ = f5 - f3;

                double yaw = Math.atan2(dZ, dX);
                double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;
                double distance = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2) + Math.pow(dZ, 2));

                poseStack.pushPose();
                poseStack.translate(f2, j1 * 16, f3);
                poseStack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(-yaw)));
                poseStack.mulPose(Axis.ZP.rotationDegrees((float) Math.toDegrees(-pitch) - 180f));
                builder.setColor(Color.WHITE)
                        .setAlpha(0.5f)
                        .renderBeam(poseStack, 0.2f, (float) distance, 1f)
                        .setColor(lightningBoltPurpleColor)
                        .setAlpha(0.25f)
                        .renderBeam(poseStack, 0.4f, (float) distance, 1f)
                        .setColor(lightningBoltBlueColor)
                        .setAlpha(0.15f)
                        .renderBeam(poseStack, 0.75f, (float) distance, 1f);
                poseStack.popPose();
            }
        }
    }

    public static void explosionEffect(Level level, Vec3 pos, float radius) {
        System.out.println(radius);
        float r = 0.5f + (radius / 4f);
        float rr = 0.75f + (radius / 8f);
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(explosionRedColor, explosionYellowColor).build())
                .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                .setScaleData(GenericParticleData.create(0.75f * r, r, 0f).setEasing(Easing.QUARTIC_OUT).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.4f).build())
                .setLifetime(20, 5)
                .randomVelocity(0.1f * rr)
                .setFriction(0.9f)
                .repeat(level, pos, 10);
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(explosionRedColor, explosionYellowColor).build())
                .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                .setScaleData(GenericParticleData.create(0.5f * r, 1.5f * r, 0f).setEasing(Easing.QUARTIC_OUT).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.8f).build())
                .setLifetime(40, 10)
                .randomVelocity(0.2f * rr)
                .repeat(level, pos, 25);
        ParticleBuilder.create(FluffyFurParticles.SMOKE)
                .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_PARTICLE)
                .setColorData(ColorParticleData.create(Color.BLACK).build())
                .setTransparencyData(GenericParticleData.create(0.7f, 0).build())
                .setScaleData(GenericParticleData.create(0.25f * r, r, 0f).setEasing(Easing.QUARTIC_OUT).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.25f).build())
                .setLifetime(60, 10)
                .randomVelocity(0.2f * rr)
                .addVelocity(0, 0.1, 0)
                .setFriction(0.92f)
                .repeat(level, pos, 30);
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_PARTICLE)
                .setColorData(ColorParticleData.create(Color.BLACK).build())
                .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                .setScaleData(GenericParticleData.create(0.75f * r, r, 0f).setEasing(Easing.QUARTIC_OUT).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.4f).build())
                .setLifetime(80, 30)
                .randomVelocity(0.1f * rr)
                .setFriction(0.9f)
                .repeat(level, pos, 5);
        ParticleBuilder.create(FluffyFurParticles.CIRCLE)
                .setBehavior(SparkParticleBehavior.create().build())
                .setColorData(ColorParticleData.create(explosionRedColor, explosionYellowColor).build())
                .setTransparencyData(GenericParticleData.create(0.5f, 0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.25f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .setLifetime(30)
                .randomVelocity(1.5f * rr)
                .addVelocity(0, 0.2f * rr, 0)
                .randomOffset(0.25f)
                .setFriction(0.88f)
                .setGravity(1f)
                .repeat(level, pos, 50, 0.7f);
        ScreenshakeHandler.addScreenshake(new PositionedScreenshakeInstance(30, pos, 0, 25).setIntensity(0.8f, 0).setEasing(Easing.QUINTIC_IN_OUT).disableNormalize());
    }
}
