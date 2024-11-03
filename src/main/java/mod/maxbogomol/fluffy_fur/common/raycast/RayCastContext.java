package mod.maxbogomol.fluffy_fur.common.raycast;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Predicate;

public class RayCastContext {
    public Vec3 startPos;
    public Vec3 endPos;
    public Predicate<BlockPos> blockPosFilter = (b) -> true;
    public Predicate<FluidState> fluidFilter = RayCastContext.Fluid.NONE;
    public Predicate<Entity> entityFilter = (e) -> false;
    public int entityCount = 0;
    public float entitySize = 0;
    public boolean entityEnd = false;
    public RayCastContext.Block blockShape = RayCastContext.Block.COLLIDER;
    public CollisionContext collisionContext = CollisionContext.empty();

    public RayCastContext() {
        this.startPos = Vec3.ZERO;
        this.endPos = Vec3.ZERO;
    }

    public RayCastContext(Vec3 startPos, Vec3 endPos) {
        this.startPos = startPos;
        this.endPos = endPos;
    }

    public RayCastContext setStartPos(Vec3 startPos) {
        this.startPos = startPos;
        return this;
    }

    public RayCastContext setEndPos(Vec3 endPos) {
        this.endPos = endPos;
        return this;
    }

    public RayCastContext setBlockPosFilter(Predicate<BlockPos> blockPosFilter) {
        this.blockPosFilter = blockPosFilter;
        return this;
    }

    public RayCastContext setFluidFilter(Predicate<FluidState> fluidFilter) {
        this.fluidFilter = fluidFilter;
        return this;
    }

    public RayCastContext setEntityFilter(Predicate<Entity> entityFilter) {
        this.entityFilter = entityFilter;
        return this;
    }

    public RayCastContext setEntityCount(int entityCount) {
        this.entityCount = entityCount;
        return this;
    }

    public RayCastContext setEntitySize(float entitySize) {
        this.entitySize = entitySize;
        return this;
    }

    public RayCastContext setEntityEnd(boolean entityEnd) {
        this.entityEnd = entityEnd;
        return this;
    }

    public RayCastContext setBlockShape(RayCastContext.Block blockShape) {
        this.blockShape = blockShape;
        return this;
    }

    public RayCastContext setCollisionContext(CollisionContext collisionContext) {
        this.collisionContext = collisionContext;
        return this;
    }

    public Vec3 getStartPos() {
        return startPos;
    }

    public Vec3 getEndPos() {
        return endPos;
    }

    public Predicate<BlockPos> getBlockPosFilter() {
        return blockPosFilter;
    }

    public Predicate<FluidState> getFluidFilter() {
        return fluidFilter;
    }

    public Predicate<Entity> getEntityFilter() {
        return entityFilter;
    }

    public int getEntityCount() {
        return entityCount;
    }

    public float getEntitySize() {
        return entitySize;
    }

    public boolean getEntityEnd() {
        return entityEnd;
    }

    public RayCastContext.Block getBlockShape() {
        return blockShape;
    }

    public CollisionContext getCollisionContext() {
        return collisionContext;
    }

    public VoxelShape getBlockShape(BlockState blockState, BlockGetter level, BlockPos pos) {
        return blockShape.get(blockState, level, pos, collisionContext);
    }

    public VoxelShape getFluidShape(FluidState state, BlockGetter level, BlockPos pos) {
        return fluidFilter.test(state) ? state.getShape(level, pos) : Shapes.empty();
    }

    public static class Block implements RayCastContext.ShapeGetter {
        public static RayCastContext.Block COLLIDER = new RayCastContext.Block(BlockBehaviour.BlockStateBase::getCollisionShape);
        public static RayCastContext.Block OUTLINE = new RayCastContext.Block(BlockBehaviour.BlockStateBase::getShape);
        public static RayCastContext.Block VISUAL = new RayCastContext.Block(BlockBehaviour.BlockStateBase::getVisualShape);

        public final RayCastContext.ShapeGetter shapeGetter;

        public Block(RayCastContext.ShapeGetter shapeGetter) {
            this.shapeGetter = shapeGetter;
        }

        public VoxelShape get(BlockState state, BlockGetter block, BlockPos pos, CollisionContext collisionContext) {
            return this.shapeGetter.get(state, block, pos, collisionContext);
        }
    }

    public static class Fluid {
        public static Predicate<FluidState> NONE = (f) -> false;
        public static Predicate<FluidState> SOURCE_ONLY = (FluidState::isSource);
        public static Predicate<FluidState> ANY = (f) -> !f.isEmpty();
        public static Predicate<FluidState> WATER = (f) -> f.is(FluidTags.WATER);
    }

    public interface ShapeGetter {
        VoxelShape get(BlockState state, BlockGetter block, BlockPos pos, CollisionContext collisionContext);
    }
}
