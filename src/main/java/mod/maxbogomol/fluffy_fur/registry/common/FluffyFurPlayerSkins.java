package mod.maxbogomol.fluffy_fur.registry.common;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.common.playerskin.*;

public class FluffyFurPlayerSkins {
    public static PlayerSkin EMPTY_SKIN = new PlayerSkin(FluffyFur.MOD_ID + ":empty");

    public static PlayerSkin MAXBOGOMOL_SKIN = new FoxPlayerSkin(FluffyFur.MOD_ID + ":maxbogomol")
            .setSkinTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "maxbogomol/skin"))
            .setSkinBlinkTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "maxbogomol/skin_blink"))
            .setEarsTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "maxbogomol/ears"))
            .setTailTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "maxbogomol/tail"))
            .setSlim(true);
    public static PlayerSkin FURRYFOXES_SKIN = new FoxPlayerSkin(FluffyFur.MOD_ID + ":furryfoxes")
            .setSkinTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "furryfoxes/skin"))
            .setSkinBlinkTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "furryfoxes/skin_blink"))
            .setEarsTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "furryfoxes/ears"))
            .setTailTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "furryfoxes/tail"))
            .setSlim(true);
    public static PlayerSkin BOYKISSER_SKIN = new CatPlayerSkin(FluffyFur.MOD_ID + ":boykisser")
            .setSkinTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "boykisser/skin"))
            .setSkinBlinkTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "boykisser/skin_blink"))
            .setEarsTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "boykisser/ears"))
            .setTailTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "boykisser/tail"))
            .setSlim(true);
    public static PlayerSkin NANACHI_SKIN = new NanachiPlayerSkin(FluffyFur.MOD_ID + ":nanachi")
            .setSkinTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "nanachi/skin"))
            .setSkinBlinkTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "nanachi/skin_blink"))
            .setEarsTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "nanachi/ears"))
            .setTailTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "nanachi/tail"))
            .setSlim(true);

    public static PlayerSkinCape EMPTY_CAPE = new PlayerSkinCape(FluffyFur.MOD_ID + ":empty");
    public static PlayerSkinCape FLUFFY_CAPE = new PlayerSkinCape(FluffyFur.MOD_ID + ":fluffy")
            .setTexture(PlayerSkinCape.getCapeLocation(FluffyFur.MOD_ID, "fluffy"));

    public static PlayerSkinEffect EMPTY_EFFECT = new PlayerSkinEffect(FluffyFur.MOD_ID + ":empty");
    public static PlayerSkinEffect PINK_HEARTS_EFFECT = new PinkHeartsPlayerSkinEffect(FluffyFur.MOD_ID + ":pink_hearts");

    public static void register() {
        PlayerSkinHandler.register(EMPTY_SKIN);
        PlayerSkinHandler.register(MAXBOGOMOL_SKIN);
        PlayerSkinHandler.register(FURRYFOXES_SKIN);
        PlayerSkinHandler.register(BOYKISSER_SKIN);
        PlayerSkinHandler.register(NANACHI_SKIN);

        PlayerSkinHandler.register(EMPTY_CAPE);
        PlayerSkinHandler.register(FLUFFY_CAPE);

        PlayerSkinHandler.register(EMPTY_EFFECT);
        PlayerSkinHandler.register(PINK_HEARTS_EFFECT);
    }
}
