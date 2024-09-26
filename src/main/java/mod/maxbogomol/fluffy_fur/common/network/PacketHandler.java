package mod.maxbogomol.fluffy_fur.common.network;

import com.mojang.datafixers.util.Pair;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.common.network.block.PlushHeartsPacket;
import mod.maxbogomol.fluffy_fur.common.network.playerskin.*;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public final class PacketHandler {
    private static final String PROTOCOL = "10";
    public static final SimpleChannel HANDLER = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(FluffyFur.MOD_ID,"network"),
            () -> PROTOCOL,
            PROTOCOL::equals,
            PROTOCOL::equals
    );

    public static void init() {
        int id = 0;
        PlayerSkinUpdatePacket.register(HANDLER, id++);
        PlayerSkinSetPacket.register(HANDLER, id++);
        PlayerSkinSetEffectPacket.register(HANDLER, id++);
        PlayerSkinSetCapePacket.register(HANDLER, id++);
        PlayerSkinChangeEffectPacket.register(HANDLER, id++);
        PlayerSkinChangePacket.register(HANDLER, id++);
        PlushHeartsPacket.register(HANDLER, id++);
        BloodPacket.register(HANDLER, id++);
    }

    private static final PacketDistributor<Pair<Level, BlockPos>> TRACKING_CHUNK_AND_NEAR = new PacketDistributor<>(
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

    public static void sendTo(ServerPlayer playerMP, Object toSend) {
        HANDLER.sendTo(toSend, playerMP.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendNonLocal(ServerPlayer playerMP, Object toSend) {
        if (playerMP.server.isDedicatedServer() || !playerMP.getGameProfile().getName().equals(playerMP.server.getLocalIp())) {
            sendTo(playerMP, toSend);
        }
    }

    public static void sendToTracking(Level level, BlockPos pos, Object msg) {
        HANDLER.send(TRACKING_CHUNK_AND_NEAR.with(() -> Pair.of(level, pos)), msg);
    }

    public static void sendTo(Player entity, Object msg) {
        HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)entity), msg);
    }

    public static void sendToServer(Object msg) {
        HANDLER.sendToServer(msg);
    }
}