package mod.maxbogomol.fluffy_fur.common.network.spectator;

import mod.maxbogomol.fluffy_fur.common.network.ServerPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class TeleportToDimensionSpawnPointPacket extends ServerPacket {

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        if (player != null) {
            BlockPos blockPos = player.level().getSharedSpawnPos();
            player.teleportTo(blockPos.getX() + 0.5f, blockPos.getY(), blockPos.getZ() + 0.5f);
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, TeleportToDimensionSpawnPointPacket.class, TeleportToDimensionSpawnPointPacket::encode, TeleportToDimensionSpawnPointPacket::decode, TeleportToDimensionSpawnPointPacket::handle);
    }

    public static TeleportToDimensionSpawnPointPacket decode(FriendlyByteBuf buf) {
        return new TeleportToDimensionSpawnPointPacket();
    }
}
