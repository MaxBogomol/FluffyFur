package mod.maxbogomol.fluffy_fur.client.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurModels;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.registry.common.item.FluffyFurItems;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class FluffyFurItemRenderer extends BlockEntityWithoutLevelRenderer {

    public FluffyFurItemRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelSet) {
        super(blockEntityRenderDispatcher, entityModelSet);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        if (stack.getItem() == FluffyFurItems.FIRCH_PLUSH.get()) {
            poseStack.pushPose();
            poseStack.translate(0.5f, 0.5f, 0.5f);
            RenderUtil.renderCustomModel(FluffyFurModels.FIRCH_PLUSH_PIECE, ItemDisplayContext.NONE, false, poseStack, buffer, packedLight, packedOverlay);
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(0.5f, 0, 0.5f);
            poseStack.translate(0.203125f, 0.671875f, 0.015625);
            poseStack.mulPose(Axis.ZP.rotationDegrees(-75));
            poseStack.mulPose(Axis.YP.rotationDegrees(-50));
            poseStack.mulPose(Axis.XP.rotationDegrees(15));
            RenderUtil.renderCustomModel(FluffyFurModels.FIRCH_PLUSH_EAR, ItemDisplayContext.NONE, false, poseStack, buffer, packedLight, packedOverlay);
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(0.5f, 0, 0.5f);
            poseStack.translate(0, 0.734375f, 0.015625f);
            poseStack.mulPose(Axis.XP.rotationDegrees(-25));
            poseStack.translate(0, 0.0625f, -0.0625f);
            RenderUtil.renderCustomModel(FluffyFurModels.FIRCH_PLUSH_EAR, ItemDisplayContext.NONE, false, poseStack, buffer, packedLight, packedOverlay);
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(0.5f, 0, 0.5f);
            poseStack.translate(-0.203125f, 0.671875f, 0.015625);
            poseStack.mulPose(Axis.ZP.rotationDegrees(75));
            poseStack.mulPose(Axis.YP.rotationDegrees(50));
            poseStack.mulPose(Axis.XP.rotationDegrees(15));
            RenderUtil.renderCustomModel(FluffyFurModels.FIRCH_PLUSH_EAR, ItemDisplayContext.NONE, false, poseStack, buffer, packedLight, packedOverlay);
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(0.5f, 0.40625f, 0.296875f);
            RenderBuilder builder = RenderBuilder.create().setRenderType(FluffyFurRenderTypes.TRANSLUCENT);
            builder.setColor(234, 254, 255, 80f / 255f).setLight(packedLight).setSided(true);
            poseStack.translate(0.03125f, 0, 0);
            builder.renderQuad(poseStack, 0.1875f);
            poseStack.translate(-0.25f, 0, 0);
            builder.renderQuad(poseStack, 0.1875f);
            if (displayContext.firstPerson()) FluffyFurRenderTypes.addCustomItemRenderBuilderFirst(builder);
            if (displayContext == ItemDisplayContext.GUI) FluffyFurRenderTypes.addCustomItemRenderBuilderGui(builder);
            poseStack.popPose();
        }
    }
}
