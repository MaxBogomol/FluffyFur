package mod.maxbogomol.fluffy_fur.common.network.playerskin;

import mod.maxbogomol.fluffy_fur.client.playerskin.PlayerSkinHandler;
import mod.maxbogomol.fluffy_fur.common.network.PacketHandler;
import mod.maxbogomol.fluffy_fur.common.network.ServerPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class PlayerSkinEffectSetPacket extends ServerPacket {
    private final Component effect;

    public PlayerSkinEffectSetPacket(String effectId) {
        this.effect = Component.literal(effectId);
    }

    public PlayerSkinEffectSetPacket(Component effectId) {
        this.effect = effectId;
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        PlayerSkinHandler.setSkinEffect(player, effect.getString());
        for (ServerPlayer serverPlayer : player.getServer().getPlayerList().getPlayers()) {
            PacketHandler.sendTo(serverPlayer, new PlayerSkinUpdatePacket(player));
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, PlayerSkinEffectSetPacket.class, PlayerSkinEffectSetPacket::encode, PlayerSkinEffectSetPacket::decode, PlayerSkinEffectSetPacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeComponent(effect);
    }

    public static PlayerSkinEffectSetPacket decode(FriendlyByteBuf buf) {
        return new PlayerSkinEffectSetPacket(buf.readComponent());
    }
}
