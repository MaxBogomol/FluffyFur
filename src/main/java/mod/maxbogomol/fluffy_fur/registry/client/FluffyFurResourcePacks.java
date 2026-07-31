package mod.maxbogomol.fluffy_fur.registry.client;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.pack.FluffyFurResourcePacksReloadListener;
import mod.maxbogomol.fluffy_fur.common.pack.PackHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class FluffyFurResourcePacks {

    @Mod.EventBusSubscriber(modid = FluffyFur.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {

        @SubscribeEvent
        public static void addPackFinders(AddPackFindersEvent event) {
            if (event.getPackType() == PackType.CLIENT_RESOURCES) {
                addPack(event, "yonkafishy");
            }
        }

        @SubscribeEvent
        public static void onRegisterClientReloadListenersEvent(RegisterClientReloadListenersEvent event) {
            event.registerReloadListener(new FluffyFurResourcePacksReloadListener());
        }
    }

    public static void addPack(AddPackFindersEvent event, String name) {
        String id = FluffyFur.MOD_ID + ":" + name;
        PackHandler.addPack(event, FluffyFur.MOD_ID, id, Component.literal(id), "resourcepacks/" + name, PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.DEFAULT);
    }
}
