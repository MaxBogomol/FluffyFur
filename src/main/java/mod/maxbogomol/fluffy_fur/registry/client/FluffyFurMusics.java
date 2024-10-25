package mod.maxbogomol.fluffy_fur.registry.client;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.FluffyFurClient;
import mod.maxbogomol.fluffy_fur.client.sound.MusicHandler;
import mod.maxbogomol.fluffy_fur.client.sound.MusicModifier;
import mod.maxbogomol.fluffy_fur.registry.common.FluffyFurSounds;
import net.minecraft.sounds.Music;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class FluffyFurMusics {
    public static final Music FLUFFY = new Music(FluffyFurSounds.MUSIC_DISC_FLUFFY.getHolder().get(), 20, 600, true);

    @Mod.EventBusSubscriber(modid = FluffyFur.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void registerMusicModifiers(FMLClientSetupEvent event) {
            MusicHandler.register(new MusicModifier.Panorama(FLUFFY, FluffyFurClient.FLUFFY_PANORAMA));
        }
    }
}
