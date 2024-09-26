package mod.maxbogomol.fluffy_fur.client.playerskin;

import mod.maxbogomol.fluffy_fur.client.model.playerskin.PlayerSkinData;
import mod.maxbogomol.fluffy_fur.common.capability.IPlayerSkin;
import mod.maxbogomol.fluffy_fur.common.network.PacketHandler;
import mod.maxbogomol.fluffy_fur.common.network.playerskin.PlayerSkinSetCapePacket;
import mod.maxbogomol.fluffy_fur.common.network.playerskin.PlayerSkinSetEffectPacket;
import mod.maxbogomol.fluffy_fur.common.network.playerskin.PlayerSkinSetPacket;
import net.minecraft.world.entity.player.Player;

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

    public static PlayerSkin getSkin(Player player) {
        AtomicReference<String> id  = new AtomicReference<>();
        player.getCapability(IPlayerSkin.INSTANCE, null).ifPresent((s) -> {
            id.set(s.getSkin());
        });
        if (skins.containsKey(id.get())) {
            return skins.get(id.get());
        }

        return null;
    }

    public static List<PlayerSkin> getSkins() {
        return skins.values().stream().toList();
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

    public static void skinTick(Player player) {
        PlayerSkin skin = getSkin(player);
        if (skin != null) {
            skin.tick(player);
        }

        PlayerSkinEffect skinEffect = getSkinEffect(player);
        if (skinEffect != null) {
            skinEffect.tick(player);
        }
    }

    public static void setSkin(Player player, String skin) {
        if (skin != null) {
            player.getCapability(IPlayerSkin.INSTANCE, null).ifPresent((s) -> {
                s.setSkin(skin);
            });
            setSkinData(player, new PlayerSkinData());
        } else {
            player.getCapability(IPlayerSkin.INSTANCE, null).ifPresent((s) -> {
                s.setSkin("");
            });
        }
    }

    public static void setSkin(Player player, PlayerSkin skin) {
        if (skin != null) {
            setSkin(player, skin.getId());
        } else {
            setSkin(player, "");
        }
    }

    public static void setSkinPacket(PlayerSkin skin) {
        PacketHandler.sendToServer(new PlayerSkinSetPacket(skin.getId()));
    }

    public static void setSkinPacket(String skin) {
        PacketHandler.sendToServer(new PlayerSkinSetPacket(skin));
    }

    public static void register(PlayerSkinEffect effect) {
        skinEffects.put(effect.getId(), effect);
    }

    public static PlayerSkinEffect getSkinEffect(Player player) {
        AtomicReference<String> id  = new AtomicReference<>();
        player.getCapability(IPlayerSkin.INSTANCE, null).ifPresent((s) -> {
            id.set(s.getSkinEffect());
        });
        if (skinEffects.containsKey(id.get())) {
            return skinEffects.get(id.get());
        }

        return null;
    }

    public static List<PlayerSkinEffect> getSkinEffects() {
        return skinEffects.values().stream().toList();
    }

    public static void setSkinEffect(Player player, String effect) {
        if (effect != null) {
            player.getCapability(IPlayerSkin.INSTANCE, null).ifPresent((s) -> {
                s.setSkinEffect(effect);
            });
            setSkinData(player, new PlayerSkinData());
        } else {
            player.getCapability(IPlayerSkin.INSTANCE, null).ifPresent((s) -> {
                s.setSkinEffect("");
            });
        }
    }

    public static void setSkinEffect(Player player, PlayerSkinEffect effect) {
        if (effect != null) {
            setSkinEffect(player, effect.getId());
        } else {
            setSkinEffect(player, "");
        }
    }

    public static void setSkinEffectPacket(PlayerSkinEffect effect) {
        PacketHandler.sendToServer(new PlayerSkinSetEffectPacket(effect.getId()));
    }

    public static void setSkinEffectPacket(String effect) {
        PacketHandler.sendToServer(new PlayerSkinSetEffectPacket(effect));
    }

    public static PlayerSkinCape getSkinCape(Player player) {
        AtomicReference<String> id  = new AtomicReference<>();
        player.getCapability(IPlayerSkin.INSTANCE, null).ifPresent((s) -> {
            id.set(s.getSkinCape());
        });
        if (skinCapes.containsKey(id.get())) {
            return skinCapes.get(id.get());
        }

        return null;
    }

    public static List<PlayerSkinCape> getSkinCapes() {
        return skinCapes.values().stream().toList();
    }

    public static void setSkinCape(Player player, String cape) {
        if (cape != null) {
            player.getCapability(IPlayerSkin.INSTANCE, null).ifPresent((s) -> {
                s.setSkinCape(cape);
            });
            setSkinData(player, new PlayerSkinData());
        } else {
            player.getCapability(IPlayerSkin.INSTANCE, null).ifPresent((s) -> {
                s.setSkinCape("");
            });
        }
    }

    public static void setSkinCape(Player player, PlayerSkinCape cape) {
        if (cape != null) {
            setSkinCape(player, cape.getId());
        } else {
            setSkinCape(player, "");
        }
    }

    public static void setSkinCapePacket(PlayerSkinCape cape) {
        PacketHandler.sendToServer(new PlayerSkinSetCapePacket(cape.getId()));
    }

    public static void setSkinCapePacket(String cape) {
        PacketHandler.sendToServer(new PlayerSkinSetCapePacket(cape));
    }

    public static void register(PlayerSkinCape cape) {
        skinCapes.put(cape.getId(), cape);
    }
}
