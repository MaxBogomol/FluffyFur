package mod.maxbogomol.fluffy_fur.common.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface ICustomBlockEntityDataItem {
    CompoundTag getCustomBlockEntityData(ItemStack stack, CompoundTag nbt);
}
