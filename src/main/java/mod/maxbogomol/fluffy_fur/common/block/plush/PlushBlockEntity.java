package mod.maxbogomol.fluffy_fur.common.block.plush;

import mod.maxbogomol.fluffy_fur.common.block.entity.BlockEntityBase;
import mod.maxbogomol.fluffy_fur.registry.common.block.FluffyFurBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class PlushBlockEntity extends BlockEntityBase {

    public PlushBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public PlushBlockEntity(BlockPos pos, BlockState state) {
        this(FluffyFurBlockEntities.PLUSH.get(), pos, state);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {

    }

    @Override
    public void load(CompoundTag tag) {

    }
}
