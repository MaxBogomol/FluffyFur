package mod.maxbogomol.fluffy_fur.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.common.block.plush.PlushBlock;
import mod.maxbogomol.fluffy_fur.common.block.plush.PlushBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class PlushRenderer implements BlockEntityRenderer<PlushBlockEntity> {

    @Override
    public void render(PlushBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Minecraft minecraft = Minecraft.getInstance();

        int rotate = 0;
        Optional<Integer> rotation = blockEntity.getBlockState().getOptionalValue(PlushBlock.ROTATION);
        if (rotation.isPresent()) rotate = rotation.get();
        ItemStack stack = new ItemStack(blockEntity.getBlockState().getBlock().asItem());

        poseStack.pushPose();
        poseStack.translate(0.5f, 0.5f, 0.5f);
        poseStack.mulPose(Axis.YP.rotationDegrees((float) rotate * -22.5f + 180f));
        minecraft.getItemRenderer().renderStatic(stack, ItemDisplayContext.NONE, light, overlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
        poseStack.popPose();
    }
}
