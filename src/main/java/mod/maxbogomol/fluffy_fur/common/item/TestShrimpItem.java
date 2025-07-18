package mod.maxbogomol.fluffy_fur.common.item;

import mod.maxbogomol.fluffy_fur.common.network.FluffyFurPacketHandler;
import mod.maxbogomol.fluffy_fur.common.network.item.TestShrimpPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurFonts;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

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
                nbt.putInt("mode", (mode + 1) % getModes());
                mode = nbt.getInt("mode");
                player.displayClientMessage(getModeComponent(mode), true);
            } else {
                FluffyFurPacketHandler.sendToTracking(level, player.blockPosition(), new TestShrimpPacket(player.getEyePosition(), player.getLookAngle(), mode, player.getUUID()));
            }
        }

        return InteractionResultHolder.success(stack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flags) {
        list.add(Component.translatable("lore.fluffy_fur.shrimp").withStyle(Style.EMPTY.withColor(ColorUtil.packColor(255, 221, 46, 68)).withFont(FluffyFurFonts.FISHII_LOCATION)));
        list.add(Component.translatable("lore.fluffy_fur.fishes").withStyle(Style.EMPTY.withColor(5592575).withFont(FluffyFurFonts.FISHII_LOCATION)));

        CompoundTag nbt = stack.getOrCreateTag();
        if (!nbt.contains("mode")) {
            nbt.putInt("mode", 0);
        }

        int mode = nbt.getInt("mode");

        list.add(getModeComponent(mode));
    }

    public int getModes() {
        return 27;
    }

    public Component getModeComponent(int mode) {
        return Component.literal(mode + 1 + "/" + getModes());
    }
}
