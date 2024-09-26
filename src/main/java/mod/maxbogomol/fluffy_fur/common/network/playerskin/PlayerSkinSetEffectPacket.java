package mod.maxbogomol.fluffy_fur.common.network.playerskin;

import mod.maxbogomol.fluffy_fur.client.playerskin.PlayerSkinHandler;
import mod.maxbogomol.fluffy_fur.common.network.PacketHandler;
import mod.maxbogomol.fluffy_fur.common.network.ServerPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class PlayerSkinSetEffectPacket extends ServerPacket {
    private final String effect;

    public PlayerSkinSetEffectPacket(String effectId) {
        this.effect = effectId;
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        PlayerSkinHandler.setSkinEffect(player, effect);
        for (ServerPlayer serverPlayer : player.getServer().getPlayerList().getPlayers()) {
            PacketHandler.sendTo(serverPlayer, new PlayerSkinUpdatePacket(player));
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, PlayerSkinSetEffectPacket.class, PlayerSkinSetEffectPacket::encode, PlayerSkinSetEffectPacket::decode, PlayerSkinSetEffectPacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(effect);
    }

    public static PlayerSkinSetEffectPacket decode(FriendlyByteBuf buf) {
        return new PlayerSkinSetEffectPacket(buf.readUtf());
    }
}
