package mod.maxbogomol.fluffy_fur.common.network.block;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.PositionEffectPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class PlushHeartsEffectsPacket extends PositionEffectPacket {

    public PlushHeartsEffectsPacket(double x, double y, double z) {
        super(x, y, z);
    }

    public PlushHeartsEffectsPacket(BlockPos pos) {
        super(pos.getCenter());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = FluffyFur.proxy.getLevel();
        ParticleBuilder.create(FluffyFurParticles.HEART)
                .setColorData(ColorParticleData.create(new Color(DyeColor.PINK.getMapColor().col)).build())
                .setTransparencyData(GenericParticleData.create(0.2f, 0.5f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setScaleData(GenericParticleData.create(0.04f, 0.05f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.01f).build())
                .setLifetime(45)
                .randomVelocity(0.015f)
                .addVelocity(0, 0.01f, 0)
                .flatRandomOffset(0.3f, 0.3f, 0.3f)
                .repeat(level, x, y, z, 5);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, PlushHeartsEffectsPacket.class, PlushHeartsEffectsPacket::encode, PlushHeartsEffectsPacket::decode, PlushHeartsEffectsPacket::handle);
    }

    public static PlushHeartsEffectsPacket decode(FriendlyByteBuf buf) {
        return decode(PlushHeartsEffectsPacket::new, buf);
    }
}
