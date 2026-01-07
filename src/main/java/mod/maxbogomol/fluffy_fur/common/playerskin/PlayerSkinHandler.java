package mod.maxbogomol.fluffy_fur.common.playerskin;

import mod.maxbogomol.fluffy_fur.client.model.playerskin.PlayerSkinData;
import mod.maxbogomol.fluffy_fur.common.capability.IPlayerSkin;
import mod.maxbogomol.fluffy_fur.common.network.FluffyFurPacketHandler;
import mod.maxbogomol.fluffy_fur.common.network.playerskin.PlayerSkinSetCapePacket;
import mod.maxbogomol.fluffy_fur.common.network.playerskin.PlayerSkinSetEffectPacket;
import mod.maxbogomol.fluffy_fur.common.network.playerskin.PlayerSkinSetPacket;
import mod.maxbogomol.fluffy_fur.registry.common.FluffyFurAttributes;
import mod.maxbogomol.fluffy_fur.registry.common.FluffyFurPlayerSkins;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class PlayerSkinHandler {
    public static Map<String, PlayerSkin> skins = new HashMap<>();
    public static Map<String, PlayerSkinEffect> skinEffects = new HashMap<>();
    public static Map<String, PlayerSkinCape> skinCapes = new HashMap<>();

    public static void register(PlayerSkin skin) {
        skins.put(skin.getId(), skin);
    }

    public static void register(PlayerSkinCape cape) {
        skinCapes.put(cape.getId(), cape);
    }

    public static void register(PlayerSkinEffect effect) {
        skinEffects.put(effect.getId(), effect);
    }

    public static PlayerSkin getSkin(String id) {
        return skins.get(id);
    }

    public static List<PlayerSkin> getSkins() {
        return new ArrayList<>(skins.values());
    }

    public static boolean isEmptySkin(PlayerSkin skin) {
        return skin == null || skin == FluffyFurPlayerSkins.EMPTY_SKIN;
    }

    public static PlayerSkinEffect getSkinEffect(String id) {
        return skinEffects.get(id);
    }

    public static List<PlayerSkinEffect> getSkinEffects() {
        return new ArrayList<>(skinEffects.values());
    }

    public static boolean isEmptySkinEffect(PlayerSkinEffect skin) {
        return skin == null || skin == FluffyFurPlayerSkins.EMPTY_EFFECT;
    }

    public static PlayerSkinCape getSkinCape(String id) {
        return skinCapes.get(id);
    }

    public static List<PlayerSkinCape> getSkinCapes() {
        return new ArrayList<>(skinCapes.values());
    }

    public static boolean isEmptySkinCape(PlayerSkinCape skin) {
        return skin == null || skin == FluffyFurPlayerSkins.EMPTY_CAPE;
    }

    public static PlayerSkin getSkin(Player player) {
        AtomicReference<PlayerSkin> skin  = new AtomicReference<>();
        player.getCapability(IPlayerSkin.INSTANCE, null).ifPresent((s) -> {
            skin.set(s.getSkin());
        });

        return skin.get();
    }

    public static PlayerSkinData getSkinData(Player player) {
        AtomicReference<PlayerSkinData> data  = new AtomicReference<>();
        player.getCapability(IPlayerSkin.INSTANCE, null).ifPresent((s) -> {
            data.set(s.getSkinData());
        });
        return data.get();
    }

    public static void setSkinData(Player player, PlayerSkinData data) {
        player.getCapability(IPlayerSkin.INSTANCE, null).ifPresent((s) -> {
            s.setSkinData(data);
        });
    }

    public static void setSkin(Player player, PlayerSkin skin) {
        player.getCapability(IPlayerSkin.INSTANCE, null).ifPresent((s) -> {
            s.setSkin(skin);
        });
        setSkinData(player, new PlayerSkinData());
    }

    public static void setSkinPacket(PlayerSkin skin) {
        FluffyFurPacketHandler.sendToServer(new PlayerSkinSetPacket(skin.getId()));
    }

    public static PlayerSkinEffect getSkinEffect(Player player) {
        AtomicReference<PlayerSkinEffect> effect  = new AtomicReference<>();
        player.getCapability(IPlayerSkin.INSTANCE, null).ifPresent((s) -> {
            effect.set(s.getSkinEffect());
        });

        return effect.get();
    }

    public static void setSkinEffect(Player player, PlayerSkinEffect effect) {
        player.getCapability(IPlayerSkin.INSTANCE, null).ifPresent((s) -> {
            s.setSkinEffect(effect);
        });
        setSkinData(player, new PlayerSkinData());
    }

    public static void setSkinEffectPacket(PlayerSkinEffect effect) {
        FluffyFurPacketHandler.sendToServer(new PlayerSkinSetEffectPacket(effect.getId()));
    }

    public static PlayerSkinCape getSkinCape(Player player) {
        AtomicReference<PlayerSkinCape> cape  = new AtomicReference<>();
        player.getCapability(IPlayerSkin.INSTANCE, null).ifPresent((s) -> {
            cape.set(s.getSkinCape());
        });

        return cape.get();
    }

    public static void setSkinCape(Player player, PlayerSkinCape cape) {
        player.getCapability(IPlayerSkin.INSTANCE, null).ifPresent((s) -> {
            s.setSkinCape(cape);
        });
        setSkinData(player, new PlayerSkinData());
    }

    public static void setSkinCapePacket(PlayerSkinCape cape) {
        FluffyFurPacketHandler.sendToServer(new PlayerSkinSetCapePacket(cape.getId()));
    }

    public static void skinTick(Player player) {
        PlayerSkin skin = getSkin(player);
        if (!isEmptySkin(skin)) {
            skin.tick(player);
        }
        PlayerSkinEffect skinEffect = getSkinEffect(player);
        if (!isEmptySkinEffect(skinEffect)) {
            skinEffect.tick(player);
        }
    }

    public static float getPlayerSizeScale(Player player) {
        if (player != null) {
            AttributeInstance attribute = player.getAttribute(FluffyFurAttributes.SIZE_SCALE.get());
            if (attribute != null) return (float) attribute.getValue();
        }
        return 1;
    }
}
