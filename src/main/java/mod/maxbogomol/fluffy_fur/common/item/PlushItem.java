package mod.maxbogomol.fluffy_fur.common.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class PlushItem extends BlockItem implements Equipable {

    public PlushItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public @NotNull EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }
}
