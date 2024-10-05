package mod.maxbogomol.fluffy_fur.integration.common.curios;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface ICurioItemTexture {
    ResourceLocation getTexture(ItemStack stack, LivingEntity entity);
}
