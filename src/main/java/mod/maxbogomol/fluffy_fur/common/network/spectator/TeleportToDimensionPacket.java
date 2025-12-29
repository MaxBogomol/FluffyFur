package mod.maxbogomol.fluffy_fur.common.network.spectator;

import mod.maxbogomol.fluffy_fur.common.network.ServerPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class TeleportToDimensionPacket extends ServerPacket {
    protected final ResourceLocation dimension;

    public TeleportToDimensionPacket(ResourceLocation dimension) {
        this.dimension = dimension;
    }

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        if (player != null) {
            ResourceKey<Level> resourceKey = ResourceKey.create(Registries.DIMENSION, dimension);
            ServerLevel serverLevel = player.server.getLevel(resourceKey);
            if (serverLevel != null) {
                BlockPos blockPos = serverLevel.getSharedSpawnPos();
                player.teleportTo(serverLevel, blockPos.getX() + 0.5f, blockPos.getY(), blockPos.getZ() + 0.5f, 0, 0);
            }
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, TeleportToDimensionPacket.class, TeleportToDimensionPacket::encode, TeleportToDimensionPacket::decode, TeleportToDimensionPacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceLocation(dimension);
    }

    public static TeleportToDimensionPacket decode(FriendlyByteBuf buf) {
        return new TeleportToDimensionPacket(buf.readResourceLocation());
    }
}
