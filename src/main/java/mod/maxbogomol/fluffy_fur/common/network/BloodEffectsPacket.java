package mod.maxbogomol.fluffy_fur.common.network;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.SparkParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class BloodEffectsPacket extends PositionEffectPacket {
    protected final double size;

    public BloodEffectsPacket(double x, double y, double z, double size) {
        super(x, y, z);
        this.size = size;
    }

    public BloodEffectsPacket(Vec3 vec, double size) {
        super(vec);
        this.size = size;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = FluffyFur.proxy.getLevel();
        double size = this.size;
        if (size > 100) size = 100;
        ParticleBuilder builder = ParticleBuilder.create(FluffyFurParticles.TINY_WISP);
        builder.setBehavior(SparkParticleBehavior.create().build())
                .setColorData(ColorParticleData.create(1f, 0, 0).build())
                .setTransparencyData(GenericParticleData.create(0.6f, 0.6f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.ELASTIC_OUT).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                .setLifetime(20)
                .randomVelocity(0.5f)
                .addVelocity(0, 0.1f, 0)
                .randomOffset(0.05f)
                .setFriction(0.9f)
                .enablePhysics()
                .setGravity(1f)
                .repeat(level, x, y, z, (int) size / 2, 0.4f);
        builder.setRenderType(FluffyFurRenderTypes.DELAYED_PARTICLE)
                .repeat(level, x, y, z, (int) size, 0.6f);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, BloodEffectsPacket.class, BloodEffectsPacket::encode, BloodEffectsPacket::decode, BloodEffectsPacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeDouble(size);
    }

    public static BloodEffectsPacket decode(FriendlyByteBuf buf) {
        return new BloodEffectsPacket(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble());
    }
}
