package mod.maxbogomol.fluffy_fur.common.item;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.particle.Particles;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class TestShrimpItem extends Item {
    public int fuel;

    public TestShrimpItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (level.isClientSide()) {
            Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(1.75f));
            Particles.create(FluffyFur.WISP_PARTICLE)
                    .randomVelocity(0.035f, 0.035f, 0.035f)
                    .setAlpha(0.5f, 0).setScale(0.3f, 0)
                    .setColor(0, 0, 1, 1, 0, 0)
                    .setLifetime(60)
                    .repeat(level, pos.x(), pos.y(), pos.z(), 10);
            Particles.create(FluffyFur.SPARKLE_PARTICLE)
                    .randomVelocity(0.035f, 0.035f, 0.035f)
                    .setAlpha(0.5f, 0).setScale(0.1f, 0)
                    .setColor(0, 0, 1, 1, 0, 0)
                    .setLifetime(60)
                    .randomSpin(0.5f)
                    .repeat(level, pos.x(), pos.y(), pos.z(), 10);
        }

        return InteractionResultHolder.success(stack);
    }
}
