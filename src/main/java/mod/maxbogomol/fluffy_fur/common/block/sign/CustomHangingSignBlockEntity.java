package mod.maxbogomol.fluffy_fur.common.block.sign;

import mod.maxbogomol.fluffy_fur.registry.common.block.FluffyFurBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CustomHangingSignBlockEntity extends HangingSignBlockEntity {

    public CustomHangingSignBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return FluffyFurBlockEntities.HANGING_SIGN.get();
    }
}
