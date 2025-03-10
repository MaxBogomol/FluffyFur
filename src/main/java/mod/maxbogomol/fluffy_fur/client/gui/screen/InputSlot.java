package mod.maxbogomol.fluffy_fur.client.gui.screen;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class InputSlot extends SlotItemHandler {

    public InputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean allowModification(Player player) {
        return true;
    }

    @Override
    public boolean mayPickup(Player player) {
        return true;
    }
}
