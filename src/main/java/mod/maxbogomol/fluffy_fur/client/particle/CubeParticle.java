package mod.maxbogomol.fluffy_fur.client.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.render.LevelRenderHandler;
import mod.maxbogomol.fluffy_fur.utils.RenderUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;

public class CubeParticle extends GenericParticle implements ICustomRenderParticle {

    public CubeParticle(ClientLevel world, GenericParticleOptions data, double x, double y, double z, double vx, double vy, double vz) {
        super(world, data, x, y, z, vx, vy, vz);
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTicks) {
        LevelRenderHandler.particleList.add(this);
    }

    private void decoVert(VertexConsumer vc, float u, float v, float alpha, int lmap) {
        vc.uv(u, v).color(rCol, gCol, bCol, alpha).uv2(lmap).endVertex();
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource buffer, float partialTicks) {
        double dX = Mth.lerp(partialTicks, this.xo, this.x);
        double dY = Mth.lerp(partialTicks, this.yo, this.y);
        double dZ = Mth.lerp(partialTicks, this.zo, this.z);

        float size = scaleData.getValue(age + partialTicks, lifetime);
        float size1 = -(size / 2f);

        stack.pushPose();
        stack.translate(dX, dY, dZ);
        stack.mulPose(Axis.YP.rotation(Mth.lerp(partialTicks, oRoll, roll)));
        RenderUtils.litQuadCube(stack, buffer, size1, size1, size1, size, size, size, rCol, gCol, bCol, alpha);
        stack.popPose();
    }
}
