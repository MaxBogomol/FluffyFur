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

public class RenderBuilder {
    protected float r1 = 1, g1 = 1, b1 = 1, a1 = 1;
    protected float r2 = 1, g2 = 1, b2 = 1, a2 = 1;
    protected float r3 = 1, g3 = 1, b3 = 1, a3 = 1;
    protected int l1 = RenderUtil.FULL_BRIGHT;
    protected int l2 = RenderUtil.FULL_BRIGHT;
    protected int l3 = RenderUtil.FULL_BRIGHT;
    protected float u0 = 0, v0 = 0, u1 = 1, v1 = 1;
    protected float u2 = 0, v2 = 0, u3 = 1, v3 = 1;
    protected float u4 = 0, v4 = 0, u5 = 1, v5 = 1;
    protected boolean firstSide = true;
    protected boolean secondSide = false;

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
        return setFirstColor(color).setFirstAlpha(a).setSecondColor(color).setSecondAlpha(a).setThirdColor(color).setThirdAlpha(a);
    }

    public RenderBuilder setColor(float r, float g, float b, float a) {
        return setFirstColor(r, g, b).setFirstAlpha(a).setSecondColor(r, g, b).setSecondAlpha(a).setThirdColor(r, g, b).setThirdAlpha(a);
    }

    public RenderBuilder setColor(float r, float g, float b) {
        setFirstColor(r, g, b).setSecondColor(r, g, b).setThirdColor(r, g, b);
        return this;
    }

    public RenderBuilder setColorRaw(float r, float g, float b) {
        return setFirstColorRaw(r, g, b).setSecondColorRaw(r, g, b).setThirdColorRaw(r, g, b);
    }

    public RenderBuilder setAlpha(float a) {
        return setFirstAlpha(a).setSecondAlpha(a).setThirdAlpha(a);
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
        this.l2 = light;
        return this;
    }

    public RenderBuilder setThirdColor(Color color) {
        return setThirdColor(color.getRed(), color.getGreen(), color.getBlue());
    }

    public RenderBuilder setThirdColor(Color color, float a) {
        return setThirdColor(color).setThirdAlpha(a);
    }

    public RenderBuilder setThirdColor(float r, float g, float b, float a) {
        return setThirdColor(r, g, b).setThirdAlpha(a);
    }

    public RenderBuilder setThirdColor(float r, float g, float b) {
        this.r3 = r / 255f;
        this.g3 = g / 255f;
        this.b3 = b / 255f;
        return this;
    }

    public RenderBuilder setThirdColorRaw(float r, float g, float b) {
        this.r3 = r;
        this.g3 = g;
        this.b3 = b;
        return this;
    }

    public RenderBuilder setThirdAlpha(float a) {
        this.a3 = a;
        return this;
    }

    public RenderBuilder setThirdLight(int light) {
        this.l3 = light;
        return this;
    }

    public RenderBuilder setUV(float u0, float v0, float u1, float v1) {
        return setFirstUV(u0, v0, u1, v1).setSecondUV(u0, v0, u1, v1).setThirdUV(u0, v0, u1, v1);
    }

    public RenderBuilder setUV(TextureAtlasSprite sprite) {
        return setUV(sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1());
    }

    public RenderBuilder setFirstUV(float u0, float v0, float u1, float v1) {
        this.u0 = u0;
        this.v0 = v0;
        this.u1 = u1;
        this.v1 = v1;
        return this;
    }

    public RenderBuilder setFirstUV(TextureAtlasSprite sprite) {
        return setFirstUV(sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1());
    }

    public RenderBuilder setSecondUV(float u0, float v0, float u1, float v1) {
        this.u2 = u0;
        this.v2 = v0;
        this.u3 = u1;
        this.v3 = v1;
        return this;
    }

    public RenderBuilder setSecondUV(TextureAtlasSprite sprite) {
        return setSecondUV(sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1());
    }

    public RenderBuilder setThirdUV(float u0, float v0, float u1, float v1) {
        this.u4 = u0;
        this.v4 = v0;
        this.u5 = u1;
        this.v5 = v1;
        return this;
    }

    public RenderBuilder setThirdUV(TextureAtlasSprite sprite) {
        return setThirdUV(sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1());
    }

    public RenderBuilder enableSided() {
        return setSided(true);
    }

    public RenderBuilder disableSided() {
        return setSided(false);
    }

    public RenderBuilder setSided(boolean side) {
        return setFirstSide(side).setSecondSide(side);
    }

    public RenderBuilder enableFirstSide() {
        return setFirstSide(true);
    }

    public RenderBuilder disableFirstSSide() {
        return setFirstSide(false);
    }

    public RenderBuilder setFirstSide(boolean side) {
        this.firstSide = side;
        return this;
    }

    public RenderBuilder enableSecondSide() {
        return setSecondSide(true);
    }

    public RenderBuilder disableSecondSSide() {
        return setSecondSide(false);
    }

    public RenderBuilder setSecondSide(boolean side) {
        this.secondSide = side;
        return this;
    }

    public RenderBuilder setSided(boolean firstSide, boolean secondSide) {
        return setFirstSide(firstSide).setSecondSide(secondSide);
    }

    public RenderBuilder endBatch() {
        ((MultiBufferSource.BufferSource) getBufferSource()).endBatch();
        return this;
    }

    public RenderBuilder renderQuad(PoseStack stack, float size) {
        return renderQuad(stack, size, size);
    }

    public RenderBuilder renderQuad(PoseStack stack, float width, float height) {
        Vector3f[] positions = new Vector3f[]{new Vector3f(0, 1, 0), new Vector3f(1, 1, 0), new Vector3f(1, 0, 0), new Vector3f(0, 0, 0)};
        return renderQuad(stack, positions, width, height);
    }

    public RenderBuilder renderCenteredQuad(PoseStack stack, float size) {
        return renderCenteredQuad(stack, size, size);
    }

    public RenderBuilder renderCenteredQuad(PoseStack stack, float width, float height) {
        Vector3f[] positions = new Vector3f[]{new Vector3f(-1, 1, 0), new Vector3f(1, 1, 0), new Vector3f(1, -1, 0), new Vector3f(-1, -1, 0)};
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
        if (firstSide) {
            supplier.placeVertex(getVertexConsumer(), last, this, positions[0].x(), positions[0].y(), positions[0].z(), r1, g1, b1, a1, u0, v1, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[1].x(), positions[1].y(), positions[1].z(), r1, g1, b1, a1, u1, v1, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[2].x(), positions[2].y(), positions[2].z(), r1, g1, b1, a1, u1, v0, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[3].x(), positions[3].y(), positions[3].z(), r1, g1, b1, a1, u0, v0, l1);
        }

        if (secondSide) {
            supplier.placeVertex(getVertexConsumer(), last, this, positions[3].x(), positions[3].y(), positions[3].z(), r1, g1, b1, a1, u1, v0, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[2].x(), positions[2].y(), positions[2].z(), r1, g1, b1, a1, u0, v0, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[1].x(), positions[1].y(), positions[1].z(), r1, g1, b1, a1, u0, v1, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[0].x(), positions[0].y(), positions[0].z(), r1, g1, b1, a1, u1, v1, l1);
        }

        return this;
    }

    public RenderBuilder renderCube(PoseStack stack, float size) {
        return renderCube(stack, size, size, size);
    }

    public RenderBuilder renderCube(PoseStack stack, float width, float height, float length) {
        Vector3f[] positions = new Vector3f[]{
                new Vector3f(0, 1, 0), new Vector3f(0, 1, 1), new Vector3f(1, 1, 1), new Vector3f(1, 1, 0),
                new Vector3f(1, 0, 0), new Vector3f(1, 0, 1), new Vector3f(0, 0, 1), new Vector3f(0, 0, 0)};
        for (Vector3f position : positions) {
            position.mul(width, height, length);
        }
        return renderCube(stack.last().pose(), positions);
    }

    public RenderBuilder renderCenteredCube(PoseStack stack, float size) {
        return renderCenteredCube(stack, size, size, size);
    }

    public RenderBuilder renderCenteredCube(PoseStack stack, float width, float height, float length) {
        Vector3f[] positions = new Vector3f[]{
                new Vector3f(-1, 1, -1), new Vector3f(-1, 1, 1), new Vector3f(1, 1, 1), new Vector3f(1, 1, -1),
                new Vector3f(1, -1, -1), new Vector3f(1, -1, 1), new Vector3f(-1, -1, 1), new Vector3f(-1, -1, -1)};
        for (Vector3f position : positions) {
            position.mul(width, height, length);
        }
        return renderCube(stack.last().pose(), positions);
    }

    public RenderBuilder renderCube(Matrix4f last, Vector3f[] positions) {
        if (firstSide) {
            supplier.placeVertex(getVertexConsumer(), last, this, positions[0].x(), positions[0].y(), positions[0].z(), r1, g1, b1, a1, u0, v0, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[1].x(), positions[1].y(), positions[1].z(), r1, g1, b1, a1, u1, v0, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[2].x(), positions[2].y(), positions[2].z(), r1, g1, b1, a1, u1, v1, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[3].x(), positions[3].y(), positions[3].z(), r1, g1, b1, a1, u0, v1, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[4].x(), positions[4].y(), positions[4].z(), r1, g1, b1, a1, u0, v0, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[5].x(), positions[5].y(), positions[5].z(), r1, g1, b1, a1, u1, v0, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[6].x(), positions[6].y(), positions[6].z(), r1, g1, b1, a1, u1, v1, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[7].x(), positions[7].y(), positions[7].z(), r1, g1, b1, a1, u0, v1, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[6].x(), positions[6].y(), positions[6].z(), r1, g1, b1, a1, u2, v2, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[5].x(), positions[5].y(), positions[5].z(), r1, g1, b1, a1, u3, v2, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[2].x(), positions[2].y(), positions[2].z(), r1, g1, b1, a1, u3, v3, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[1].x(), positions[1].y(), positions[1].z(), r1, g1, b1, a1, u2, v3, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[0].x(), positions[0].y(), positions[0].z(), r1, g1, b1, a1, u2, v2, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[3].x(), positions[3].y(), positions[3].z(), r1, g1, b1, a1, u3, v2, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[4].x(), positions[4].y(), positions[4].z(), r1, g1, b1, a1, u3, v3, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[7].x(), positions[7].y(), positions[7].z(), r1, g1, b1, a1, u2, v3, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[3].x(), positions[3].y(), positions[3].z(), r1, g1, b1, a1, u4, v4, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[2].x(), positions[2].y(), positions[2].z(), r1, g1, b1, a1, u5, v4, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[5].x(), positions[5].y(), positions[5].z(), r1, g1, b1, a1, u5, v5, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[4].x(), positions[4].y(), positions[4].z(), r1, g1, b1, a1, u4, v5, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[7].x(), positions[7].y(), positions[7].z(), r1, g1, b1, a1, u4, v4, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[6].x(), positions[6].y(), positions[6].z(), r1, g1, b1, a1, u5, v4, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[1].x(), positions[1].y(), positions[1].z(), r1, g1, b1, a1, u5, v5, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[0].x(), positions[0].y(), positions[0].z(), r1, g1, b1, a1, u4, v5, l1);
        }

        if (secondSide) {
            supplier.placeVertex(getVertexConsumer(), last, this, positions[3].x(), positions[3].y(), positions[3].z(), r1, g1, b1, a1, u0, v1, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[2].x(), positions[2].y(), positions[2].z(), r1, g1, b1, a1, u1, v1, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[1].x(), positions[1].y(), positions[1].z(), r1, g1, b1, a1, u1, v0, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[0].x(), positions[0].y(), positions[0].z(), r1, g1, b1, a1, u0, v0, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[7].x(), positions[7].y(), positions[7].z(), r1, g1, b1, a1, u0, v1, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[6].x(), positions[6].y(), positions[6].z(), r1, g1, b1, a1, u1, v1, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[5].x(), positions[5].y(), positions[5].z(), r1, g1, b1, a1, u1, v0, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[4].x(), positions[4].y(), positions[4].z(), r1, g1, b1, a1, u0, v0, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[1].x(), positions[1].y(), positions[1].z(), r1, g1, b1, a1, u2, v3, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[2].x(), positions[2].y(), positions[2].z(), r1, g1, b1, a1, u3, v3, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[5].x(), positions[5].y(), positions[5].z(), r1, g1, b1, a1, u3, v2, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[6].x(), positions[6].y(), positions[6].z(), r1, g1, b1, a1, u2, v2, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[7].x(), positions[7].y(), positions[7].z(), r1, g1, b1, a1, u2, v3, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[4].x(), positions[4].y(), positions[4].z(), r1, g1, b1, a1, u3, v3, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[3].x(), positions[3].y(), positions[3].z(), r1, g1, b1, a1, u3, v2, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[0].x(), positions[0].y(), positions[0].z(), r1, g1, b1, a1, u2, v2, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[4].x(), positions[4].y(), positions[4].z(), r1, g1, b1, a1, u4, v5, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[5].x(), positions[5].y(), positions[5].z(), r1, g1, b1, a1, u5, v5, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[2].x(), positions[2].y(), positions[2].z(), r1, g1, b1, a1, u5, v4, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[3].x(), positions[3].y(), positions[3].z(), r1, g1, b1, a1, u4, v4, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[0].x(), positions[0].y(), positions[0].z(), r1, g1, b1, a1, u4, v5, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[1].x(), positions[1].y(), positions[1].z(), r1, g1, b1, a1, u5, v5, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[6].x(), positions[6].y(), positions[6].z(), r1, g1, b1, a1, u5, v4, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[7].x(), positions[7].y(), positions[7].z(), r1, g1, b1, a1, u4, v4, l1);
        }

        return this;
    }

    public RenderBuilder renderRay(PoseStack stack, float width, float height) {
        return renderRay(stack, width, height, 1);
    }

    public RenderBuilder renderRay(PoseStack stack, float width, float height, float endOffset) {
        Vector3f[] positions = new Vector3f[]{
                new Vector3f(-1, 1, -1), new Vector3f(-1, 1, 1), new Vector3f(1, 1, 1), new Vector3f(1, 1, -1),
                new Vector3f(1, -1, -1), new Vector3f(1, -1, 1), new Vector3f(-1, -1, 1), new Vector3f(-1, -1, -1)};
        for (int i = 0; i < 4; ++i) {
            positions[i].mul(width * endOffset, height, width * endOffset);
        }
        for (int i = 0; i < 4; ++i) {
            positions[i + 4].mul(width);
        }
        return renderRay(stack.last().pose(), positions);
    }

    public RenderBuilder renderRay(Matrix4f last, Vector3f[] positions) {
        if (firstSide) {
            supplier.placeVertex(getVertexConsumer(), last, this, positions[0].x(), positions[0].y(), positions[0].z(), r2, g2, b2, a2, u0, v0, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[1].x(), positions[1].y(), positions[1].z(), r2, g2, b2, a2, u1, v0, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[2].x(), positions[2].y(), positions[2].z(), r2, g2, b2, a2, u1, v1, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[3].x(), positions[3].y(), positions[3].z(), r2, g2, b2, a2, u0, v1, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[4].x(), positions[4].y(), positions[4].z(), r1, g1, b1, a1, u0, v0, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[5].x(), positions[5].y(), positions[5].z(), r1, g1, b1, a1, u1, v0, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[6].x(), positions[6].y(), positions[6].z(), r1, g1, b1, a1, u1, v1, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[7].x(), positions[7].y(), positions[7].z(), r1, g1, b1, a1, u0, v1, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[6].x(), positions[6].y(), positions[6].z(), r1, g1, b1, a1, u2, v2, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[5].x(), positions[5].y(), positions[5].z(), r1, g1, b1, a1, u3, v2, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[2].x(), positions[2].y(), positions[2].z(), r2, g2, b2, a2, u3, v3, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[1].x(), positions[1].y(), positions[1].z(), r2, g2, b2, a2, u2, v3, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[0].x(), positions[0].y(), positions[0].z(), r2, g2, b2, a2, u2, v2, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[3].x(), positions[3].y(), positions[3].z(), r2, g2, b2, a2, u3, v2, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[4].x(), positions[4].y(), positions[4].z(), r1, g1, b1, a1, u3, v3, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[7].x(), positions[7].y(), positions[7].z(), r1, g1, b1, a1, u2, v3, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[3].x(), positions[3].y(), positions[3].z(), r2, g2, b2, a2, u4, v4, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[2].x(), positions[2].y(), positions[2].z(), r2, g2, b2, a2, u5, v4, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[5].x(), positions[5].y(), positions[5].z(), r1, g1, b1, a1, u5, v5, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[4].x(), positions[4].y(), positions[4].z(), r1, g1, b1, a1, u4, v5, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[7].x(), positions[7].y(), positions[7].z(), r1, g1, b1, a1, u4, v4, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[6].x(), positions[6].y(), positions[6].z(), r1, g1, b1, a1, u5, v4, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[1].x(), positions[1].y(), positions[1].z(), r2, g2, b2, a2, u5, v5, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[0].x(), positions[0].y(), positions[0].z(), r2, g2, b2, a2, u4, v5, l2);
        }

        if (secondSide) {
            supplier.placeVertex(getVertexConsumer(), last, this, positions[3].x(), positions[3].y(), positions[3].z(), r2, g2, b2, a2, u0, v1, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[2].x(), positions[2].y(), positions[2].z(), r2, g2, b2, a2, u1, v1, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[1].x(), positions[1].y(), positions[1].z(), r2, g2, b2, a2, u1, v0, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[0].x(), positions[0].y(), positions[0].z(), r2, g2, b2, a2, u0, v0, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[7].x(), positions[7].y(), positions[7].z(), r1, g1, b1, a1, u0, v1, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[6].x(), positions[6].y(), positions[6].z(), r1, g1, b1, a1, u1, v1, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[5].x(), positions[5].y(), positions[5].z(), r1, g1, b1, a1, u1, v0, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[4].x(), positions[4].y(), positions[4].z(), r1, g1, b1, a1, u0, v0, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[1].x(), positions[1].y(), positions[1].z(), r2, g2, b2, a2, u2, v3, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[2].x(), positions[2].y(), positions[2].z(), r2, g2, b2, a2, u3, v3, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[5].x(), positions[5].y(), positions[5].z(), r1, g1, b1, a1, u3, v2, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[6].x(), positions[6].y(), positions[6].z(), r1, g1, b1, a1, u2, v2, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[7].x(), positions[7].y(), positions[7].z(), r1, g1, b1, a1, u2, v3, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[4].x(), positions[4].y(), positions[4].z(), r1, g1, b1, a1, u3, v3, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[3].x(), positions[3].y(), positions[3].z(), r2, g2, b2, a2, u3, v2, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[0].x(), positions[0].y(), positions[0].z(), r2, g2, b2, a2, u2, v2, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[4].x(), positions[4].y(), positions[4].z(), r1, g1, b1, a1, u4, v5, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[5].x(), positions[5].y(), positions[5].z(), r1, g1, b1, a1, u5, v5, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[2].x(), positions[2].y(), positions[2].z(), r2, g2, b2, a2, u5, v4, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[3].x(), positions[3].y(), positions[3].z(), r2, g2, b2, a2, u4, v4, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[0].x(), positions[0].y(), positions[0].z(), r2, g2, b2, a2, u4, v5, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[1].x(), positions[1].y(), positions[1].z(), r2, g2, b2, a2, u5, v5, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[6].x(), positions[6].y(), positions[6].z(), r1, g1, b1, a1, u5, v4, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[7].x(), positions[7].y(), positions[7].z(), r1, g1, b1, a1, u4, v4, l1);
        }

        return this;
    }

    public RenderBuilder renderBeam(PoseStack stack, float width, float height) {
        return renderBeam(stack, width, height, 1);
    }

    public RenderBuilder renderBeam(PoseStack stack, float width, float height, float endOffset) {
        Vector3f[] positions = new Vector3f[]{
                new Vector3f(-1, 1, -1), new Vector3f(-1, 1, 1), new Vector3f(1, 1, 1), new Vector3f(1, 1, -1),
                new Vector3f(1, 0, -1), new Vector3f(1, 0, 1), new Vector3f(-1, 0, 1), new Vector3f(-1, 0, -1)};
        for (int i = 0; i < 4; ++i) {
            positions[i].mul(width * endOffset, height, width * endOffset);
        }
        for (int i = 0; i < 4; ++i) {
            positions[i + 4].mul(width);
        }
        return renderBeam(stack.last().pose(), positions);
    }

    public RenderBuilder renderBeam(Matrix4f last, Vector3f[] positions) {
        if (firstSide) {
            supplier.placeVertex(getVertexConsumer(), last, this, positions[6].x(), positions[6].y(), positions[6].z(), r1, g1, b1, a1, u2, v2, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[5].x(), positions[5].y(), positions[5].z(), r1, g1, b1, a1, u3, v2, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[2].x(), positions[2].y(), positions[2].z(), r2, g2, b2, a2, u3, v3, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[1].x(), positions[1].y(), positions[1].z(), r2, g2, b2, a2, u2, v3, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[0].x(), positions[0].y(), positions[0].z(), r2, g2, b2, a2, u2, v2, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[3].x(), positions[3].y(), positions[3].z(), r2, g2, b2, a2, u3, v2, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[4].x(), positions[4].y(), positions[4].z(), r1, g1, b1, a1, u3, v3, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[7].x(), positions[7].y(), positions[7].z(), r1, g1, b1, a1, u2, v3, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[3].x(), positions[3].y(), positions[3].z(), r2, g2, b2, a2, u4, v4, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[2].x(), positions[2].y(), positions[2].z(), r2, g2, b2, a2, u5, v4, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[5].x(), positions[5].y(), positions[5].z(), r1, g1, b1, a1, u5, v5, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[4].x(), positions[4].y(), positions[4].z(), r1, g1, b1, a1, u4, v5, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[7].x(), positions[7].y(), positions[7].z(), r1, g1, b1, a1, u4, v4, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[6].x(), positions[6].y(), positions[6].z(), r1, g1, b1, a1, u5, v4, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[1].x(), positions[1].y(), positions[1].z(), r2, g2, b2, a2, u5, v5, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[0].x(), positions[0].y(), positions[0].z(), r2, g2, b2, a2, u4, v5, l2);
        }

        if (secondSide) {
            supplier.placeVertex(getVertexConsumer(), last, this, positions[1].x(), positions[1].y(), positions[1].z(), r2, g2, b2, a2, u2, v3, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[2].x(), positions[2].y(), positions[2].z(), r2, g2, b2, a2, u3, v3, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[5].x(), positions[5].y(), positions[5].z(), r1, g1, b1, a1, u3, v2, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[6].x(), positions[6].y(), positions[6].z(), r1, g1, b1, a1, u2, v2, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[7].x(), positions[7].y(), positions[7].z(), r1, g1, b1, a1, u2, v3, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[4].x(), positions[4].y(), positions[4].z(), r1, g1, b1, a1, u3, v3, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[3].x(), positions[3].y(), positions[3].z(), r2, g2, b2, a2, u3, v2, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[0].x(), positions[0].y(), positions[0].z(), r2, g2, b2, a2, u2, v2, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[4].x(), positions[4].y(), positions[4].z(), r1, g1, b1, a1, u4, v5, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[5].x(), positions[5].y(), positions[5].z(), r1, g1, b1, a1, u5, v5, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[2].x(), positions[2].y(), positions[2].z(), r2, g2, b2, a2, u5, v4, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[3].x(), positions[3].y(), positions[3].z(), r2, g2, b2, a2, u4, v4, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[0].x(), positions[0].y(), positions[0].z(), r2, g2, b2, a2, u4, v5, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[1].x(), positions[1].y(), positions[1].z(), r2, g2, b2, a2, u5, v5, l2);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[6].x(), positions[6].y(), positions[6].z(), r1, g1, b1, a1, u5, v4, l1);
            supplier.placeVertex(getVertexConsumer(), last, this, positions[7].x(), positions[7].y(), positions[7].z(), r1, g1, b1, a1, u4, v4, l1);
        }

        return this;
    }

    public RenderBuilder renderWavyQuad(PoseStack stack, float size, float strength, float time) {
        return renderWavyQuad(stack, size, size, strength, time);
    }

    public RenderBuilder renderWavyQuad(PoseStack stack, float width, float height, float strength, float time) {
        Vector3f[] positions = new Vector3f[]{new Vector3f(-1, 1, 0), new Vector3f(1, 1, 0), new Vector3f(1, -1, 0), new Vector3f(-1, -1, 0)};
        RenderUtil.applyWobble(positions, strength, time);
        return renderQuad(stack, positions, width, height);
    }

    public RenderBuilder renderWavyQuad(PoseStack stack, Vector3f[] positions, float width, float height, float strength, float time) {
        RenderUtil.applyWobble(positions, strength, time);
        return renderQuad(stack, positions, width, height);
    }

    public RenderBuilder renderWavyCube(PoseStack stack, float size, float strength, float time) {
        return renderWavyCube(stack, size, size, size, strength, time);
    }

    public RenderBuilder renderWavyCube(PoseStack stack, float width, float height, float length, float strength, float time) {
        Vector3f[] positions = new Vector3f[]{
                new Vector3f(-1, 1, -1), new Vector3f(-1, 1, 1), new Vector3f(1, 1, 1), new Vector3f(1, 1, -1),
                new Vector3f(1, -1, -1), new Vector3f(1, -1, 1), new Vector3f(-1, -1, 1), new Vector3f(-1, -1, -1)};
        for (Vector3f position : positions) {
            position.mul(width, height, length);
        }
        RenderUtil.applyWobble(positions, strength, time);
        return renderCube(stack.last().pose(), positions);
    }

    public RenderBuilder renderBeam(@Nullable Matrix4f last, Vec3 start, Vec3 end, float width) {
        Minecraft minecraft = Minecraft.getInstance();
        Vec3 cameraPosition = minecraft.getBlockEntityRenderDispatcher().camera.getPosition();
        return renderBeam(last, start, end, width, width, cameraPosition);
    }

    public RenderBuilder renderBeam(@Nullable Matrix4f last, Vec3 start, Vec3 end, float width1, float width2) {
        Minecraft minecraft = Minecraft.getInstance();
        Vec3 cameraPosition = minecraft.getBlockEntityRenderDispatcher().camera.getPosition();
        return renderBeam(last, start, end, width1, width2, cameraPosition);
    }

    public RenderBuilder renderBeam(@Nullable Matrix4f last, Vec3 start, Vec3 end, float width, Vec3 cameraPosition) {
        return renderBeam(last, start, end, width, width, cameraPosition);
    }

    public RenderBuilder renderBeam(@Nullable Matrix4f last, Vec3 start, Vec3 end, float width1, float width2, Vec3 cameraPosition) {
        Vec3 delta = end.subtract(start);
        Vec3 normalStart = start.subtract(cameraPosition).cross(delta).normalize().multiply(width1 / 2f, width1 / 2f, width1 / 2f);
        Vec3 normalEnd = start.subtract(cameraPosition).cross(delta).normalize().multiply(width2 / 2f, width2 / 2f, width2 / 2f);

        Vec3[] positions = new Vec3[]{start.subtract(normalStart), start.add(normalStart), end.add(normalEnd), end.subtract(normalEnd)};

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
            points[i] = new TrailRenderPoint(current, RenderUtil.perpendicularTrailPoints(previous, next, width));
        }
        points[0] = new TrailRenderPoint(positions.get(0), RenderUtil.perpendicularTrailPoints(positions.get(0), positions.get(1), widthFunc.apply(0f)));
        points[count] = new TrailRenderPoint(positions.get(count), RenderUtil.perpendicularTrailPoints(positions.get(count-1), positions.get(count), widthFunc.apply(1f)));
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
            points[i] = new TrailRenderPoint(current, RenderUtil.perpendicularTrailPoints(previous, next, width));
        }
        points[0] = new TrailRenderPoint(positions.get(0), RenderUtil.perpendicularTrailPoints(positions.get(0), positions.get(1), widthFunc.apply(0f)));
        points[count] = new TrailRenderPoint(positions.get(count), RenderUtil.perpendicularTrailPoints(positions.get(count-1), positions.get(count), widthFunc.apply(1f)));
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
            float r = Mth.lerp(i * increment, r1, r2);
            float g = Mth.lerp(i * increment, g1, g2);
            float b = Mth.lerp(i * increment, b1, b2);
            float a = Mth.lerp(i * increment, a1, a2);
            int l = (int) Mth.lerp(i * increment, l1, l2);
            points[i].renderMid(getVertexConsumer(), this, u0, current, u1, current, r, g, b, a, l);
        }
        vfxOperator.accept(1f);
        points[count].renderEnd(getVertexConsumer(), this, u0, Mth.lerp((count) * increment, v0, v1), u1, v1, r2, g2, b2, a2, l2);
        return this;
    }

    public RenderBuilder renderDragon(PoseStack stack, float radius, float partialTicks, float randomF) {
        return renderDragon(stack, 0, 0, 0, radius, partialTicks, randomF);
    }

    public RenderBuilder renderDragon(PoseStack stack, double x, double y, double z, float radius, float partialTicks, float randomF) {
        float f5 = 0.5f;
        float f7 = Math.min(f5 > 0.8F ? (f5 - 0.8F) / 0.2F : 0.0F, 1.0F);
        Random random = new Random((long) (432L + randomF));
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

    public RenderBuilder renderSphere(PoseStack stack, float radius, int longs, int lats, float endU) {
        Matrix4f last = stack.last().pose();
        float startU = 0;
        float startV = 0;
        float endV = Mth.PI;
        float stepU = (endU - startU) / longs;
        float stepV = (endV - startV) / lats;
        for (int i = 0; i < longs; ++i) {
            for (int j = 0; j < lats; ++j) {
                float u = i * stepU + startU;
                float v = j * stepV + startV;
                float un = (i + 1 == longs) ? endU : (i + 1) * stepU + startU;
                float vn = (j + 1 == lats) ? endV : (j + 1) * stepV + startV;
                Vector3f p0 = RenderUtil.parametricSphere(u, v, radius);
                Vector3f p1 = RenderUtil.parametricSphere(u, vn, radius);
                Vector3f p2 = RenderUtil.parametricSphere(un, v, radius);
                Vector3f p3 = RenderUtil.parametricSphere(un, vn, radius);

                float textureU = (u / endU * radius) * u0;
                float textureV = (v / endV * radius) * v0;
                float textureUN = (un / endU * radius) * u1;
                float textureVN = (vn / endV * radius) * v1;

                supplier.placeVertex(getVertexConsumer(), last, this, p0.x(), p0.y(), p0.z(), r1, g1, b1, r1, textureU, textureV, l1);
                supplier.placeVertex(getVertexConsumer(), last, this, p0.x(), p0.y(), p0.z(), r1, g1, b1, r1, textureU, textureV, l1);
                supplier.placeVertex(getVertexConsumer(), last, this, p2.x(), p2.y(), p2.z(), r1, g1, b1, r1, textureUN, textureV, l1);
                supplier.placeVertex(getVertexConsumer(), last, this, p1.x(), p1.y(), p1.z(), r1, g1, b1, r1, textureU, textureVN, l1);

                supplier.placeVertex(getVertexConsumer(), last, this, p3.x(), p3.y(), p3.z(), r1, g1, b1, r1, textureUN, textureVN, l1);
                supplier.placeVertex(getVertexConsumer(), last, this, p3.x(), p3.y(), p3.z(), r1, g1, b1, r1, textureUN, textureVN, l1);
                supplier.placeVertex(getVertexConsumer(), last, this, p1.x(), p1.y(), p1.z(), r1, g1, b1, r1, textureU, textureVN, l1);
                supplier.placeVertex(getVertexConsumer(), last, this, p2.x(), p2.y(), p2.z(), r1, g1, b1, r1, textureUN, textureV, l1);
            }
        }
        return this;
    }

    public RenderBuilder renderSphere(PoseStack stack, float radius, int longs, int lats) {
        return renderSphere(stack, radius, longs, lats, Mth.PI * 2);
    }

    public RenderBuilder renderSemiSphere(PoseStack stack, float radius, int longs, int lats) {
        return renderSphere(stack, radius, longs, lats, Mth.PI);
    }

    public interface VertexConsumerActor {
        void placeVertex(VertexConsumer consumer, Matrix4f last, RenderBuilder builder, float x, float y, float z, float r, float g, float b, float a, float u, float v, int l);
    }
}
