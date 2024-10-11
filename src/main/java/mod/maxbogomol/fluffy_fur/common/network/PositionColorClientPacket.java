package mod.maxbogomol.fluffy_fur.common.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public abstract class PositionColorClientPacket extends ClientPacket {
    protected final double x;
    protected final double y;
    protected final double z;
    protected final float r;
    protected final float g;
    protected final float b;
    protected final float a;

    public PositionColorClientPacket(double x, double y, double z, float r, float g, float b, float a) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public PositionColorClientPacket(double x, double y, double z, Color color) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = color.getRed() / 255f;
        this.g = color.getGreen() / 255f;
        this.b = color.getBlue() / 255f;
        this.a = 1;
    }

    public PositionColorClientPacket(BlockPos pos, Color color) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.r = color.getRed() / 255f;
        this.g = color.getGreen() / 255f;
        this.b = color.getBlue() / 255f;
        this.a = 1;
    }

    public PositionColorClientPacket(Vec3 vec, Color color) {
        this.x = vec.x();
        this.y = vec.y();
        this.z = vec.z();
        this.r = color.getRed() / 255f;
        this.g = color.getGreen() / 255f;
        this.b = color.getBlue() / 255f;
        this.a = 1;
    }

    public PositionColorClientPacket(double x, double y, double z, Color color, float a) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = color.getRed() / 255f;
        this.g = color.getGreen() / 255f;
        this.b = color.getBlue() / 255f;
        this.a = a;
    }

    public PositionColorClientPacket(BlockPos pos, Color color, float a) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.r = color.getRed() / 255f;
        this.g = color.getGreen() / 255f;
        this.b = color.getBlue() / 255f;
        this.a = a;
    }

    public PositionColorClientPacket(Vec3 vec, Color color, float a) {
        this.x = vec.x();
        this.y = vec.y();
        this.z = vec.z();
        this.r = color.getRed() / 255f;
        this.g = color.getGreen() / 255f;
        this.b = color.getBlue() / 255f;
        this.a = a;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeFloat(r);
        buf.writeFloat(g);
        buf.writeFloat(b);
        buf.writeFloat(a);
    }

    public static <T extends PositionColorClientPacket> T decode(PacketProvider<T> provider, FriendlyByteBuf buf) {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        float r = buf.readFloat();
        float g = buf.readFloat();
        float b = buf.readFloat();
        float a = buf.readFloat();
        return provider.getPacket(x, y, z, r, g, b, a);
    }

    public interface PacketProvider<T extends PositionColorClientPacket> {
        T getPacket(double x, double y, double z, float r, float g, float b, float a);
    }
}
