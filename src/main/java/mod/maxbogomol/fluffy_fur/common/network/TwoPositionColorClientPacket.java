package mod.maxbogomol.fluffy_fur.common.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public abstract class TwoPositionColorClientPacket extends ClientPacket {
    protected final double x1;
    protected final double y1;
    protected final double z1;
    protected final double x2;
    protected final double y2;
    protected final double z2;
    protected final float r;
    protected final float g;
    protected final float b;
    protected final float a;

    public TwoPositionColorClientPacket(double x1, double y1, double z1, double x2, double y2, double z2, float r, float g, float b, float a) {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public TwoPositionColorClientPacket(BlockPos pos, Vec3 vec, Color color) {
        this.x1 = pos.getX();
        this.y1 = pos.getY();
        this.z1 = pos.getZ();
        this.x2 = vec.x();
        this.y2 = vec.y();
        this.z2 = vec.z();
        this.r = color.getRed() / 255f;
        this.g = color.getGreen() / 255f;
        this.b = color.getBlue() / 255f;
        this.a = 1;
    }

    public TwoPositionColorClientPacket(Vec3 vec1, Vec3 vec2, Color color) {
        this.x1 = vec1.x();
        this.y1 = vec1.y();
        this.z1 = vec1.z();
        this.x2 = vec2.x();
        this.y2 = vec2.y();
        this.z2 = vec2.z();
        this.r = color.getRed() / 255f;
        this.g = color.getGreen() / 255f;
        this.b = color.getBlue() / 255f;
        this.a = 1;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(x1);
        buf.writeDouble(y1);
        buf.writeDouble(z1);
        buf.writeDouble(x2);
        buf.writeDouble(y2);
        buf.writeDouble(z2);
        buf.writeFloat(r);
        buf.writeFloat(g);
        buf.writeFloat(b);
        buf.writeFloat(a);
    }

    public static <T extends TwoPositionColorClientPacket> T decode(PacketProvider<T> provider, FriendlyByteBuf buf) {
        double x1 = buf.readDouble();
        double y1 = buf.readDouble();
        double z1 = buf.readDouble();
        double x2 = buf.readDouble();
        double y2 = buf.readDouble();
        double z2 = buf.readDouble();
        float r = buf.readFloat();
        float g = buf.readFloat();
        float b = buf.readFloat();
        float a = buf.readFloat();
        return provider.getPacket(x1, y1, z1, x2, y2, z2, r, g, b, a);
    }

    public interface PacketProvider<T extends TwoPositionColorClientPacket> {
        T getPacket(double x1, double y1, double z1, double x2, double y2, double z2, float r, float g, float b, float a);
    }
}
