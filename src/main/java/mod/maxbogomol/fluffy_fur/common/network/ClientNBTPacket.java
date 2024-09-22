package mod.maxbogomol.fluffy_fur.common.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;

public abstract class ClientNBTPacket extends ClientPacket {
    protected CompoundTag nbt;

    public ClientNBTPacket(CompoundTag data) {
        this.nbt = data;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeNbt(nbt);
    }

    public static <T extends ClientNBTPacket> T decode(ClientNBTPacket.PacketProvider<T> provider, FriendlyByteBuf buf) {
        CompoundTag nbt = buf.readNbt();
        return provider.getPacket(nbt);
    }

    public interface PacketProvider<T extends ClientNBTPacket> {
        T getPacket(CompoundTag nbt);
    }
}
