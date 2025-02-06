package mod.maxbogomol.fluffy_fur.common.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;

public class FuelBlockItem extends BlockItem {
    public int fuel;

    public FuelBlockItem(Block block, Properties properties, int fuel) {
        super(block, properties);
        this.fuel = fuel;
    }

    @Override
    public int getBurnTime(ItemStack stack, @Nullable RecipeType<?> recipeType) {
        return fuel;
    }
}
