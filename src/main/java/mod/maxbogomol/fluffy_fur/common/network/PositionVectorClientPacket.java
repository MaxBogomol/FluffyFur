package mod.maxbogomol.fluffy_fur.common.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

public abstract class PositionVectorClientPacket extends ClientPacket {
    protected final double x;
    protected final double y;
    protected final double z;
    protected final double vx;
    protected final double vy;
    protected final double vz;

    public PositionVectorClientPacket(double x, double y, double z, double vx, double vy, double vz) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
    }

    public PositionVectorClientPacket(BlockPos pos, Vec3 vec) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.vx = vec.x();
        this.vy = vec.y();
        this.vz = vec.z();
    }

    public PositionVectorClientPacket(Vec3 vec, Vec3 vel) {
        this.x = vec.x();
        this.y = vec.y();
        this.z = vec.z();
        this.vx = vel.x();
        this.vy = vel.y();
        this.vz = vel.z();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeDouble(vx);
        buf.writeDouble(vy);
        buf.writeDouble(vz);
    }

    public static <T extends PositionVectorClientPacket> T decode(PacketProvider<T> provider, FriendlyByteBuf buf) {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        double vx = buf.readDouble();
        double vy = buf.readDouble();
        double vz = buf.readDouble();
        return provider.getPacket(x, y, z, vx, vy, vz);
    }

    public interface PacketProvider<T extends PositionVectorClientPacket> {
        T getPacket(double x, double y, double z, double vx, double vy, double vz);
    }
}
