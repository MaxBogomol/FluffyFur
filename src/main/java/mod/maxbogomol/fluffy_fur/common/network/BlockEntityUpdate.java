package mod.maxbogomol.fluffy_fur.common.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;

public class BlockEntityUpdate {

    public static void packet(BlockEntity blockEntity) {
        if (blockEntity != null && blockEntity.getLevel() instanceof ServerLevel) {
            Packet<?> packet = blockEntity.getUpdatePacket();
            if (packet != null) {
                BlockPos pos = blockEntity.getBlockPos();
                ((ServerChunkCache) blockEntity.getLevel().getChunkSource()).chunkMap
                        .getPlayers(new ChunkPos(pos), false)
                        .forEach(e -> e.connection.send(packet));
            }
        }
    }
}
