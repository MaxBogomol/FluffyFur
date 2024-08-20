package mod.maxbogomol.fluffy_fur.common.item;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class TestShrimpItem extends Item {

    public TestShrimpItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag nbt = stack.getOrCreateTag();

        if (!nbt.contains("mode")) {
            nbt.putInt("mode", 0);
        }

        int mode = nbt.getInt("mode");

        if (player.isShiftKeyDown()) {
            nbt.putInt("mode", (mode + 1) % 4);
        }

        if (level.isClientSide()) {
            if (mode == 0) {
                Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(1.75f));
                /*
                Particles.create(FluffyFur.WISP_PARTICLE)
                        .randomVelocity(0.035f, 0.035f, 0.035f)
                        .setAlpha(0.5f, 0).setScale(0.3f, 0)
                        .setColor(0, 0, 1, 1, 0, 0)
                        .setLifetime(60)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 5);
                Particles.create(FluffyFur.SPARKLE_PARTICLE)
                        .randomVelocity(0.035f, 0.035f, 0.035f)
                        .setAlpha(0.5f, 0).setScale(0.1f, 0)
                        .setColor(0, 0, 1, 1, 0, 0)
                        .setLifetime(60)
                        .randomSpin(0.5f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 50);*/
                ParticleBuilder.create(FluffyFur.WISP_PARTICLE)
                        .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                        .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .setScaleData(GenericParticleData.create(0.3f, 2, 0).setEasing(Easing.ELASTIC_OUT).build())
                        .setLifetime(200)
                        .randomVelocity(0.035f, 0.035f, 0.035f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 5);
            }

            if (mode == 1) {
                Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(5f));
                ParticleBuilder.create(FluffyFur.WISP_PARTICLE)
                        .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                        .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .setScaleData(GenericParticleData.create(0.3f, 2, 0).setEasing(Easing.ELASTIC_OUT).build())
                        .setLifetime(200, 100)
                        .randomVelocity(0.35f, 0.35f, 0.35f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 50);
            }

            if (mode == 2) {
                Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(3f));
                ParticleBuilder.create(FluffyFur.WISP_PARTICLE)
                        .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                        .setTransparencyData(GenericParticleData.create(1f, 0).build())
                        .setScaleData(GenericParticleData.create(0.3f, 0).build())
                        .setLifetime(200, 100)
                        .flatRandomVelocity(0.05f, 0.05f, 0.05f)
                        .flatRandomOffset(0.35f, 0.35f, 0.35f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 1);
            }

            if (mode == 3) {
                Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(3f));
                ParticleBuilder.create(FluffyFur.SPARKLE_PARTICLE)
                        .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                        .setTransparencyData(GenericParticleData.create(1f, 0).build())
                        .setScaleData(GenericParticleData.create(0.3f, 0).build())
                        .randomSpin(0.1f)
                        .setLifetime(100)
                        .flatRandomOffset(0.35f, 0.35f, 0.35f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 10);
            }
        }

        return InteractionResultHolder.success(stack);
    }
}
