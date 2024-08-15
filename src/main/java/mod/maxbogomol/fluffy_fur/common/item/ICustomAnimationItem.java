package mod.maxbogomol.fluffy_fur.common.item;

import mod.maxbogomol.fluffy_fur.client.animation.ItemAnimation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ICustomAnimationItem {
    @OnlyIn(Dist.CLIENT)
    ItemAnimation getAnimation(ItemStack stack);
}
