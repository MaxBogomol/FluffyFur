package mod.maxbogomol.fluffy_fur.client.render.trail;

import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import net.minecraft.world.phys.Vec2;
import org.joml.Vector4f;

public class TrailRenderPoint {

    public final float xp;
    public final float xn;
    public final float yp;
    public final float yn;
    public final float z;

    public TrailRenderPoint(float xp, float xn, float yp, float yn, float z) {
        this.xp = xp;
        this.xn = xn;
        this.yp = yp;
        this.yn = yn;
        this.z = z;
    }

    public TrailRenderPoint(Vector4f pos, Vec2 perp) {
        this(pos.x() + perp.x, pos.x() - perp.x, pos.y() + perp.y, pos.y() - perp.y, pos.z());
    }

    public void renderStart(VertexConsumer vertexConsumer, RenderBuilder builder, float u0, float v0, float u1, float v1, float r, float g, float b, float a, int light) {
        builder.getSupplier().placeVertex(vertexConsumer, null, builder, xp, yp, z, r, g, b, a, u0, v0, light);
        builder.getSupplier().placeVertex(vertexConsumer, null, builder, xn, yn, z, r, g, b, a, u1, v0, light);
    }

    public void renderEnd(VertexConsumer vertexConsumer, RenderBuilder builder, float u0, float v0, float u1, float v1, float r, float g, float b, float a, int light) {
        builder.getSupplier().placeVertex(vertexConsumer, null, builder, xn, yn, z, r, g, b, a, u1, v1, light);
        builder.getSupplier().placeVertex(vertexConsumer, null, builder, xp, yp, z, r, g, b, a, u0, v1, light);
    }

    public void renderMid(VertexConsumer vertexConsumer, RenderBuilder builder, float u0, float v0, float u1, float v1, float r, float g, float b, float a, int light) {
        renderEnd(vertexConsumer, builder, u0, v0, u1, v1, r, g, b, a, light);
        renderStart(vertexConsumer, builder, u0, v0, u1, v1, r, g, b, a, light);
    }
}