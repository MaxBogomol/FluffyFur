package mod.maxbogomol.fluffy_fur.mixin.client;

import mod.maxbogomol.fluffy_fur.config.FluffyFurClientConfig;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.common.item.IGuiParticleItem;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin {

    @Inject(at = @At(value = "TAIL"), method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;IIII)V")
    private void fluffy_fur$renderItem(LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset, CallbackInfo ci) {
        if (FluffyFurClientConfig.ITEM_GUI_PARTICLE.get()) {
            if (stack.getItem() instanceof IGuiParticleItem guiParticleItem) {
                GuiGraphics self = (GuiGraphics) ((Object) this);
                guiParticleItem.renderParticle(self.pose(), entity, level, stack, x, y, seed, guiOffset);
            }
        }

        for (RenderBuilder builder : FluffyFurRenderTypes.customItemRenderBuilderGui) {
            builder.endBatch();
        }
        FluffyFurRenderTypes.customItemRenderBuilderGui.clear();
    }
}
