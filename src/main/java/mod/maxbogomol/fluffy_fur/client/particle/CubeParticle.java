package mod.maxbogomol.fluffy_fur.client.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.particle.options.GenericParticleOptions;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.util.RenderUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class CubeParticle extends GenericParticle implements ICustomRenderParticle {

    public CubeParticle(ClientLevel level, GenericParticleOptions data, ParticleEngine.MutableSpriteSet spriteSet, double x, double y, double z, double vx, double vy, double vz) {
        super(level, data, spriteSet, x, y, z, vx, vy, vz);
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTicks) {
        if (shouldRenderTraits) updateRenderTraits(partialTicks);
        FluffyFurParticles.addParticleList(this);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, float partialTicks) {
        double dX = Mth.lerp(partialTicks, this.xo, this.x);
        double dY = Mth.lerp(partialTicks, this.yo, this.y);
        double dZ = Mth.lerp(partialTicks, this.zo, this.z);

        float size = quadSize;
        float size1 = -(size / 2f);

        poseStack.pushPose();
        if (behavior == null) {
            poseStack.translate(dX, dY, dZ);
            poseStack.mulPose(Axis.YP.rotation(Mth.lerp(partialTicks, oRoll, roll)));
        } else {
            Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
            Vec3 vec3 = camera.getPosition();
            Vec3 pos = behavior.getPosition(this, Minecraft.getInstance().gameRenderer.getMainCamera(), partialTicks);
            poseStack.translate((float) pos.x() + vec3.x(), (float) pos.y() + vec3.y(), (float) pos.z() + vec3.z());
            poseStack.mulPose(behavior.getRotate(this, Minecraft.getInstance().gameRenderer.getMainCamera(), partialTicks));
        }
        RenderUtils.litQuadCube(poseStack, buffer, size1, size1, size1, size, size, size, rCol, gCol, bCol, alpha);
        poseStack.popPose();
    }
}
