package mod.maxbogomol.fluffy_fur.common.fire;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class FireBlockHandler {
    public static List<FireBlockModifier> modifiers = new ArrayList<>();

    public static void register(FireBlockModifier modifier) {
        modifiers.add(modifier);
    }

    public static List<FireBlockModifier> getModifiers() {
        return modifiers;
    }

    public static boolean canLightBlock(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
        for (FireBlockModifier modifier : getModifiers()) {
            if (modifier.canLightBlock(level, blockPos, blockState, entity)) {
                return true;
            }
        }
        return false;
    }

    public static boolean canSetFireBlock(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
        return BaseFireBlock.canBePlacedAt(level, blockPos, Direction.UP);
    }

    public static boolean canSetFireBlock(Level level, BlockPos blockPos, BlockState blockState, Direction direction, Entity entity) {
        return BaseFireBlock.canBePlacedAt(level, blockPos, direction);
    }

    public static void setFireBlock(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
        BlockState blockState1 = getFireBlockState(level, blockPos, blockState, entity);
        level.setBlock(blockPos, blockState1, 11);
    }

    public static BlockState getFireBlockState(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
        return BaseFireBlock.getState(level, blockPos);
    }

    public static void setLightBlock(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
        for (FireBlockModifier modifier : getModifiers()) {
            if (modifier.canLightBlock(level, blockPos, blockState, entity)) {
                modifier.setLightBlock(level, blockPos, blockState, entity);
                break;
            }
        }
    }
}
