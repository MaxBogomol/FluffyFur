package mod.maxbogomol.fluffy_fur.client.playerskin;

import mod.maxbogomol.fluffy_fur.client.model.playerskin.PlayerSkinData;
import mod.maxbogomol.fluffy_fur.common.capability.IPlayerSkin;
import mod.maxbogomol.fluffy_fur.common.network.PacketHandler;
import mod.maxbogomol.fluffy_fur.common.network.PlayerSkinSetPacket;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class PlayerSkinHandler {
    public static Map<String, PlayerSkin> skins = new HashMap<>();

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
}
