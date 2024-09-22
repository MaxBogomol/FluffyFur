package mod.maxbogomol.fluffy_fur.common.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public abstract class PositionVectorColorClientPacket extends ClientPacket {
    protected final double x;
    protected final double y;
    protected final double z;
    protected final double vx;
    protected final double vy;
    protected final double vz;
    protected final float r;
    protected final float g;
    protected final float b;
    protected final float a;

    public PositionVectorColorClientPacket(double x, double y, double z, double vx, double vy, double vz, float r, float g, float b, float a) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public PositionVectorColorClientPacket(BlockPos pos, Vec3 vec, Color color) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.vx = vec.x();
        this.vy = vec.y();
        this.vz = vec.z();
        this.r = color.getRed();
        this.g = color.getGreen();
        this.b = color.getBlue();
        this.a = 1;
    }

    public PositionVectorColorClientPacket(Vec3 vec, Vec3 vel, Color color) {
        this.x = vec.x();
        this.y = vec.y();
        this.z = vec.z();
        this.vx = vel.x();
        this.vy = vel.y();
        this.vz = vel.z();
        this.r = color.getRed();
        this.g = color.getGreen();
        this.b = color.getBlue();
        this.a = 1;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeDouble(vx);
        buf.writeDouble(vy);
        buf.writeDouble(vz);
    }

    public static <T extends PositionVectorColorClientPacket> T decode(PacketProvider<T> provider, FriendlyByteBuf buf) {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        double vx = buf.readDouble();
        double vy = buf.readDouble();
        double vz = buf.readDouble();
        float r = buf.readFloat();
        float g = buf.readFloat();
        float b = buf.readFloat();
        float a = buf.readFloat();
        return provider.getPacket(x, y, z, vx, vy, vz, r, g, b, a);
    }

    public interface PacketProvider<T extends PositionVectorColorClientPacket> {
        T getPacket(double x, double y, double z, double vx, double vy, double vz, float r, float g, float b, float a);
    }
}
