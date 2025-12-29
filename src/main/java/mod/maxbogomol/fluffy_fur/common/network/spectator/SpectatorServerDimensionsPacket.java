package mod.maxbogomol.fluffy_fur.common.network.spectator;

import mod.maxbogomol.fluffy_fur.common.network.FluffyFurPacketHandler;
import mod.maxbogomol.fluffy_fur.common.network.ServerPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.storage.ChunkScanAccess;
import net.minecraft.world.level.chunk.storage.IOWorker;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.nio.file.Path;
import java.util.function.Supplier;

public class SpectatorServerDimensionsPacket extends ServerPacket {

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        if (player != null) {
            CompoundTag nbt = new CompoundTag();
            ListTag dimensions = new ListTag();
            for(ServerLevel serverLevel : player.server.getAllLevels()) {
                CompoundTag dimension = new CompoundTag();
                dimension.putString("name", serverLevel.dimension().location().toString());
                boolean save = false;
                ChunkScanAccess chunkScanAccess = serverLevel.getChunkSource().chunkMap.chunkScanner();
                if (chunkScanAccess instanceof IOWorker ioWorker) {
                    Path path = ioWorker.storage.folder;
                    save = path.toFile().exists();
                }
                dimension.putBoolean("save", save);
                dimensions.add(dimension);
            }
            nbt.put("dimensions", dimensions);
            FluffyFurPacketHandler.sendTo(player, new SpectatorClientDimensionsPacket(nbt));
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SpectatorServerDimensionsPacket.class, SpectatorServerDimensionsPacket::encode, SpectatorServerDimensionsPacket::decode, SpectatorServerDimensionsPacket::handle);
    }

    public static SpectatorServerDimensionsPacket decode(FriendlyByteBuf buf) {
        return new SpectatorServerDimensionsPacket();
    }
}
