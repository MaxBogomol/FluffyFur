package mod.maxbogomol.fluffy_fur.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.client.shader.VertexAttributeHandler;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurVertexFormats;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Random;

public class ThingItem extends Item implements IParticleItem, IGuiParticleItem {

    private static final Random random = new Random();

    public ThingItem(Properties properties) {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flags) {
        list.add(Component.translatable("gui.fluffy_fur.menu.mcreator_mods").append(" " + FluffyFur.mcreatorModsCount).withStyle(ChatFormatting.DARK_RED)
                .append(Screen.hasShiftDown() || FluffyFur.mcreatorModsCount == 0 ? Component.empty() : Component.literal(" []").withStyle(ChatFormatting.GRAY)));
        if (Screen.hasShiftDown()) {
            for (String string : FluffyFur.mcreatorModsList) {
                list.add(Component.literal(string).withStyle(ChatFormatting.RED));
            }
        }
    }

    @Override
    public void addParticles(Level level, ItemEntity entity) {
        if (random.nextFloat() < 0.5f) {
            ParticleBuilder.create(FluffyFurParticles.TINY_WISP)
                    .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                    .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                    .setScaleData(GenericParticleData.create(0.3f, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                    .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                    .setLifetime(30, 10)
                    .setVelocity(0, 0.06f, 0)
                    .randomVelocity(0.01f)
                    .randomOffset(0.125f)
                    .spawn(level, entity.getX(), entity.getY() + 0.25F, entity.getZ());
        }
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

        if (FluffyFur.mcreatorModsCount > 0) {
            poseStack.pushPose();
            poseStack.translate(x + 8, y + 8, 100);
            poseStack.mulPose(Axis.ZP.rotationDegrees(ticks / 10f));

            TextureAtlasSprite sprite = RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/star");

            FluffyFurVertexFormats.UV_DISTORT_HOLDER.setValue(sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1());
            FluffyFurVertexFormats.AMPLIFIER_DISTORT_HOLDER.setValue(15f);
            builder = RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_DISTORT);
            builder.setUV(sprite);
            builder.setColorRaw(1, 0, 0)
                    .renderCenteredQuad(poseStack, 20f)
                    .endBatch();
            poseStack.popPose();
            VertexAttributeHandler.clear(FluffyFurVertexFormats.ADDITIVE_DISTORT);
        }
    }
}
