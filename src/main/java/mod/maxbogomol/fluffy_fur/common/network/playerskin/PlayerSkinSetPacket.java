package mod.maxbogomol.fluffy_fur.common.network.playerskin;

import mod.maxbogomol.fluffy_fur.common.playerskin.PlayerSkinHandler;
import mod.maxbogomol.fluffy_fur.common.network.FluffyFurPacketHandler;
import mod.maxbogomol.fluffy_fur.common.network.ServerPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class PlayerSkinSetPacket extends ServerPacket {
    private final String skin;

    public PlayerSkinSetPacket(String skin) {
        this.skin = skin;
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        PlayerSkinHandler.setSkin(player, PlayerSkinHandler.getSkin(skin));
        for (ServerPlayer serverPlayer : player.getServer().getPlayerList().getPlayers()) {
            FluffyFurPacketHandler.sendTo(serverPlayer, new PlayerSkinUpdatePacket(player));
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, PlayerSkinSetPacket.class, PlayerSkinSetPacket::encode, PlayerSkinSetPacket::decode, PlayerSkinSetPacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(skin);
    }

    public static PlayerSkinSetPacket decode(FriendlyByteBuf buf) {
        return new PlayerSkinSetPacket(buf.readUtf());
    }
}
