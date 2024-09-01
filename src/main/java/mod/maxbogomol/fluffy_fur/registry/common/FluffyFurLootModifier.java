package mod.maxbogomol.fluffy_fur.registry.common;

import com.mojang.serialization.Codec;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.common.loot.AddItemListLootModifier;
import mod.maxbogomol.fluffy_fur.common.loot.AddItemLootModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FluffyFurLootModifier {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, FluffyFur.MOD_ID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM = LOOT_MODIFIERS.register("add_item", AddItemLootModifier.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM_LIST = LOOT_MODIFIERS.register("add_item_list", AddItemListLootModifier.CODEC);

    public static void register(IEventBus eventBus) {
        LOOT_MODIFIERS.register(eventBus);
    }
}
