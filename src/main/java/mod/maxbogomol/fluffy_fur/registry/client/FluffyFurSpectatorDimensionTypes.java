package mod.maxbogomol.fluffy_fur.registry.client;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.spectator.SpectatorDimensionHandler;
import mod.maxbogomol.fluffy_fur.client.spectator.SpectatorDimensionType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class FluffyFurSpectatorDimensionTypes {
    public static SpectatorDimensionType OVERWORLD = new SpectatorDimensionType(Level.OVERWORLD, new ResourceLocation(FluffyFur.MOD_ID, "textures/spectator_dimension/overworld.png"));
    public static SpectatorDimensionType NETHER = new SpectatorDimensionType(Level.NETHER, new ResourceLocation(FluffyFur.MOD_ID, "textures/spectator_dimension/the_nether.png"));
    public static SpectatorDimensionType END = new SpectatorDimensionType(Level.END, new ResourceLocation(FluffyFur.MOD_ID, "textures/spectator_dimension/the_end.png"));
    public static SpectatorDimensionType UNKNOWN = new SpectatorDimensionType(new ResourceLocation(FluffyFur.MOD_ID, "textures/spectator_dimension/unknown.png"));
    public static SpectatorDimensionType OTHER = new SpectatorDimensionType();

    @Mod.EventBusSubscriber(modid = FluffyFur.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void registerSpectatorDimensionTypes(FMLClientSetupEvent event) {
            SpectatorDimensionHandler.register(OVERWORLD);
            SpectatorDimensionHandler.register(NETHER);
            SpectatorDimensionHandler.register(END);
        }
    }
}
