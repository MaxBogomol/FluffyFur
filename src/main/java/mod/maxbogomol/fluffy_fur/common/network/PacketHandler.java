package mod.maxbogomol.fluffy_fur.common.network;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public abstract class PacketHandler {

    public static final PacketDistributor<Pair<Level, BlockPos>> TRACKING_CHUNK_AND_NEAR = new PacketDistributor<>(
            (_d, pairSupplier) -> {
                var pair = pairSupplier.get();
                var level = pair.getFirst();
                var blockpos = pair.getSecond();
                var chunkpos = new ChunkPos(blockpos);
                return packet -> {
                    var players = ((ServerChunkCache) level.getChunkSource()).chunkMap
                            .getPlayers(chunkpos, false);
                    for (var player : players) {
                        if (player.distanceToSqr(blockpos.getX(), blockpos.getY(), blockpos.getZ()) < 64 * 64) {
                            player.connection.send(packet);
                        }
                    }
                };
            },
            NetworkDirection.PLAY_TO_CLIENT
    );

    public static void sendTo(SimpleChannel channel, ServerPlayer playerMP, Object toSend) {
        channel.sendTo(toSend, playerMP.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendNonLocal(SimpleChannel channel, ServerPlayer playerMP, Object toSend) {
        if (playerMP.server.isDedicatedServer() || !playerMP.getGameProfile().getName().equals(playerMP.server.getLocalIp())) {
            sendTo(channel, playerMP, toSend);
        }
    }

    public static void sendToTracking(SimpleChannel channel, Level level, BlockPos pos, Object msg) {
        channel.send(TRACKING_CHUNK_AND_NEAR.with(() -> Pair.of(level, pos)), msg);
    }

    public static void sendTo(SimpleChannel channel, Player entity, Object msg) {
        channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) entity), msg);
    }

    public static void sendToServer(SimpleChannel channel, Object msg) {
        channel.sendToServer(msg);
    }
}