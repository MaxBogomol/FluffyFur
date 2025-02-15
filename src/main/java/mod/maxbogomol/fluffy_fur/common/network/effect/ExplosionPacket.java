package mod.maxbogomol.fluffy_fur.common.network.effect;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.effect.FluffyFurEffects;
import mod.maxbogomol.fluffy_fur.common.network.PositionClientPacket;
import mod.maxbogomol.fluffy_fur.config.FluffyFurClientConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class ExplosionPacket extends PositionClientPacket {
    protected final double radius;

    public ExplosionPacket(double x, double y, double z, double radius) {
        super(x, y, z);
        this.radius = radius;
    }

    public ExplosionPacket(Vec3 vec, double size) {
        super(vec);
        this.radius = size;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        if (FluffyFurClientConfig.EXPLOSION_EFFECT.get()) {
            Level level = FluffyFur.proxy.getLevel();
            FluffyFurEffects.explosionEffect(level, new Vec3(x, y, z), (float) radius);
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, ExplosionPacket.class, ExplosionPacket::encode, ExplosionPacket::decode, ExplosionPacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeDouble(radius);
    }

    public static ExplosionPacket decode(FriendlyByteBuf buf) {
        return new ExplosionPacket(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble());
    }
}
