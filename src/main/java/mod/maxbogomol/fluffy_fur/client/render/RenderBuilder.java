package mod.maxbogomol.fluffy_fur.client.render;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.trail.TrailPoint;
import mod.maxbogomol.fluffy_fur.client.render.trail.TrailRenderPoint;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

import static net.minecraft.util.Mth.sqrt;

public class RenderBuilder {
    protected float r1 = 1, g1 = 1, b1 = 1, a1 = 1;
    protected float r2 = 1, g2 = 1, b2 = 1, a2 = 1;
    protected int l1 = RenderUtil.FULL_BRIGHT;
    protected int l2 = RenderUtil.FULL_BRIGHT;
    protected float u0 = 0, v0 = 0, u1 = 1, v1 = 1;
    protected boolean sided;

    protected MultiBufferSource bufferSource = LevelRenderHandler.DELAYED_RENDER;
    protected RenderType renderType;
    protected VertexFormat format;
    protected VertexConsumerActor supplier;
    protected VertexConsumer vertexConsumer;

    public static final HashMap<VertexFormatElement, VertexConsumerActor> CONSUMER_INFO_MAP = new HashMap<>();

    private static final float ROOT_3 = (float)(Math.sqrt(3.0D) / 2.0D);

    static {
        CONSUMER_INFO_MAP.put(DefaultVertexFormat.ELEMENT_POSITION, (consumer, last, builder, x, y, z, r, g, b, a, u, v, l) -> {
            if (last == null) {
                consumer.vertex(x, y, z);
            } else {
                consumer.vertex(last, x, y, z);
            }
        });
        CONSUMER_INFO_MAP.put(DefaultVertexFormat.ELEMENT_COLOR, (consumer, last, builder, x, y, z, r, g, b, a, u, v, l) -> consumer.color(r, g, b, a));
        CONSUMER_INFO_MAP.put(DefaultVertexFormat.ELEMENT_UV0, (consumer, last, builder, x, y, z, r, g, b, a, u, v, l) -> consumer.uv(u, v));
        CONSUMER_INFO_MAP.put(DefaultVertexFormat.ELEMENT_UV2, (consumer, last, builder, x, y, z, r, g, b, a, u, v, l) -> consumer.uv2(l));
    }

    public static RenderBuilder create() {
        return new RenderBuilder();
    }

    public RenderBuilder replaceBufferSource(MultiBufferSource bufferSource) {
        this.bufferSource = bufferSource;
        return this;
    }

    public RenderBuilder setRenderType(RenderType renderType) {
        return setRenderTypeRaw(renderType).setFormat(renderType.format()).setVertexConsumer(bufferSource.getBuffer(renderType));
    }

    public RenderBuilder setRenderTypeRaw(RenderType renderType) {
        this.renderType = renderType;
        return this;
    }

    public RenderBuilder setFormat(VertexFormat format) {
        ImmutableList<VertexFormatElement> elements = format.getElements();
        return setFormatRaw(format).setVertexSupplier((consumer, last, builder, x, y, z, r, g, b, a, u, v, l) -> {
            for (VertexFormatElement element : elements) {
                CONSUMER_INFO_MAP.get(element).placeVertex(consumer, last, this, x, y, z, r, g, b, a, u, v, l);
            }
            consumer.endVertex();
        });
    }

    public RenderBuilder setFormatRaw(VertexFormat format) {
        this.format = format;
        return this;
    }

    public RenderBuilder setVertexSupplier(VertexConsumerActor supplier) {
        this.supplier = supplier;
        return this;
    }

    public RenderBuilder setVertexConsumer(VertexConsumer vertexConsumer) {
        this.vertexConsumer = vertexConsumer;
        return this;
    }

    public VertexConsumer getVertexConsumer() {
        if (vertexConsumer == null) {
            setVertexConsumer(bufferSource.getBuffer(renderType));
        }
        return vertexConsumer;
    }

    public MultiBufferSource getBufferSource() {
        return bufferSource;
    }

    public RenderType getRenderType() {
        return renderType;
    }

    public VertexFormat getFormat() {
        return format;
    }

    public VertexConsumerActor getSupplier() {
        return supplier;
    }

    public RenderBuilder setColor(Color color) {
        return setColor(color.getRed(), color.getGreen(), color.getBlue());
    }

    public RenderBuilder setColor(Color color, float a) {
        return setFirstColor(color).setFirstAlpha(a).setSecondColor(color).setSecondAlpha(a);
    }

    public RenderBuilder setColor(float r, float g, float b, float a) {
        return setFirstColor(r, g, b).setFirstAlpha(a).setSecondColor(r, g, b).setSecondAlpha(a);
    }

    public RenderBuilder setColor(float r, float g, float b) {
        setFirstColor(r, g, b).setSecondColor(r, g, b);
        return this;
    }

    public RenderBuilder setColorRaw(float r, float g, float b) {
        return setFirstColorRaw(r, g, b).setSecondColorRaw(r, g, b);
    }

    public RenderBuilder setAlpha(float a) {
        return setFirstAlpha(a).setSecondAlpha(a);
    }

    public RenderBuilder setLight(int light) {
        return setFirstLight(light).setSecondLight(light);
    }

    public RenderBuilder setFirstColor(Color color) {
        return setFirstColor(color.getRed(), color.getGreen(), color.getBlue());
    }

    public RenderBuilder setFirstColor(Color color, float a) {
        return setFirstColor(color).setFirstAlpha(a);
    }

    public RenderBuilder setFirstColor(float r, float g, float b, float a) {
        return setFirstColor(r, g, b).setFirstAlpha(a);
    }

    public RenderBuilder setFirstColor(float r, float g, float b) {
        this.r1 = r / 255f;
        this.g1 = g / 255f;
        this.b1 = b / 255f;
        return this;
    }

    public RenderBuilder setFirstColorRaw(float r, float g, float b) {
        this.r1 = r;
        this.g1 = g;
        this.b1 = b;
        return this;
    }

    public RenderBuilder setFirstAlpha(float a) {
        this.a1 = a;
        return this;
    }

    public RenderBuilder setFirstLight(int light) {
        this.l1 = light;
        return this;
    }

    public RenderBuilder setSecondColor(Color color) {
        return setSecondColor(color.getRed(), color.getGreen(), color.getBlue());
    }

    public RenderBuilder setSecondColor(Color color, float a) {
        return setSecondColor(color).setSecondAlpha(a);
    }

    public RenderBuilder setSecondColor(float r, float g, float b, float a) {
        return setSecondColor(r, g, b).setSecondAlpha(a);
    }

    public RenderBuilder setSecondColor(float r, float g, float b) {
        this.r2 = r / 255f;
        this.g2 = g / 255f;
        this.b2 = b / 255f;
        return this;
    }

    public RenderBuilder setSecondColorRaw(float r, float g, float b) {
        this.r2 = r;
        this.g2 = g;
        this.b2 = b;
        return this;
    }

    public RenderBuilder setSecondAlpha(float a) {
        this.a2 = a;
        return this;
    }

    public RenderBuilder setSecondLight(int light) {
        this.l1 = light;
        return this;
    }

    public RenderBuilder setUV(float u0, float v0, float u1, float v1) {
        this.u0 = u0;
        this.v0 = v0;
        this.u1 = u1;
        this.v1 = v1;
        return this;
    }

    public RenderBuilder setUV(TextureAtlasSprite sprite) {
        setUV(sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1());
        return this;
    }

    public RenderBuilder enableSided() {
        return setSided(true);
    }

    public RenderBuilder disableSided() {
        return setSided(false);
    }

    public RenderBuilder setSided(boolean sided) {
        this.sided = sided;
        return this;
    }

    public RenderBuilder endBatch() {
        ((MultiBufferSource.BufferSource) getBufferSource()).endBatch();
        return this;
    }

    public RenderBuilder renderBeam(@Nullable Matrix4f last, Vec3 start, Vec3 end, float width) {
        Minecraft minecraft = Minecraft.getInstance();
        Vec3 cameraPosition = minecraft.getBlockEntityRenderDispatcher().camera.getPosition();
        return renderBeam(last, start, end, width, cameraPosition);
    }

    public RenderBuilder renderBeam(@Nullable Matrix4f last, Vec3 start, Vec3 end, float width, Vec3 cameraPosition) {
        Vec3 delta = end.subtract(start);
        Vec3 normal = start.subtract(cameraPosition).cross(delta).normalize().multiply(width / 2f, width / 2f, width / 2f);

        Vec3[] positions = new Vec3[]{start.subtract(normal), start.add(normal), end.add(normal), end.subtract(normal)};

        supplier.placeVertex(getVertexConsumer(), last, this, (float) positions[0].x, (float) positions[0].y, (float) positions[0].z, r1, g1, b1, a1, u0, v1, l1);
        supplier.placeVertex(getVertexConsumer(), last, this, (float) positions[1].x, (float) positions[1].y, (float) positions[1].z, r1, g1, b1, a1, u1, v1, l1);
        supplier.placeVertex(getVertexConsumer(), last, this, (float) positions[2].x, (float) positions[2].y, (float) positions[2].z, r2, g2, b2, a2, u1, v0, l2);
        supplier.placeVertex(getVertexConsumer(), last, this, (float) positions[3].x, (float) positions[3].y, (float) positions[3].z, r2, g2, b2, a2, u0, v0, l2);

        return this;
    }

    public RenderBuilder renderTrail(PoseStack stack, List<TrailPoint> trailSegments, float width) {
        return renderTrail(stack, trailSegments, f -> width, f -> {
        });
    }

    public RenderBuilder renderTrail(PoseStack stack, List<TrailPoint> trailSegments, Function<Float, Float> widthFunc) {
        return renderTrail(stack, trailSegments, widthFunc, f -> {
        });
    }

    public RenderBuilder renderTrail(PoseStack stack, List<TrailPoint> trailSegments, Function<Float, Float> widthFunc, Consumer<Float> vfxOperator) {
        return renderTrail(stack.last().pose(), trailSegments, widthFunc, vfxOperator);
    }

    public RenderBuilder renderTrail(Matrix4f pose, List<TrailPoint> trailSegments, Function<Float, Float> widthFunc, Consumer<Float> vfxOperator) {
        if (trailSegments.size() < 2) {
            return this;
        }
        List<Vector4f> positions = trailSegments.stream().map(TrailPoint::getMatrixPosition).peek(p -> p.mul(pose)).toList();
        int count = trailSegments.size() - 1;
        float increment = 1.0F / count;
        TrailRenderPoint[] points = new TrailRenderPoint[trailSegments.size()];
        for (int i = 1; i < count; i++) {
            float width = widthFunc.apply(increment * i);
            Vector4f previous = positions.get(i - 1);
            Vector4f current = positions.get(i);
            Vector4f next = positions.get(i + 1);
            points[i] = new TrailRenderPoint(current, perpendicularTrailPoints(previous, next, width));
        }
        points[0] = new TrailRenderPoint(positions.get(0), perpendicularTrailPoints(positions.get(0), positions.get(1), widthFunc.apply(0f)));
        points[count] = new TrailRenderPoint(positions.get(count), perpendicularTrailPoints(positions.get(count-1), positions.get(count), widthFunc.apply(1f)));
        return renderPoints(points, u0, v0, u1, v1, vfxOperator);
    }

    public RenderBuilder renderTrail(List<TrailPoint> trailSegments, Function<Float, Float> widthFunc, Consumer<Float> vfxOperator) {
        if (trailSegments.size() < 2) {
            return this;
        }
        List<Vector4f> positions = trailSegments.stream().map(TrailPoint::getMatrixPosition).toList();
        int count = trailSegments.size() - 1;
        float increment = 1.0F / count;
        TrailRenderPoint[] points = new TrailRenderPoint[trailSegments.size()];
        for (int i = 1; i < count; i++) {
            float width = widthFunc.apply(increment * i);
            Vector4f previous = positions.get(i - 1);
            Vector4f current = positions.get(i);
            Vector4f next = positions.get(i + 1);
            points[i] = new TrailRenderPoint(current, perpendicularTrailPoints(previous, next, width));
        }
        points[0] = new TrailRenderPoint(positions.get(0), perpendicularTrailPoints(positions.get(0), positions.get(1), widthFunc.apply(0f)));
        points[count] = new TrailRenderPoint(positions.get(count), perpendicularTrailPoints(positions.get(count-1), positions.get(count), widthFunc.apply(1f)));
        return renderPoints(points, u0, v0, u1, v1, vfxOperator);
    }

    public RenderBuilder renderPoints(TrailRenderPoint[] points, float u0, float v0, float u1, float v1, Consumer<Float> vfxOperator) {
        int count = points.length - 1;
        float increment = 1.0F / count;
        vfxOperator.accept(0f);
        points[0].renderStart(getVertexConsumer(), this, u0, v0, u1, Mth.lerp(increment, v0, v1), r1, g1, b1, a1, l1);
        for (int i = 1; i < count; i++) {
            float current = Mth.lerp(i * increment, v0, v1);
            vfxOperator.accept(current);
            points[i].renderMid(getVertexConsumer(), this, u0, current, u1, current, r1, g1, b1, a1, l1);
        }
        vfxOperator.accept(1f);
        points[count].renderEnd(getVertexConsumer(), this, u0, Mth.lerp((count) * increment, v0, v1), u1, v1, r1, g1, b1, a1, l1);
        return this;
    }

    public RenderBuilder renderQuad(PoseStack stack, float size) {
        return renderQuad(stack, size, size);
    }

    public RenderBuilder renderQuad(PoseStack stack, float width, float height) {
        Vector3f[] positions = new Vector3f[]{new Vector3f(0, 0, 0), new Vector3f(1, 0, 0), new Vector3f(1, 1, 0), new Vector3f(0, 1, 0)};
        return renderQuad(stack, positions, width, height);
    }

    public RenderBuilder renderCenteredQuad(PoseStack stack, float size) {
        return renderCenteredQuad(stack, size, size);
    }

    public RenderBuilder renderCenteredQuad(PoseStack stack, float width, float height) {
        Vector3f[] positions = new Vector3f[]{new Vector3f(-1, -1, 0), new Vector3f(1, -1, 0), new Vector3f(1, 1, 0), new Vector3f(-1, 1, 0)};
        return renderQuad(stack, positions, width, height);
    }

    public RenderBuilder renderQuad(PoseStack stack, Vector3f[] positions, float size) {
        return renderQuad(stack, positions, size, size);
    }

    public RenderBuilder renderQuad(PoseStack stack, Vector3f[] positions, float width, float height) {
        for (Vector3f position : positions) {
            position.mul(width, height, width);
        }
        return renderQuad(stack.last().pose(), positions);
    }

    public RenderBuilder renderQuad(Matrix4f last, Vector3f[] positions) {
        supplier.placeVertex(getVertexConsumer(), last, this, positions[3].x(), positions[3].y(), positions[3].z(), r1, g1, b1, a1, u0, v1, l1);
        supplier.placeVertex(getVertexConsumer(), last, this, positions[2].x(), positions[2].y(), positions[2].z(), r1, g1, b1, a1, u1, v1, l1);
        supplier.placeVertex(getVertexConsumer(), last, this, positions[1].x(), positions[1].y(), positions[1].z(), r1, g1, b1, a1, u1, v0, l1);
        supplier.placeVertex(getVertexConsumer(), last, this, positions[0].x(), positions[0].y(), positions[0].z(), r1, g1, b1, a1, u0, v0, l1);

        if (sided) {
            supplier.placeVertex(getVertexConsumer(), last, this, positions[0].x(), positions[0].y(), positions[0].z(), r1, g1, b1, a1, u0, v1, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[1].x(), positions[1].y(), positions[1].z(), r1, g1, b1, a1, u1, v1, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[2].x(), positions[2].y(), positions[2].z(), r1, g1, b1, a1, u1, v0, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[3].x(), positions[3].y(), positions[3].z(), r1, g1, b1, a1, u0, v0, l1);
        }

        return this;
    }

    public RenderBuilder renderWavyQuad(PoseStack stack, float size, float wave, float tick1, float tick2, float tick3, float tick4) {
        return renderWavyQuad(stack, size, size, wave, tick1, tick2, tick3, tick4);
    }

    public RenderBuilder renderWavyQuad(PoseStack stack, float width, float height, float wave, float tick1, float tick2, float tick3, float tick4) {
        Vector3f[] positions = new Vector3f[]{
                new Vector3f(-1 + (wave * (float) Math.sin(Math.toRadians(tick1))), -1 + (wave * (float) Math.cos(Math.toRadians(tick1))), 0),
                new Vector3f(1 + (wave * (float) Math.sin(Math.toRadians(tick2))), -1 + (wave * (float) Math.cos(Math.toRadians(tick2))), 0),
                new Vector3f(1 + (wave * (float) Math.sin(Math.toRadians(tick3))), 1 + (wave * (float) Math.cos(Math.toRadians(tick3))), 0),
                new Vector3f(-1 + (wave * (float) Math.sin(Math.toRadians(tick4))), 1 + (wave * (float) Math.cos(Math.toRadians(tick4))), 0)};
        return renderQuad(stack, positions, width, height);
    }

    public RenderBuilder renderDragon(PoseStack stack, double x, double y, double z, float radius, float partialTicks, float randomF) {
        float f5 = 0.5f;
        float f7 = Math.min(f5 > 0.8F ? (f5 - 0.8F) / 0.2F : 0.0F, 1.0F);
        Random random = new Random((long) (432L + randomF));
        VertexConsumer builder = bufferSource.getBuffer(renderType);
        stack.pushPose();
        stack.translate(x, y, z);

        float rotation = (ClientTickHandler.ticksInGame + partialTicks) / 200;

        for(int i = 0; (float)i < (f5 + f5 * f5) / 2.0F * 60.0F; ++i) {
            stack.mulPose(Axis.XP.rotationDegrees(random.nextFloat() * 360.0F));
            stack.mulPose(Axis.YP.rotationDegrees(random.nextFloat() * 360.0F));
            stack.mulPose(Axis.ZP.rotationDegrees(random.nextFloat() * 360.0F));
            stack.mulPose(Axis.XP.rotationDegrees(random.nextFloat() * 360.0F));
            stack.mulPose(Axis.YP.rotationDegrees(random.nextFloat() * 360.0F));
            stack.mulPose(Axis.ZP.rotationDegrees(random.nextFloat() * 360.0F + rotation * 90.0F));
            float f3 = random.nextFloat() * 20.0F + 5.0F + f7 * 10.0F;
            float f4 = random.nextFloat() * 2.0F + 1.0F + f7 * 2.0F;
            f3 *= 0.05f * radius;
            f4 *= 0.05f * radius;
            Matrix4f last = stack.last().pose();
            float alpha = (1 - f7) * a1;

            supplier.placeVertex(getVertexConsumer(), last, this, 0.0F, 0.0F, 0.0F, r1, g1, b1, alpha, 0, 0, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, 0.0F, 0.0F, 0.0F, r1, g1, b1, alpha, 0, 0, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, -ROOT_3 * f4, f3, -0.5F * f4, r2, g2, b2, 0, 0, 0, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, ROOT_3 * f4, f3, -0.5F * f4, r2, g2, b2, 0, 0, 0, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, 0.0F, 0.0F, 0.0F, r1, g1, b1, alpha, 0, 0, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, 0.0F, 0.0F, 0.0F, r1, g1, b1, alpha, 0, 0, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, ROOT_3 * f4, f3, -0.5F * f4, r2, g2, b2, 0, 0, 0, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, 0.0F, f3, f4, r2, g2, b2, 0, 0, 0, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, 0.0F, 0.0F, 0.0F, r1, g1, b1, alpha, 0, 0, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, 0.0F, 0.0F, 0.0F, r1, g1, b1, alpha, 0, 0, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, 0.0F, f3, f4, r2, g2, b2, 0, 0, 0, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, -ROOT_3 * f4, f3, -0.5F * f4, r2, g2, b2, 0, 0, 0, l2);
        }
        stack.popPose();

        return this;
    }

    public static Vec2 perpendicularTrailPoints(Vector4f start, Vector4f end, float width) {
        float x = -start.x();
        float y = -start.y();
        if (Math.abs(start.z()) > 0) {
            float ratio = end.z() / start.z();
            x = end.x() + x * ratio;
            y = end.y() + y * ratio;
        } else if (Math.abs(end.z()) <= 0) {
            x += end.x();
            y += end.y();
        }
        if (start.z() > 0) {
            x = -x;
            y = -y;
        }
        if (x * x + y * y > 0F) {
            float normalize = width * 0.5F / distance(x, y);
            x *= normalize;
            y *= normalize;
        }
        return new Vec2(-y, x);
    }

    public static float distance(float... a) {
        return sqrt(distSqr(a));
    }

    public static float distSqr(float... a) {
        float d = 0.0F;
        for (float f : a) {
            d += f * f;
        }
        return d;
    }

    public interface VertexConsumerActor {
        void placeVertex(VertexConsumer consumer, Matrix4f last, RenderBuilder builder, float x, float y, float z, float r, float g, float b, float a, float u, float v, int l);
    }
}
