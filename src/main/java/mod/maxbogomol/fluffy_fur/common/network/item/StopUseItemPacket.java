package mod.maxbogomol.fluffy_fur.common.network.item;

import mod.maxbogomol.fluffy_fur.common.network.ServerPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class StopUseItemPacket extends ServerPacket {

    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        if (player != null) player.stopUsingItem();
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, StopUseItemPacket.class, StopUseItemPacket::encode, StopUseItemPacket::decode, StopUseItemPacket::handle);
    }

    public static StopUseItemPacket decode(FriendlyByteBuf buf) {
        return new StopUseItemPacket();
    }
}
