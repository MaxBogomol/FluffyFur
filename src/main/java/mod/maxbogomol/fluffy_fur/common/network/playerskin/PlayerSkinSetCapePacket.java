package mod.maxbogomol.fluffy_fur.common.network.playerskin;

import mod.maxbogomol.fluffy_fur.client.playerskin.PlayerSkinHandler;
import mod.maxbogomol.fluffy_fur.common.network.FluffyFurPacketHandler;
import mod.maxbogomol.fluffy_fur.common.network.ServerPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class PlayerSkinSetCapePacket extends ServerPacket {
    private final String cape;

    public PlayerSkinSetCapePacket(String capeId) {
        this.cape = capeId;
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        PlayerSkinHandler.setSkinCape(player, cape);
        for (ServerPlayer serverPlayer : player.getServer().getPlayerList().getPlayers()) {
            FluffyFurPacketHandler.sendTo(serverPlayer, new PlayerSkinUpdatePacket(player));
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, PlayerSkinSetCapePacket.class, PlayerSkinSetCapePacket::encode, PlayerSkinSetCapePacket::decode, PlayerSkinSetCapePacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(cape);
    }

    public static PlayerSkinSetCapePacket decode(FriendlyByteBuf buf) {
        return new PlayerSkinSetCapePacket(buf.readUtf());
    }
}
