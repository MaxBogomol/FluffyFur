package mod.maxbogomol.fluffy_fur.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ThingItem extends Item implements IGuiParticleItem {

    public ThingItem(Properties properties) {
        super(properties);
    }

    @Override
    public void renderParticle(PoseStack poseStack, LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset) {
        float ticks = ClientTickHandler.getTotal() * 0.5f;

        poseStack.pushPose();
        poseStack.translate(x + 8, y + 8, 100);
        poseStack.mulPose(Axis.ZP.rotationDegrees(ticks));
        RenderBuilder builder = RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE);
        builder.setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/star"));
        builder.setColorRaw(0, 0, 1).setAlpha(1f)
                .renderCenteredQuad(poseStack, 10f);
        poseStack.mulPose(Axis.ZP.rotationDegrees(25.5f));
        builder.setColorRaw(1, 0, 1).setAlpha(0.5f)
                .renderCenteredQuad(poseStack, 10f)
                .endBatch();
        poseStack.popPose();
    }
}
