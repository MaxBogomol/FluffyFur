package mod.maxbogomol.fluffy_fur.common.network;

import mod.maxbogomol.fluffy_fur.client.playerskin.PlayerSkinHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class PlayerSkinSetPacket extends ServerPacket {
    private final Component skin;

    public PlayerSkinSetPacket(String skinId) {
        this.skin = Component.literal(skinId);
    }

    public PlayerSkinSetPacket(Component skin) {
        this.skin = skin;
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        PlayerSkinHandler.setSkin(player, skin.getString());
        PacketHandler.sendToTracking(player.level(), player.getOnPos(), new PlayerSkinUpdatePacket(player));
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, PlayerSkinSetPacket.class, PlayerSkinSetPacket::encode, PlayerSkinSetPacket::decode, PlayerSkinSetPacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeComponent(skin);
    }

    public static PlayerSkinSetPacket decode(FriendlyByteBuf buf) {
        return new PlayerSkinSetPacket(buf.readComponent());
    }
}
