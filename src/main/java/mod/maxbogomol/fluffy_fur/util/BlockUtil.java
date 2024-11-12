package mod.maxbogomol.fluffy_fur.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BlockUtil {

    public static boolean growCrop(ItemStack stack, Level level, BlockPos blockPos) {
        if (BoneMealItem.growCrop(stack, level, blockPos)) {
            return true;
        } else {
            BlockState blockstate = level.getBlockState(blockPos);
            boolean flag = blockstate.isFaceSturdy(level, blockPos, Direction.UP);
            return flag && BoneMealItem.growWaterPlant(stack, level, blockPos.relative(Direction.UP), Direction.UP);
        }
    }

    public static boolean growCrop(Level level, BlockPos blockPos) {
        return growCrop(ItemStack.EMPTY, level, blockPos);
    }
}
