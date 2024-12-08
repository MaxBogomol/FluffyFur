package mod.maxbogomol.fluffy_fur.common.item;

import mod.maxbogomol.fluffy_fur.common.network.FluffyFurPacketHandler;
import mod.maxbogomol.fluffy_fur.common.network.item.TestShrimpPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TestShrimpItem extends Item {

    public TestShrimpItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag nbt = stack.getOrCreateTag();

        if (!level.isClientSide()) {
            if (!nbt.contains("mode")) {
                nbt.putInt("mode", 0);
            }

            int mode = nbt.getInt("mode");

            if (player.isShiftKeyDown()) {
                nbt.putInt("mode", (mode + 1) % 29);
                mode = nbt.getInt("mode");
            }
            FluffyFurPacketHandler.sendToTracking(level, player.blockPosition(), new TestShrimpPacket(player.getEyePosition(), player.getLookAngle(), mode));
        }

        return InteractionResultHolder.success(stack);
    }
}
