package mod.maxbogomol.fluffy_fur.client.particle.behavior;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.maxbogomol.fluffy_fur.client.particle.GenericParticle;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.phys.Vec3;

public class SphereParticleBehavior extends ParticleBehavior implements ICustomBehaviorParticleRender {

    public int longs;
    public int lats;
    public boolean stretch;

    public SphereParticleBehavior(int longs, int lats, boolean stretch, SpinParticleData xSpinData, SpinParticleData ySpinData, SpinParticleData zSpinData, float xOffset, float yOffset, float zOffset, boolean firstSide, boolean secondSide, boolean camera, boolean xRotCam, boolean yRotCam) {
        super(xSpinData, ySpinData, zSpinData, xOffset, yOffset, zOffset, firstSide, secondSide, camera, xRotCam, yRotCam);
        this.longs = longs;
        this.lats = lats;
        this.stretch = stretch;
    }

    public SphereParticleBehavior copy() {
        return new SphereParticleBehavior(longs, lats, stretch, xSpinData, ySpinData, zSpinData, xOffset, yOffset, zOffset, firstSide, secondSide, camera, xRotCam, yRotCam);
    }

    public static SphereParticleBehaviorBuilder create() {
        return new SphereParticleBehaviorBuilder(0, 0, 0);
    }

    public static SphereParticleBehaviorBuilder create(float xOffset, float yOffset, float zOffset) {
        return new SphereParticleBehaviorBuilder((float) Math.toRadians(xOffset), (float) Math.toRadians(yOffset), (float) Math.toRadians(zOffset));
    }

    @Override
    public void render(GenericParticle particle, VertexConsumer vertexConsumer, Camera renderInfo, float partialTicks) {
        if (particle.shouldRenderTraits) updateRenderTraits(particle, partialTicks);
        FluffyFurParticles.addBehaviorParticleList(particle, this);
    }

    @Override
    public void render(GenericParticle particle, PoseStack poseStack, MultiBufferSource buffer, float partialTicks) {
        poseStack.pushPose();
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        Vec3 vec3 = camera.getPosition();
        Vec3 pos = getPosition(particle, Minecraft.getInstance().gameRenderer.getMainCamera(), partialTicks);
        poseStack.translate((float) pos.x() + vec3.x(), (float) pos.y() + vec3.y(), (float) pos.z() + vec3.z());
        poseStack.mulPose(getRotate(particle, Minecraft.getInstance().gameRenderer.getMainCamera(), partialTicks));

        RenderBuilder builder = RenderBuilder.create();
        if (particle.bufferSource != null) builder.replaceBufferSource(particle.bufferSource);
        builder.setRenderType(particle.renderType);
        if (particle.format != null) builder.setFormat(particle.format);
        builder.setSided(firstSide, secondSide)
                .setUV(particle.getU0(), particle.getV0(), particle.getU1(), particle.getV1())
                .setColorRaw(particle.getRed(), particle.getGreen(), particle.getBlue())
                .setAlpha(particle.getAlpha())
                .setLight(particle.getLightColor(partialTicks))
                .renderSphere(poseStack, particle.getSize() / 2f, longs, lats, stretch);
        poseStack.popPose();
    }
}
