package mod.maxbogomol.fluffy_fur.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class YonKaBlockItem extends PlushItem {

    public YonKaBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flags) {
        list.add(Component.translatable("lore.fluffy_fur.normal").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
    }
}
