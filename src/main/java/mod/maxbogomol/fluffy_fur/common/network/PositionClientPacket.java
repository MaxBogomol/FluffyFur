package mod.maxbogomol.fluffy_fur.common.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

public abstract class PositionClientPacket extends ClientPacket {
    protected final double x;
    protected final double y;
    protected final double z;

    public PositionClientPacket(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PositionClientPacket(BlockPos pos) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public PositionClientPacket(Vec3 vec) {
        this.x = vec.x();
        this.y = vec.y();
        this.z = vec.z();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }

    public static <T extends PositionClientPacket> T decode(PacketProvider<T> provider, FriendlyByteBuf buf) {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        return provider.getPacket(x, y, z);
    }

    public interface PacketProvider<T extends PositionClientPacket> {
        T getPacket(double x, double y, double z);
    }
}
