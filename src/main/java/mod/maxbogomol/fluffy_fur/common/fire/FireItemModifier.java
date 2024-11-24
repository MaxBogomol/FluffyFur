package mod.maxbogomol.fluffy_fur.common.fire;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

public class FireItemModifier {

    public static final Random random = new Random();

    public boolean isCreeperInteract(Entity entity, Player player, InteractionHand hand) {
        return false;
    }

    public boolean isTntUse(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return false;
    }

    public void creeperInteract(Entity entity, Player player, InteractionHand hand) {
        SoundEvent soundevent = SoundEvents.FLINTANDSTEEL_USE;
        entity.level().playSound(player, entity.getX(), entity.getY(), entity.getZ(), soundevent, entity.getSoundSource(), 1.0F, random.nextFloat() * 0.4F + 0.8F);
    }

    public void tntUse(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        player.awardStat(Stats.ITEM_USED.get(player.getItemInHand(hand).getItem()));
    }
}
