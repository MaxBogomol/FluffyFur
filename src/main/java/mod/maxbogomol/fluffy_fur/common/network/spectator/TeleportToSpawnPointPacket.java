package mod.maxbogomol.fluffy_fur.common.network.spectator;

import mod.maxbogomol.fluffy_fur.common.network.ServerPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;
import java.util.function.Supplier;

public class TeleportToSpawnPointPacket extends ServerPacket {

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        if (player != null) {
            BlockPos blockPos = player.getRespawnPosition();
            float f = player.getRespawnAngle();
            boolean flag = player.isRespawnForced();

            ServerLevel serverlevel = player.server.getLevel(player.getRespawnDimension());
            Optional<Vec3> optional;
            if (serverlevel != null && blockPos != null) {
                optional = Player.findRespawnPositionAndUseSpawnBlock(serverlevel, blockPos, f, flag, true);
            } else {
                optional = Optional.empty();
            }
            ServerLevel serverLevel1 = serverlevel != null && optional.isPresent() ? serverlevel : player.server.overworld();
            if (optional.isEmpty()) blockPos = serverLevel1.getSharedSpawnPos();
            if (serverlevel != null) player.teleportTo(serverLevel1, blockPos.getX() + 0.5f, blockPos.getY(), blockPos.getZ() + 0.5f, 0, 0);
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, TeleportToSpawnPointPacket.class, TeleportToSpawnPointPacket::encode, TeleportToSpawnPointPacket::decode, TeleportToSpawnPointPacket::handle);
    }

    public static TeleportToSpawnPointPacket decode(FriendlyByteBuf buf) {
        return new TeleportToSpawnPointPacket();
    }
}
