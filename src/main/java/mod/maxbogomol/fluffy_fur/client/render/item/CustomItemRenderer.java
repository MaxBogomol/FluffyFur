package mod.maxbogomol.fluffy_fur.client.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.MatrixUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;

public class CustomItemRenderer extends ItemRenderer {
    
    public CustomItemRenderer(Minecraft minecraft, TextureManager textureManager, ModelManager modelManager, ItemColors itemColors, BlockEntityWithoutLevelRenderer blockEntityRenderer) {
        super(minecraft, textureManager, modelManager, itemColors, blockEntityRenderer);
    }

    public void renderItem(ItemStack itemStack, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, BakedModel bakedModel) {
        if (!itemStack.isEmpty()) {
            poseStack.pushPose();
            boolean flag = displayContext == ItemDisplayContext.GUI || displayContext == ItemDisplayContext.GROUND || displayContext == ItemDisplayContext.FIXED;
            if (flag) {
                if (itemStack.is(Items.TRIDENT)) {
                    bakedModel = this.itemModelShaper.getModelManager().getModel(TRIDENT_MODEL);
                } else if (itemStack.is(Items.SPYGLASS)) {
                    bakedModel = this.itemModelShaper.getModelManager().getModel(SPYGLASS_MODEL);
                }
            }

            bakedModel = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(poseStack, bakedModel, displayContext, leftHand);
            poseStack.translate(-0.5F, -0.5F, -0.5F);
            if (!bakedModel.isCustomRenderer() && (!itemStack.is(Items.TRIDENT) || flag)) {
                boolean flag1;
                if (displayContext != ItemDisplayContext.GUI && !displayContext.firstPerson() && itemStack.getItem() instanceof BlockItem) {
                    Block block = ((BlockItem) itemStack.getItem()).getBlock();
                    flag1 = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
                } else {
                    flag1 = true;
                }
                for (var model : bakedModel.getRenderPasses(itemStack, flag1)) {
                    for (var rendertype : model.getRenderTypes(itemStack, flag1)) {
                        VertexConsumer vertexconsumer;
                        if (hasAnimatedTexture(itemStack) && itemStack.hasFoil()) {
                            poseStack.pushPose();
                            PoseStack.Pose posestack$pose = poseStack.last();
                            if (displayContext == ItemDisplayContext.GUI) {
                                MatrixUtil.mulComponentWise(posestack$pose.pose(), 0.5F);
                            } else if (displayContext.firstPerson()) {
                                MatrixUtil.mulComponentWise(posestack$pose.pose(), 0.75F);
                            }

                            if (flag1) {
                                vertexconsumer = getCompassFoilBufferDirect(buffer, rendertype, posestack$pose);
                            } else {
                                vertexconsumer = getCompassFoilBuffer(buffer, rendertype, posestack$pose);
                            }

                            poseStack.popPose();
                        } else if (flag1) {
                            vertexconsumer = getFoilBufferDirect(buffer, rendertype, true, itemStack.hasFoil());
                        } else {
                            vertexconsumer = getFoilBuffer(buffer, rendertype, true, itemStack.hasFoil());
                        }

                        this.renderModelLists(model, itemStack, combinedLight, combinedOverlay, poseStack, vertexconsumer);
                    }
                }
            } else {
                net.minecraftforge.client.extensions.common.IClientItemExtensions.of(itemStack).getCustomRenderer().renderByItem(itemStack, displayContext, poseStack, buffer, combinedLight, combinedOverlay);
            }

            poseStack.popPose();
        }
    }

    private static boolean hasAnimatedTexture(ItemStack stack) {
        return stack.is(ItemTags.COMPASSES) || stack.is(Items.CLOCK);
    }
}
