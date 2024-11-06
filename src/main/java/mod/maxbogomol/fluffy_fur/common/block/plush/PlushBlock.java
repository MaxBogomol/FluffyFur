package mod.maxbogomol.fluffy_fur.common.block.plush;

import mod.maxbogomol.fluffy_fur.common.network.FluffyFurPacketHandler;
import mod.maxbogomol.fluffy_fur.common.network.block.PlushHeartsPacket;
import mod.maxbogomol.fluffy_fur.registry.common.block.FluffyFurBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlushBlock extends Block implements EntityBlock, SimpleWaterloggedBlock {

    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;

    private static final VoxelShape SHAPE = Block.box(5, 0, 5, 11, 12, 11);

    public PlushBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false).setValue(ROTATION, Integer.valueOf(0)));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.WATERLOGGED).add(ROTATION);;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER).setValue(ROTATION, Integer.valueOf(RotationSegment.convertToSegment(context.getRotation() + 180.0F)));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide()) {
            if (state.getBlock().equals(FluffyFurBlocks.MAXBOGOMOL_PLUSH.get()) || state.getBlock().equals(FluffyFurBlocks.NANACHI_PLUSH.get())) {
                level.playSound(null, pos, SoundEvents.FOX_AMBIENT, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            if (state.getBlock().equals(FluffyFurBlocks.ONIXTHECAT_PLUSH.get())) {
                level.playSound(null, pos, SoundEvents.GOAT_AMBIENT, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            if (state.getBlock().equals(FluffyFurBlocks.SAMMYSEMICOLON_PLUSH.get()) || state.getBlock().equals(FluffyFurBlocks.BOYKISSER_PLUSH.get())) {
                level.playSound(null, pos, SoundEvents.CAT_AMBIENT, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            FluffyFurPacketHandler.sendToTracking(level, pos, new PlushHeartsPacket(pos));
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PlushBlockEntity(pos, state);
    }
}
