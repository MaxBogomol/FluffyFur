package mod.maxbogomol.fluffy_fur.util;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.client.render.item.CustomItemRenderer;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
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

    public static TextureAtlasSprite getSprite(ResourceLocation resourceLocation) {
        return Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(resourceLocation);
    }

    public static TextureAtlasSprite getSprite(String modId, String sprite) {
        return getSprite(new ResourceLocation(modId, sprite));
    }

    public static void renderFluid(PoseStack stack, FluidStack fluidStack, float size, float texSize, boolean flowing, int light) {
        renderFluid(stack, fluidStack, size, size, size, texSize, texSize, texSize, flowing, light);
    }

    public static void renderFluid(PoseStack stack, FluidStack fluidStack, float size, float texSize, Color color, boolean flowing, int light) {
        renderFluid(stack, fluidStack, size, size, size, texSize, texSize, texSize, color, flowing, light);
    }

    public static void renderFluid(PoseStack stack, FluidStack fluidStack, float width, float height, float length, float texWidth, float texHeight, float texLength, boolean flowing, int light) {
        if (!fluidStack.isEmpty()) {
            RenderBuilder builder = getFluidRenderBuilder(fluidStack, texWidth, texHeight, texLength, flowing, light);
            builder.renderCube(stack, width, height, length);
        }
    }

    public static void renderFluid(PoseStack stack, FluidStack fluidStack, float width, float height, float length, float texWidth, float texHeight, float texLength, Color color, boolean flowing, int light) {
        if (!fluidStack.isEmpty()) {
            RenderBuilder builder = getFluidRenderBuilder(fluidStack, texWidth, texHeight, texLength, flowing, light);
            builder.setColor(color).renderCube(stack, width, height, length);
        }
    }

    public static void renderCenteredFluid(PoseStack stack, FluidStack fluidStack, float size, float texSize, boolean flowing, int light) {
        renderCenteredFluid(stack, fluidStack, size, size, size, texSize, texSize, texSize, flowing, light);
    }

    public static void renderCenteredFluid(PoseStack stack, FluidStack fluidStack, float size, float texSize, Color color, boolean flowing, int light) {
        renderCenteredFluid(stack, fluidStack, size, size, size, texSize, texSize, texSize, color, flowing, light);
    }

    public static void renderCenteredFluid(PoseStack stack, FluidStack fluidStack, float width, float height, float length, float texWidth, float texHeight, float texLength, boolean flowing, int light) {
        if (!fluidStack.isEmpty()) {
            RenderBuilder builder = getFluidRenderBuilder(fluidStack, texWidth, texHeight, texLength, flowing, light);
            builder.renderCenteredCube(stack, width, height, length);
        }
    }

    public static void renderCenteredFluid(PoseStack stack, FluidStack fluidStack, float width, float height, float length, float texWidth, float texHeight, float texLength, Color color, boolean flowing, int light) {
        if (!fluidStack.isEmpty()) {
            RenderBuilder builder = getFluidRenderBuilder(fluidStack, texWidth, texHeight, texLength, flowing, light);
            builder.setColor(color).renderCenteredCube(stack, width, height, length);
        }
    }

    public static void renderWavyFluid(PoseStack stack, FluidStack fluidStack, float size, float texSize, boolean flowing, int light, float strength, float time) {
        renderWavyFluid(stack, fluidStack, size, size, size, texSize, texSize, texSize, flowing, light, strength, time);
    }

    public static void renderWavyFluid(PoseStack stack, FluidStack fluidStack, float size, float texSize, Color color, boolean flowing, int light, float strength, float time) {
        renderWavyFluid(stack, fluidStack, size, size, size, texSize, texSize, texSize, color, flowing, light, strength, time);
    }

    public static void renderWavyFluid(PoseStack stack, FluidStack fluidStack, float width, float height, float length, float texWidth, float texHeight, float texLength, boolean flowing, int light, float strength, float time) {
        if (!fluidStack.isEmpty()) {
            RenderBuilder builder = getFluidRenderBuilder(fluidStack, texWidth, texHeight, texLength, flowing, light);
            builder.renderWavyCube(stack, width, height, length, strength, time);
        }
    }

    public static void renderWavyFluid(PoseStack stack, FluidStack fluidStack, float width, float height, float length, float texWidth, float texHeight, float texLength, Color color, boolean flowing, int light, float strength, float time) {
        if (!fluidStack.isEmpty()) {
            RenderBuilder builder = getFluidRenderBuilder(fluidStack, texWidth, texHeight, texLength, flowing, light);
            builder.setColor(color).renderWavyCube(stack, width, height, length, strength, time);
        }
    }

    public static RenderBuilder getFluidRenderBuilder(FluidStack fluidStack, float texWidth, float texHeight, float texLength, boolean flowing, int light) {
        RenderBuilder builder = RenderBuilder.create().setRenderType(FluffyFurRenderTypes.TRANSLUCENT_TEXTURE);
        if (!fluidStack.isEmpty()) {
            FluidType type = fluidStack.getFluid().getFluidType();
            IClientFluidTypeExtensions clientType = IClientFluidTypeExtensions.of(type);
            TextureAtlasSprite sprite = RenderUtil.getSprite(clientType.getStillTexture(fluidStack));
            if (flowing) sprite = RenderUtil.getSprite(clientType.getFlowingTexture(fluidStack));

            builder.setFirstUV(sprite.getU0(), sprite.getV0(), sprite.getU0() + ((sprite.getU1() - sprite.getU0()) * texLength), sprite.getV0() + ((sprite.getV1() - sprite.getV0()) * texWidth))
                    .setSecondUV(sprite.getU0(), sprite.getV0(), sprite.getU0() + ((sprite.getU1() - sprite.getU0()) * texWidth), sprite.getV0() + ((sprite.getV1() - sprite.getV0()) * texHeight))
                    .setThirdUV(sprite.getU0(), sprite.getV0(), sprite.getU0() + ((sprite.getU1() - sprite.getU0()) * texLength), sprite.getV0() + ((sprite.getV1() - sprite.getV0()) * texHeight))
                    .setColor(ColorUtil.getColor(clientType.getTintColor(fluidStack)))
                    .setLight(Math.max(type.getLightLevel(fluidStack) << 4, light & 0xFFFF));
        }
        return builder;
    }

    public static void renderConnectLine(PoseStack stack, Vec3 from, Vec3 to, Color color, float alpha) {
        double dX = to.x() - from.x();
        double dY = to.y() - from.y();
        double dZ = to.z() - from.z();

        double yaw = Math.atan2(dZ, dX);
        double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

        stack.pushPose();
        stack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(-yaw)));
        stack.mulPose(Axis.ZP.rotationDegrees((float) Math.toDegrees(-pitch) - 180f));
        RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE)
                .setColor(color)
                .setAlpha(alpha)
                .renderRay(stack, 0.01f, (float) from.distanceTo(to) + 0.01f);
        stack.popPose();
    }

    public static void renderConnectLine(PoseStack stack, BlockPos posFrom, BlockPos posTo, Color color, float alpha) {
        renderConnectLine(stack, posFrom.getCenter(), posTo.getCenter(), color, alpha);
    }

    public static void renderConnectLineOffset(PoseStack stack, Vec3 from, Vec3 to, Color color, float alpha) {
        stack.pushPose();
        stack.translate(from.x(), from.y(), from.z());
        renderConnectLine(stack, from, to, color, alpha);
        stack.popPose();
    }

    public static void renderConnectBoxLines(PoseStack stack, Vec3 size, Color color, float alpha) {
        renderConnectLineOffset(stack, new Vec3(0, 0, 0), new Vec3(size.x() , 0, 0), color, alpha);
        renderConnectLineOffset(stack, new Vec3(size.x(), 0, 0), new Vec3(size.x(), 0, size.z()), color, alpha);
        renderConnectLineOffset(stack, new Vec3(size.x(), 0, size.z()), new Vec3(0, 0, size.z()), color, alpha);
        renderConnectLineOffset(stack, new Vec3(0, 0, size.z()), new Vec3(0, 0, 0), color, alpha);

        renderConnectLineOffset(stack, new Vec3(0, 0, 0), new Vec3(0, size.y(), 0), color, alpha);
        renderConnectLineOffset(stack, new Vec3(size.x(), 0, 0), new Vec3(size.x(), size.y(), 0), color, alpha);
        renderConnectLineOffset(stack, new Vec3(size.x(), 0, size.z()), new Vec3(size.x(), size.y(), size.z()), color, alpha);
        renderConnectLineOffset(stack, new Vec3(0, 0, size.z()), new Vec3(0, size.y(), size.z()), color, alpha);

        renderConnectLineOffset(stack, new Vec3(0, size.y(), 0), new Vec3(size.x(), size.y(), 0), color, alpha);
        renderConnectLineOffset(stack, new Vec3(size.x(), size.y(), 0), new Vec3(size.x() , size.y(), size.z()), color, alpha);
        renderConnectLineOffset(stack, new Vec3(size.x(), size.y(), size.z()), new Vec3(0, size.y(), size.z()), color, alpha);
        renderConnectLineOffset(stack, new Vec3(0, size.y(), size.z()), new Vec3(0, size.y(), 0), color, alpha);
    }

    public static void renderConnectSideLines(PoseStack stack, Vec3 size, Color color, float alpha) {
        renderConnectLineOffset(stack, new Vec3(0, 0, 0), new Vec3(size.x() , 0, 0), color, alpha);
        renderConnectLineOffset(stack, new Vec3(size.x(), 0, 0), new Vec3(size.x(), 0, size.z()), color, alpha);
        renderConnectLineOffset(stack, new Vec3(size.x(), 0, size.z()), new Vec3(0, 0, size.z()), color, alpha);
        renderConnectLineOffset(stack, new Vec3(0, 0, size.z()), new Vec3(0, 0, 0), color, alpha);
    }

    public static void renderConnectSide(PoseStack stack, Direction side, Color color, float alpha) {
        Vec3 size = new Vec3(1, 1, 1);
        stack.pushPose();
        stack.translate(0.5f, 0.5f, 0.5f);
        stack.mulPose(side.getOpposite().getRotation());
        stack.translate(-size.x() / 2f, -size.y() / 2f, -size.z() / 2f);
        renderConnectSideLines(stack, size, color, alpha);
        stack.popPose();
    }

    public static Vector3f parametricSphere(float u, float v, float r) {
        return new Vector3f(Mth.cos(u) * Mth.sin(v) * r, Mth.cos(v) * r, Mth.sin(u) * Mth.sin(v) * r);
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
