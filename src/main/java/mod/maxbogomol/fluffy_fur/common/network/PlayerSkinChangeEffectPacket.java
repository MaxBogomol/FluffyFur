package mod.maxbogomol.fluffy_fur.common.network;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class PlayerSkinChangeEffectPacket extends PositionEffectPacket {

    public PlayerSkinChangeEffectPacket(double x, double y, double z) {
        super(x, y, z);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = FluffyFur.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(1, 1, 1, 1, 0, 0).build())
                .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .setScaleData(GenericParticleData.create(1f, 2, 0).setEasing(Easing.ELASTIC_OUT).build())
                .setLifetime(100)
                .randomVelocity(0.35f)
                .randomSpin(0.1f)
                .flatRandomOffset(1, 2, 1)
                .disableDistanceSpawn()
                .repeat(level, x, y, z, 50);
        level.playSound(null, x, y, z, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.PLAYERS, 1f, 1f);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, PlayerSkinChangeEffectPacket.class, PlayerSkinChangeEffectPacket::encode, PlayerSkinChangeEffectPacket::decode, PlayerSkinChangeEffectPacket::handle);
    }

    public static PlayerSkinChangeEffectPacket decode(FriendlyByteBuf buf) {
        return decode(PlayerSkinChangeEffectPacket::new, buf);
    }
}
