package mod.maxbogomol.fluffy_fur.registry.client;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.playerskin.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class FluffyFurPlayerSkins {
    public static PlayerSkin MAXBOGOMOL = new FoxPlayerSkin(FluffyFur.MOD_ID + ":maxbogomol")
            .setSkinTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "maxbogomol/skin"))
            .setSkinBlinkTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "maxbogomol/skin_blink"))
            .setEarsTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "maxbogomol/ears"))
            .setTailTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "maxbogomol/tail"))
            .setSlim(true);
    public static PlayerSkin ONIXTHECAT = new PlayerSkin(FluffyFur.MOD_ID + ":onixthecat")
            .setSkinTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "onixthecat/skin"))
            .setSkinBlinkTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "onixthecat/skin_blink"))
            .setSlim(true);
    public static PlayerSkin NANACHI = new NanachiPlayerSkin(FluffyFur.MOD_ID + ":nanachi")
            .setSkinTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "nanachi/skin"))
            .setSkinBlinkTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "nanachi/skin_blink"))
            .setEarsTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "nanachi/ears"))
            .setTailTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "nanachi/tail"))
            .setSlim(true);

    public static PlayerSkinEffect PINK_HEARTS_EFFECT = new PinkHeartsPlayerSkinEffect(FluffyFur.MOD_ID + ":pink_hearts");

    @Mod.EventBusSubscriber(modid = FluffyFur.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void registerPlayerSkins(FMLClientSetupEvent event) {
            PlayerSkinHandler.register(MAXBOGOMOL);
            PlayerSkinHandler.register(ONIXTHECAT);
            PlayerSkinHandler.register(NANACHI);

            PlayerSkinHandler.register(PINK_HEARTS_EFFECT);
        }
    }
}
