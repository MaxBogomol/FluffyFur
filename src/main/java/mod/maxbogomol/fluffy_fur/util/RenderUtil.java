package mod.maxbogomol.fluffy_fur.util;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.render.LevelRenderHandler;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.client.render.item.CustomItemRenderer;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurShaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.awt.*;

import static net.minecraft.util.Mth.sqrt;

public class RenderUtil {

    public static CustomItemRenderer customItemRenderer;
    public static float blitOffset = 0;
    private static final float ROOT_3 = (float)(Math.sqrt(3.0D) / 2.0D);

    public static int FULL_BRIGHT = 15728880;

    public static CustomItemRenderer getCustomItemRenderer() {
        Minecraft minecraft = Minecraft.getInstance();
        if (customItemRenderer == null) customItemRenderer = new CustomItemRenderer(minecraft, minecraft.getTextureManager(), minecraft.getModelManager(), minecraft.getItemColors(), minecraft.getItemRenderer().getBlockEntityRenderer());
        return customItemRenderer;
    }

    public static void renderItemModelInGui(ItemStack stack, float x, float y, float xSize, float ySize, float zSize) {
        renderItemModelInGui(stack, x, y, xSize, ySize, zSize, 0, 0, 0);
    }

    public static void renderItemModelInGui(ItemStack stack, float x, float y, float xSize, float ySize, float zSize, float xRot, float yRot, float zRot) {
        Minecraft minecraft = Minecraft.getInstance();
        BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getModel(stack, minecraft.level, minecraft.player, 0);
        CustomItemRenderer customItemRenderer = getCustomItemRenderer();

        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.translate(x, y, 100.0F + blitOffset);
        posestack.translate((double) xSize / 2, (double) ySize / 2, 0.0D);
        posestack.scale(1.0F, -1.0F, 1.0F);
        posestack.scale(xSize, ySize, zSize);
        posestack.mulPose(Axis.XP.rotationDegrees(xRot));
        posestack.mulPose(Axis.YP.rotationDegrees(yRot));
        posestack.mulPose(Axis.ZP.rotationDegrees(zRot));
        RenderSystem.applyModelViewMatrix();
        PoseStack posestack1 = new PoseStack();
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        boolean flag = !bakedmodel.usesBlockLight();
        if (flag) Lighting.setupForFlatItems();

        customItemRenderer.render(stack, ItemDisplayContext.GUI, false, posestack1, multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);

        RenderSystem.disableDepthTest();
        multibuffersource$buffersource.endBatch();
        RenderSystem.enableDepthTest();
        if (flag) Lighting.setupFor3DItems();
        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
    }

    public static void renderFloatingItemModelIntoGUI(GuiGraphics gui, ItemStack stack, int x, int y, float ticks, float ticksUp) {
        Minecraft minecraft = Minecraft.getInstance();
        BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getModel(stack, minecraft.level, minecraft.player, 0);
        CustomItemRenderer customItemRenderer = getCustomItemRenderer();

        float old = bakedmodel.getTransforms().gui.rotation.y;

        PoseStack posestack = gui.pose();

        posestack.pushPose();
        posestack.translate((float)(x + 8), (float)(y + 8), 100 + blitOffset);
        posestack.mulPoseMatrix((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));
        posestack.scale(16.0F, 16.0F, 16.0F);
        posestack.translate(0.0D, Math.sin(Math.toRadians(ticksUp)) * 0.03125F, 0.0D);
        if (bakedmodel.usesBlockLight()) {
            bakedmodel.getTransforms().gui.rotation.y = ticks;
        } else {
            posestack.mulPose(Axis.YP.rotationDegrees(ticks));
        }
        boolean flag = !bakedmodel.usesBlockLight();
        if (flag) Lighting.setupForFlatItems();

        customItemRenderer.renderItem(stack, ItemDisplayContext.GUI, false, posestack, Minecraft.getInstance().renderBuffers().bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);

        RenderSystem.disableDepthTest();
        Minecraft.getInstance().renderBuffers().bufferSource().endBatch();
        RenderSystem.enableDepthTest();
        if (flag) Lighting.setupFor3DItems();
        posestack.popPose();
        RenderSystem.applyModelViewMatrix();

        bakedmodel.getTransforms().gui.rotation.y = old;
    }

    public static void renderCustomModel(ModelResourceLocation model, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(model);
        Minecraft.getInstance().getItemRenderer().render(new ItemStack(Items.DIRT), displayContext, leftHand, poseStack, buffer, combinedLight, combinedOverlay, bakedmodel);
    }

    public static void ray(PoseStack mStack, MultiBufferSource buf, float width, float height, float endOffset, float r, float g, float b, float a) {
        ray(mStack, buf, width, height, endOffset, r, g, b, a, r, g, b, a);
    }

    public static void raySided(PoseStack mStack, MultiBufferSource buf, float width, float height, float endOffset, float r, float g, float b, float a) {
        raySided(mStack, buf, width, height, endOffset, r, g, b, a, r, g, b, a);
    }

    public static void raySided(PoseStack mStack, MultiBufferSource buf, float width, float height, float endOffset, float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2) {
        ray(mStack, buf, width, height, endOffset, r1, g1, b1, a1, r2, g2, b2, a2);
        mStack.pushPose();
        mStack.scale(-1, -1, -1);
        mStack.mulPose(Axis.ZP.rotationDegrees(180f));
        ray(mStack, buf, width, height, endOffset, r1, g1, b1, a1, r2, g2, b2, a2);
        mStack.popPose();
    }

    public static void ray(PoseStack mStack, MultiBufferSource buf, float width, float height, float endOffset, float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2) {
        VertexConsumer builder = buf.getBuffer(FluffyFurRenderTypes.GLOWING);

        Matrix4f mat = mStack.last().pose();

        builder.vertex(mat, -width, width, -width).color(r1, g1, b1, a1).endVertex();
        builder.vertex(mat, height, width * endOffset, -width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, height, -width * endOffset, -width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, -width, -width, -width).color(r1, g1, b1, a1).endVertex();

        builder.vertex(mat, height, width * endOffset, width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, -width, width, width).color(r1, g1, b1, a1).endVertex();
        builder.vertex(mat, -width, -width, width).color(r1, g1, b1, a1).endVertex();
        builder.vertex(mat, height, -width * endOffset, width * endOffset).color(r2, g2, b2, a2).endVertex();

        builder.vertex(mat, height, -width * endOffset, width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, height, -width * endOffset, -width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, height, width * endOffset, -width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, height, width * endOffset, width * endOffset).color(r2, g2, b2, a2).endVertex();

        builder.vertex(mat, -width, -width, -width).color(r1, g1, b1, a1).endVertex();
        builder.vertex(mat, -width, -width, width).color(r1, g1, b1, a1).endVertex();
        builder.vertex(mat, -width, width, width).color(r1, g1, b1, a1).endVertex();
        builder.vertex(mat, -width, width, -width).color(r1, g1, b1, a1).endVertex();

        builder.vertex(mat, -width, width, width).color(r1, g1, b1, a1).endVertex();
        builder.vertex(mat, height, width * endOffset, width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, height, width * endOffset, -width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, -width, width, -width).color(r1, g1, b1, a1).endVertex();

        builder.vertex(mat, -width, -width, -width).color(r1, g1, b1, a1).endVertex();
        builder.vertex(mat, height, -width * endOffset, -width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, height, -width * endOffset, width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, -width, -width, width).color(r1, g1, b1, a1).endVertex();
    }

    public static void beam(PoseStack mStack, MultiBufferSource buf, float width, float height, float endOffset, float r, float g, float b, float a) {
        beam(mStack, buf, width, height, endOffset, r, g, b, a, r, g, b, a);
    }

    public static void beamSided(PoseStack mStack, MultiBufferSource buf, float width, float height, float endOffset, float r, float g, float b, float a) {
        beamSided(mStack, buf, width, height, endOffset, r, g, b, a, r, g, b, a);
    }

    public static void beamSided(PoseStack mStack, MultiBufferSource buf, float width, float height, float endOffset, float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2) {
        beam(mStack, buf, width, height, endOffset, r1, g1, b1, a1, r2, g2, b2, a2);
        mStack.pushPose();
        mStack.scale(-1, -1, -1);
        mStack.mulPose(Axis.ZP.rotationDegrees(180f));
        beam(mStack, buf, width, height, endOffset, r1, g1, b1, a1, r2, g2, b2, a2);
        mStack.popPose();
    }

    public static void beam(PoseStack mStack, MultiBufferSource buf, float width, float height, float endOffset, float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2) {
        VertexConsumer builder = buf.getBuffer(FluffyFurRenderTypes.GLOWING);

        Matrix4f mat = mStack.last().pose();

        builder.vertex(mat, 0, width, -width).color(r1, g1, b1, a1).endVertex();
        builder.vertex(mat, height, width * endOffset, -width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, height, -width * endOffset, -width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, 0, -width, -width).color(r1, g1, b1, a1).endVertex();

        builder.vertex(mat, height, width * endOffset, width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, 0, width, width).color(r1, g1, b1, a1).endVertex();
        builder.vertex(mat, 0, -width, width).color(r1, g1, b1, a1).endVertex();
        builder.vertex(mat, height, -width * endOffset, width * endOffset).color(r2, g2, b2, a2).endVertex();

        builder.vertex(mat, 0, width, width).color(r1, g1, b1, a1).endVertex();
        builder.vertex(mat, height, width * endOffset, width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, height, width * endOffset, -width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, 0, width, -width).color(r1, g1, b1, a1).endVertex();

        builder.vertex(mat, 0, -width, -width).color(r1, g1, b1, a1).endVertex();
        builder.vertex(mat, height, -width * endOffset, -width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, height, -width * endOffset, width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, 0, -width, width).color(r1, g1, b1, a1).endVertex();
    }

    public static void litQuad(PoseStack mStack, MultiBufferSource buf, float x, float y, float width, float height, float r, float g, float b, float a) {
        VertexConsumer builder = buf.getBuffer(FluffyFurRenderTypes.GLOWING);

        Matrix4f mat = mStack.last().pose();
        builder.vertex(mat, x, y + height, 0).color(r, g, b, a).endVertex();
        builder.vertex(mat, x + width, y + height, 0).color(r, g, b, a).endVertex();
        builder.vertex(mat, x + width, y, 0).color(r, g, b, a).endVertex();
        builder.vertex(mat, x, y, 0).color(r, g, b, a).endVertex();
    }

    public static void litQuadCube(PoseStack mStack, MultiBufferSource buf, float x1, float y1, float z1, float x2, float y2, float z2, float r, float g, float b, float a) {
        VertexConsumer builder = buf.getBuffer(FluffyFurRenderTypes.GLOWING);

        Matrix4f mat = mStack.last().pose();

        builder.vertex(mat, x1, y1 + y2, z1).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1 + x2, y1 + y2, z1).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1 + x2, y1, z1).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1, y1, z1).color(r, g, b, a).endVertex();

        builder.vertex(mat, x1 + x2, y1 + y2, z1 + z2).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1, y1 + y2, z1 + z2).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1, y1, z1 + z2).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1 + x2, y1, z1 + z2).color(r, g, b, a).endVertex();

        builder.vertex(mat, x1 + x2, y1, z1 + z2).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1 + x2, y1, z1).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1 + x2, y1 + y2, z1).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1 + x2, y1 + y2, z1 + z2).color(r, g, b, a).endVertex();

        builder.vertex(mat, x1, y1, z1).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1, y1, z1 + z2).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1, y1 + y2, z1 + z2).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1, y1 + y2, z1).color(r, g, b, a).endVertex();

        builder.vertex(mat, x1, y1 + y2, z1 + z2).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1 + x2, y1 + y2, z1 + z2).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1 + x2, y1 + y2, z1).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1, y1 + y2, z1).color(r, g, b, a).endVertex();

        builder.vertex(mat, x1, y1, z1).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1 + x2, y1, z1).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1 + x2, y1, z1 + z2).color(r, g, b, a).endVertex();
        builder.vertex(mat, x1, y1, z1 + z2).color(r, g, b, a).endVertex();
    }

    public static void renderConnectLine(BlockPos posFrom, BlockPos posTo, Color color, float partialTicks, PoseStack ms) {
        renderConnectLine(posFrom.getCenter(), posTo.getCenter(), color, partialTicks, ms);
    }

    public static void renderConnectLine(Vec3 from, Vec3 to, Color color, float partialTicks, PoseStack ms) {
        MultiBufferSource bufferDelayed = LevelRenderHandler.getDelayedRender();

        double dX = to.x() - from.x();
        double dY = to.y() - from.y();
        double dZ = to.z() - from.z();

        double yaw = Math.atan2(dZ, dX);
        double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;
        float a = color.getAlpha() / 255f;

        ms.pushPose();
        ms.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(-yaw)));
        ms.mulPose(Axis.ZP.rotationDegrees((float) Math.toDegrees(-pitch) - 90f));
        RenderUtil.ray(ms, bufferDelayed, 0.01f, (float) from.distanceTo(to) + 0.01f, 1f, r, g, b, 0.5f * a);
        ms.popPose();
    }

    public static void renderConnectLineOffset(Vec3 from, Vec3 to, Color color, float partialTicks, PoseStack ms) {
        ms.pushPose();
        ms.translate(from.x(), from.y(), from.z());
        renderConnectLine(from, to, color, partialTicks, ms);
        ms.popPose();
    }

    public static void renderBoxLines(Vec3 size, Color color, float partialTicks, PoseStack ms) {
        renderConnectLineOffset(new Vec3(0, 0, 0), new Vec3(size.x() , 0, 0), color, partialTicks, ms);
        renderConnectLineOffset(new Vec3(size.x(), 0, 0), new Vec3(size.x(), 0, size.z()), color, partialTicks, ms);
        renderConnectLineOffset(new Vec3(size.x(), 0, size.z()), new Vec3(0, 0, size.z()), color, partialTicks, ms);
        renderConnectLineOffset(new Vec3(0, 0, size.z()), new Vec3(0, 0, 0), color, partialTicks, ms);

        renderConnectLineOffset(new Vec3(0, 0, 0), new Vec3(0, size.y(), 0), color, partialTicks, ms);
        renderConnectLineOffset(new Vec3(size.x(), 0, 0), new Vec3(size.x(), size.y(), 0), color, partialTicks, ms);
        renderConnectLineOffset(new Vec3(size.x(), 0, size.z()), new Vec3(size.x(), size.y(), size.z()), color, partialTicks, ms);
        renderConnectLineOffset(new Vec3(0, 0, size.z()), new Vec3(0, size.y(), size.z()), color, partialTicks, ms);

        renderConnectLineOffset(new Vec3(0, size.y(), 0), new Vec3(size.x(), size.y(), 0), color, partialTicks, ms);
        renderConnectLineOffset(new Vec3(size.x(), size.y(), 0), new Vec3(size.x() , size.y(), size.z()), color, partialTicks, ms);
        renderConnectLineOffset(new Vec3(size.x(), size.y(), size.z()), new Vec3(0, size.y(), size.z()), color, partialTicks, ms);
        renderConnectLineOffset(new Vec3(0, size.y(), size.z()), new Vec3(0, size.y(), 0), color, partialTicks, ms);
    }

    public static void renderSideLines(Vec3 size, Color color, float partialTicks, PoseStack ms) {
        renderConnectLineOffset(new Vec3(0, 0, 0), new Vec3(size.x() , 0, 0), color, partialTicks, ms);
        renderConnectLineOffset(new Vec3(size.x(), 0, 0), new Vec3(size.x(), 0, size.z()), color, partialTicks, ms);
        renderConnectLineOffset(new Vec3(size.x(), 0, size.z()), new Vec3(0, 0, size.z()), color, partialTicks, ms);
        renderConnectLineOffset(new Vec3(0, 0, size.z()), new Vec3(0, 0, 0), color, partialTicks, ms);
    }

    public static void renderSide(Direction side, Color color, float partialTicks, PoseStack ms) {
        Vec3 size = new Vec3(1, 1, 1);
        ms.pushPose();
        ms.translate(0.5f, 0.5f, 0.5f);
        ms.mulPose(side.getOpposite().getRotation());
        ms.translate(-size.x() / 2f, -size.y() / 2f, -size.z() / 2f);
        renderSideLines(size, color, partialTicks, ms);
        ms.popPose();
    }

    public static Vector3f parametricSphere(float u, float v, float r) {
        return new Vector3f(Mth.cos(u) * Mth.sin(v) * r, Mth.cos(v) * r, Mth.sin(u) * Mth.sin(v) * r);
    }

    public static TextureAtlasSprite getSprite(ResourceLocation resourceLocation) {
        return Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(resourceLocation);
    }

    public static TextureAtlasSprite getSprite(String modId, String sprite) {
        return getSprite(new ResourceLocation(modId, sprite));
    }

    public static void startGlowGuiSprite() {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        RenderSystem.depthMask(false);
        RenderSystem.setShader(FluffyFurShaders::getGlowingSprite);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
    }

    public static void endGlowGuiSprite() {
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
    }

    public static void renderWavyFluid(PoseStack stack, FluidStack fluidStack, float size, float strength, float time) {
        renderWavyFluid(stack, fluidStack, size, size, size, strength, time);
    }

    public static void renderWavyFluid(PoseStack stack, FluidStack fluidStack, float width, float height, float length, float strength, float time) {
        if (!fluidStack.isEmpty()) {
            FluidType type = fluidStack.getFluid().getFluidType();
            IClientFluidTypeExtensions clientType = IClientFluidTypeExtensions.of(type);
            TextureAtlasSprite still = RenderUtil.getSprite(clientType.getStillTexture(fluidStack));

            RenderBuilder.create().setRenderType(FluffyFurRenderTypes.TRANSPARENT_TEXTURE)
                    .setUV(still.getU0(), still.getV0(), still.getU1(), still.getV1())
                    .setColor(ColorUtil.getColor(clientType.getTintColor(fluidStack)))
                    .setAlpha(ColorUtil.getColor(clientType.getTintColor(fluidStack)).getAlpha() / 255f)
                    .renderWavyCube(stack, width, strength, time);
        }
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

    public static void applyWobble(Vector3f[] offsets, float strength, float gameTime) {
        float offset = 0;
        for (Vector3f vector3f : offsets) {
            double time = ((gameTime / 40.0F) % Math.PI * 2);
            float sine = Mth.sin((float) (time + (offset * Math.PI * 2))) * strength;
            vector3f.add(sine, -sine, 0);
            offset += 0.25f;
        }
    }
}
