package mod.maxbogomol.fluffy_fur.common.network.playerskin;

import mod.maxbogomol.fluffy_fur.common.network.PacketHandler;
import mod.maxbogomol.fluffy_fur.common.network.ServerPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class PlayerSkinChangePacket extends ServerPacket {
    protected final double x;
    protected final double y;
    protected final double z;

    public PlayerSkinChangePacket(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PlayerSkinChangePacket(Vec3 vec) {
        this.x = vec.x();
        this.y = vec.y();
        this.z = vec.z();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        PacketHandler.sendToTracking(player.level(), BlockPos.containing(x, y, z), new PlayerSkinChangeEffectPacket(x, y, z));
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, PlayerSkinChangePacket.class, PlayerSkinChangePacket::encode, PlayerSkinChangePacket::decode, PlayerSkinChangePacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }

    public static PlayerSkinChangePacket decode(FriendlyByteBuf buf) {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        return new PlayerSkinChangePacket(x, y, z);
    }
}
