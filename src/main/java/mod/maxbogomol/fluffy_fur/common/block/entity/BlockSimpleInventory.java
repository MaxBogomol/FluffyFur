package mod.maxbogomol.fluffy_fur.common.block.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemHandlerHelper;

public abstract class BlockSimpleInventory extends BlockEntityBase {

    private final SimpleContainer itemHandler = createItemHandler();

    public BlockSimpleInventory(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        itemHandler.addListener(i -> setChanged());
    }

    private static void copyToInv(NonNullList<ItemStack> src, Container dest) {
        Preconditions.checkArgument(src.size() == dest.getContainerSize());
        for (int i = 0; i < src.size(); i++) {
            dest.setItem(i, src.get(i));
        }
    }

    private static NonNullList<ItemStack> copyFromInv(Container inv) {
        NonNullList<ItemStack> ret = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ret.set(i, inv.getItem(i));
        }
        return ret;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        NonNullList<ItemStack> tmp = NonNullList.withSize(inventorySize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, tmp);
        copyToInv(tmp, itemHandler);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, copyFromInv(itemHandler));
    }

    public final int inventorySize() {
        return getItemHandler().getContainerSize();
    }

    protected abstract SimpleContainer createItemHandler();

    public final Container getItemHandler() {
        return itemHandler;
    }

    public static void addHandPlayerItem(Level level, Player player, InteractionHand hand, ItemStack stack, ItemStack addStack) {
        if (player.getInventory().getSlotWithRemainingSpace(addStack) >= 0) {
            addPlayerItem(level, player, addStack);
        } else if (stack.isEmpty()) {
            player.setItemInHand(hand, addStack.copy());
        } else if (ItemHandlerHelper.canItemStacksStack(stack, addStack) && (stack.getCount() + addStack.getCount() <= addStack.getMaxStackSize())) {
            stack.setCount(stack.getCount() + addStack.getCount());
            player.setItemInHand(hand, stack);
        } else {
            addPlayerItem(level, player, addStack);
        }
    }

    public static void addPlayerItem(Level level, Player player, ItemStack addStack) {
        if (player.getInventory().getSlotWithRemainingSpace(addStack) != -1 || player.getInventory().getFreeSlot() > -1) {
            player.getInventory().add(addStack.copy());
        } else {
            level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY(), player.getZ(), addStack.copy()));
        }
    }
}