package mod.maxbogomol.fluffy_fur.registry.common;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.common.command.ItemSkinArgumentType;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FluffyFurArgumentTypes {
    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> COMMAND_ARGUMENT_TYPES = DeferredRegister.create(ForgeRegistries.COMMAND_ARGUMENT_TYPES, FluffyFur.MOD_ID);

    public static final RegistryObject<ArgumentTypeInfo<?, ?>> ITEM_SKIN = COMMAND_ARGUMENT_TYPES.register("skin", () -> ArgumentTypeInfos.registerByClass(ItemSkinArgumentType.class, SingletonArgumentInfo.contextFree(ItemSkinArgumentType::skins)));

    public static void register(IEventBus eventBus) {
        COMMAND_ARGUMENT_TYPES.register(eventBus);
    }
}
