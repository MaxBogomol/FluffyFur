package mod.maxbogomol.fluffy_fur.common.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

public abstract class TwoPositionClientPacket extends ClientPacket {
    protected final double x1;
    protected final double y1;
    protected final double z1;
    protected final double x2;
    protected final double y2;
    protected final double z2;

    public TwoPositionClientPacket(double x1, double y1, double z1, double x2, double y2, double z2) {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
    }

    public TwoPositionClientPacket(BlockPos pos, double x2, double y2, double z2) {
        this.x1 = pos.getX();
        this.y1 = pos.getY();
        this.z1 = pos.getZ();
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
    }

    public TwoPositionClientPacket(BlockPos pos, Vec3 vec) {
        this.x1 = pos.getX();
        this.y1 = pos.getY();
        this.z1 = pos.getZ();
        this.x2 = vec.x();
        this.y2 = vec.y();
        this.z2 = vec.z();
    }

    public TwoPositionClientPacket(Vec3 vec1, Vec3 vec2) {
        this.x1 = vec1.x();
        this.y1 = vec1.y();
        this.z1 = vec1.z();
        this.x2 = vec2.x();
        this.y2 = vec2.y();
        this.z2 = vec2.z();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(x1);
        buf.writeDouble(y1);
        buf.writeDouble(z1);
        buf.writeDouble(x2);
        buf.writeDouble(y2);
        buf.writeDouble(z2);
    }

    public static <T extends TwoPositionClientPacket> T decode(PacketProvider<T> provider, FriendlyByteBuf buf) {
        double x1 = buf.readDouble();
        double y1 = buf.readDouble();
        double z1 = buf.readDouble();
        double x2 = buf.readDouble();
        double y2 = buf.readDouble();
        double z2 = buf.readDouble();
        return provider.getPacket(x1, y1, z1, x2, y2, z2);
    }

    public interface PacketProvider<T extends TwoPositionClientPacket> {
        T getPacket(double x1, double y1, double z1, double x2, double y2, double z2);
    }
}
