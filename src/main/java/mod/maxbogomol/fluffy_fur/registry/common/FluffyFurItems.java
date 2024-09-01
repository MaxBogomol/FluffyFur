package mod.maxbogomol.fluffy_fur.registry.common;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.common.item.TestShrimpItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FluffyFurItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FluffyFur.MOD_ID);

    public static final RegistryObject<Item> TEST_SHRIMP = ITEMS.register("test_shrimp", () -> new TestShrimpItem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> THING = ITEMS.register("thing", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static void composterItem(float chance, ItemLike item) {
        ComposterBlock.add(chance, item);
    }
}
