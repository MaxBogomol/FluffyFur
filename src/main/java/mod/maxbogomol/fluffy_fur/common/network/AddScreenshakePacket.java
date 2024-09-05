package mod.maxbogomol.fluffy_fur.common.network;

import mod.maxbogomol.fluffy_fur.client.event.ScreenshakeHandler;
import mod.maxbogomol.fluffy_fur.common.network.playerskin.PlayerSkinChangeEffectPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class AddScreenshakePacket extends ClientPacket {
    private final float shake;

    public AddScreenshakePacket(float shake) {
        this.shake = shake;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ScreenshakeHandler.addScreenshake(shake);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, AddScreenshakePacket.class, AddScreenshakePacket::encode, AddScreenshakePacket::decode, AddScreenshakePacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(shake);
    }

    public static AddScreenshakePacket decode(FriendlyByteBuf buf) {
        return new AddScreenshakePacket(buf.readFloat());
    }
}
