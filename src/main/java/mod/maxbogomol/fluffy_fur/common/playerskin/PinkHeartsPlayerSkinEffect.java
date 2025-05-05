package mod.maxbogomol.fluffy_fur.common.playerskin;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;

import java.awt.*;

public class PinkHeartsPlayerSkinEffect extends PlayerSkinEffect {

    public PinkHeartsPlayerSkinEffect(String id) {
        super(id);
    }

    @Override
    public void tick(Player player) {
        if (player.level().isClientSide()) {
            if (random.nextFloat() < 0.2f) {
                ParticleBuilder.create(FluffyFurParticles.HEART)
                        .setColorData(ColorParticleData.create(new Color(DyeColor.PINK.getMapColor().col)).build())
                        .setTransparencyData(GenericParticleData.create(0.2f, 0.5f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                        .setScaleData(GenericParticleData.create(0.02f, 0.05f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.01f).build())
                        .setLifetime(45)
                        .randomVelocity(0.035f)
                        .flatRandomOffset(player.getBbWidth() / 2f, player.getBbHeight() / 2f, player.getBbWidth() / 2f)
                        .spawn(player.level(), player.getX(), player.getY() + (player.getBbHeight() / 2f), player.getZ());
            }
        }
    }
}
