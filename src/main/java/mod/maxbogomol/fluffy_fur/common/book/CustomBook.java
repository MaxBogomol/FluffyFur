package mod.maxbogomol.fluffy_fur.common.book;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.client.model.book.CustomBookModel;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurModels;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class CustomBook {

    public Random random = new Random();

    public boolean isBook(Level level, Vec3 pos, ItemStack itemStack) {
        return false;
    }

    public CustomBookComponent getComponent() {
        return new CustomBookComponent();
    }

    public ResourceLocation getTexture(Level level, Vec3 pos, ItemStack itemStack, CustomBookComponent component) {
        return null;
    }

    @OnlyIn(Dist.CLIENT)
    public CustomBookModel getModel(Level level, Vec3 pos, ItemStack itemStack, CustomBookComponent component) {
        return FluffyFurModels.BOOK;
    }

    @OnlyIn(Dist.CLIENT)
    public void openGui(Level level, ItemStack stack) {

    }

    @OnlyIn(Dist.CLIENT)
    public void openGui(Level level, Vec3 pos, ItemStack stack) {

    }

    public void tick(Level level, Vec3 pos, ItemStack itemStack, CustomBookComponent component, double distance) {
        component.oOpen = component.open;
        component.oRot = component.rot;
        Player player = level.getNearestPlayer(pos.x(), pos.y(), pos.z(), distance, false);
        if (player != null) {
            double d0 = player.getX() - (pos.x());
            double d1 = player.getZ() - (pos.z());
            component.tRot = (float) Mth.atan2(d1, d0);
            component.open += 0.1F;
            if (component.open < 0.5F || random.nextInt(40) == 0) {
                float f1 = component.flipT;
                do {
                    component.flipT += (float) (random.nextInt(4) - random.nextInt(4));
                } while(f1 == component.flipT);
            }
        } else {
            component.tRot += 0.02F;
            component.open -= 0.1F;
        }

        while(component.rot >= (float) Math.PI) {
            component.rot -= ((float) Math.PI * 2F);
        }

        while(component.rot < -(float) Math.PI) {
            component.rot += ((float) Math.PI * 2F);
        }

        while(component.tRot >= (float) Math.PI) {
            component.tRot -= ((float)Math.PI * 2F);
        }

        while(component.tRot < -(float) Math.PI) {
            component.tRot += ((float) Math.PI * 2F);
        }

        float f2;
        for(f2 = component.tRot - component.rot; f2 >= (float) Math.PI; f2 -= ((float) Math.PI * 2F)) {}

        while(f2 < -(float) Math.PI) {
            f2 += ((float) Math.PI * 2F);
        }

        component.rot += f2 * 0.4F;
        component.open = Mth.clamp(component.open, 0.0F, 1.0F);
        ++component.time;
        component.oFlip = component.flip;
        float f = (component.flipT - component.flip) * 0.4F;
        float f3 = 0.2F;
        f = Mth.clamp(f, -0.2F, 0.2F);
        component.flipA += (f - component.flipA) * 0.9F;
        component.flip += component.flipA;
    }

    @OnlyIn(Dist.CLIENT)
    public void setupAnim(Level level, Vec3 pos, ItemStack itemStack, CustomBookComponent component, CustomBookModel model, float partialTicks) {
        float f = (float) component.time + partialTicks;
        float f3 = Mth.lerp(partialTicks, component.oFlip, component.flip);
        float f4 = Mth.frac(f3 + 0.25F) * 1.6F - 0.3F;
        float f5 = Mth.frac(f3 + 0.75F) * 1.6F - 0.3F;
        float f6 = Mth.lerp(partialTicks, component.oOpen, component.open);
        model.setupAnim(f, Mth.clamp(f4, 0.0F, 1.0F), Mth.clamp(f5, 0.0F, 1.0F), f6);
    }

    @OnlyIn(Dist.CLIENT)
    public void renderModel(Level level, Vec3 pos, ItemStack itemStack, CustomBookComponent component, CustomBookModel model, ResourceLocation texture, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        model.render(poseStack, bufferSource.getBuffer(RenderType.entityCutout(texture)), light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    @OnlyIn(Dist.CLIENT)
    public void render(Level level, Vec3 pos, ItemStack itemStack, CustomBookComponent component, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        CustomBookModel model = getModel(level, pos, itemStack, component);
        ResourceLocation texture = getTexture(level, pos, itemStack, component);
        setupAnim(level, pos, itemStack, component, model, partialTicks);
        renderModel(level, pos, itemStack, component, model, texture, partialTicks, poseStack, bufferSource, light, overlay);
    }
}
