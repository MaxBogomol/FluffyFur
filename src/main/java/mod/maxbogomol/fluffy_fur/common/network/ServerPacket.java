package mod.maxbogomol.fluffy_fur.common.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class ServerPacket {

    public void encode(FriendlyByteBuf buf) {}

    public final void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> execute(context));
        context.get().setPacketHandled(true);
    }

    public void execute(Supplier<NetworkEvent.Context> context) {}
}
