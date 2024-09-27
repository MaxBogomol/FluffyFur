package mod.maxbogomol.fluffy_fur.common.recipe;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidHandlerContext implements Container {

    public IFluidHandler fluid;

    public FluidHandlerContext(IFluidHandler fluid) {
        this.fluid = fluid;
    }

    @Override
    @Deprecated
    public void clearContent() { }

    @Override
    @Deprecated
    public int getContainerSize() {
        return 0;
    }

    @Override
    @Deprecated
    public boolean isEmpty() {
        return true;
    }

    @Override
    @Deprecated
    public ItemStack getItem(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    @Deprecated
    public ItemStack removeItem(int slot, int amount) {
        return ItemStack.EMPTY;
    }

    @Override
    @Deprecated
    public ItemStack removeItemNoUpdate(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    @Deprecated
    public void setItem(int slot, ItemStack stack) { }

    @Override
    @Deprecated
    public void setChanged() { }

    @Override
    @Deprecated
    public boolean stillValid(Player player) {
        return false;
    }
}