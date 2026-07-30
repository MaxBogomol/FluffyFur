package mod.maxbogomol.fluffy_fur.registry.client;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.string.KeyMappingStringReplacerInstance;
import mod.maxbogomol.fluffy_fur.client.string.StringReplacerHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class FluffyFurStringReplacers {
    public static KeyMappingStringReplacerInstance UP = new KeyMappingStringReplacerInstance("minecraft:up", Minecraft.getInstance().options.keyUp);
    public static KeyMappingStringReplacerInstance DOWN = new KeyMappingStringReplacerInstance("minecraft:down", Minecraft.getInstance().options.keyDown);
    public static KeyMappingStringReplacerInstance LEFT = new KeyMappingStringReplacerInstance("minecraft:left", Minecraft.getInstance().options.keyLeft);
    public static KeyMappingStringReplacerInstance RIGHT = new KeyMappingStringReplacerInstance("minecraft:right", Minecraft.getInstance().options.keyRight);
    public static KeyMappingStringReplacerInstance SNEAK = new KeyMappingStringReplacerInstance("minecraft:sneak", Minecraft.getInstance().options.keyShift);
    public static KeyMappingStringReplacerInstance SPRINT = new KeyMappingStringReplacerInstance("minecraft:sprint", Minecraft.getInstance().options.keySprint);
    public static KeyMappingStringReplacerInstance INVENTORY = new KeyMappingStringReplacerInstance("minecraft:inventory", Minecraft.getInstance().options.keyInventory);
    public static KeyMappingStringReplacerInstance SWAP_OFF_HAND = new KeyMappingStringReplacerInstance("minecraft:swapOffhand", Minecraft.getInstance().options.keySwapOffhand);
    public static KeyMappingStringReplacerInstance DROP = new KeyMappingStringReplacerInstance("minecraft:drop", Minecraft.getInstance().options.keyDrop);
    public static KeyMappingStringReplacerInstance USE = new KeyMappingStringReplacerInstance("minecraft:use", Minecraft.getInstance().options.keyUse);
    public static KeyMappingStringReplacerInstance ATTACK = new KeyMappingStringReplacerInstance("minecraft:attack", Minecraft.getInstance().options.keyAttack);
    public static KeyMappingStringReplacerInstance PICK_ITEM = new KeyMappingStringReplacerInstance("minecraft:pickItem", Minecraft.getInstance().options.keyPickItem);

    public static KeyMappingStringReplacerInstance FLUFFY_FUR_MENU = new KeyMappingStringReplacerInstance(FluffyFur.MOD_ID+":fluffyFurMenu", FluffyFurKeyMappings.FLUFFY_FUR_MENU);
    public static KeyMappingStringReplacerInstance SKIN_MENU = new KeyMappingStringReplacerInstance(FluffyFur.MOD_ID+":skinMenu", FluffyFurKeyMappings.SKIN_MENU);

    @Mod.EventBusSubscriber(modid = FluffyFur.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void registerStringReplacers(FMLClientSetupEvent event) {
            StringReplacerHandler.register(UP);
            StringReplacerHandler.register(DOWN);
            StringReplacerHandler.register(LEFT);
            StringReplacerHandler.register(RIGHT);
            StringReplacerHandler.register(SNEAK);
            StringReplacerHandler.register(SPRINT);
            StringReplacerHandler.register(INVENTORY);
            StringReplacerHandler.register(SWAP_OFF_HAND);
            StringReplacerHandler.register(DROP);
            StringReplacerHandler.register(USE);
            StringReplacerHandler.register(ATTACK);
            StringReplacerHandler.register(PICK_ITEM);

            StringReplacerHandler.register(FLUFFY_FUR_MENU);
            StringReplacerHandler.register(SKIN_MENU);
        }
    }
}
