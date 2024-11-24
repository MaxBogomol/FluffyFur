package mod.maxbogomol.fluffy_fur.mixin.common;

import mod.maxbogomol.fluffy_fur.common.fire.FireItemHandler;
import mod.maxbogomol.fluffy_fur.common.fire.FireItemModifier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TntBlock.class)
public abstract class TntBlockMixin {

    @Inject(method = "use", at = @At("RETURN"), cancellable = true)
    private void fluffy_fur$use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
        TntBlock self = (TntBlock) ((Object) this);
        for (FireItemModifier modifier : FireItemHandler.getModifiers()) {
            if (modifier.isTntUse(state, level, pos, player, hand, hit)) {
                modifier.tntUse(state, level, pos, player, hand, hit);
                self.onCaughtFire(state, level, pos, hit.getDirection(), player);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
                cir.setReturnValue(InteractionResult.sidedSuccess(level.isClientSide()));
            }
        }
    }
}
