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

    @Mod.EventBusSubscriber(modid = FluffyFur.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void registerStringReplacers(FMLClientSetupEvent event) {
            StringReplacerHandler.register(UP);
            StringReplacerHandler.register(DOWN);
            StringReplacerHandler.register(LEFT);
            StringReplacerHandler.register(RIGHT);
            StringReplacerHandler.register(SNEAK);
        }
    }
}
