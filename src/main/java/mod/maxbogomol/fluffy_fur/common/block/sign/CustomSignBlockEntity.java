package mod.maxbogomol.fluffy_fur.common.block.sign;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CustomSignBlockEntity extends SignBlockEntity {

    public CustomSignBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return FluffyFur.SIGN_BLOCK_ENTITY.get();
    }
}
