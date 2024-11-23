package mod.maxbogomol.fluffy_fur.client.bow;

import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class BowHandler {
    public static List<Item> bows = new ArrayList<>();

    public static void addBow(Item item) {
        bows.add(item);
    }

    public static List<Item> getBows() {
        return bows;
    }
}
