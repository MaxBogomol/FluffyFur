package mod.maxbogomol.fluffy_fur.common.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;

public abstract class ServerNBTPacket extends ServerPacket {
    protected CompoundTag nbt;

    public ServerNBTPacket(CompoundTag data) {
        this.nbt = data;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeNbt(nbt);
    }

    public static <T extends ServerNBTPacket> T decode(ServerNBTPacket.PacketProvider<T> provider, FriendlyByteBuf buf) {
        CompoundTag nbt = buf.readNbt();
        return provider.getPacket(nbt);
    }

    public interface PacketProvider<T extends ServerNBTPacket> {
        T getPacket(CompoundTag nbt);
    }
}
