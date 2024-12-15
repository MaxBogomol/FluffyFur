package mod.maxbogomol.fluffy_fur.common.fire;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;

public class FireBlockModifier {

    public boolean canLightBlock(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
        return CampfireBlock.canLight(blockState) || CandleBlock.canLight(blockState) || CandleCakeBlock.canLight(blockState);
    }

    public void setLightBlock(Level level, BlockPos blockPos,BlockState blockState, Entity entity) {
        level.setBlock(blockPos, blockState.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
        if (entity != null) level.gameEvent(entity, GameEvent.BLOCK_CHANGE, blockPos);
    }
}
