package mod.maxbogomol.fluffy_fur.common.item;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.CubeParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.ParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.SparkParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.SphereParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.LightParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.options.ItemParticleOptions;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

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
            nbt.putInt("mode", (mode + 1) % 18);
            mode = nbt.getInt("mode");
        }

        if (level.isClientSide()) {
            if (mode == 0) {
                Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(1.75f));
                ParticleBuilder.create(FluffyFurParticles.WISP)
                        .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                        .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .setScaleData(GenericParticleData.create(0.3f, 2, 0).setEasing(Easing.ELASTIC_OUT).build())
                        .setLifetime(200)
                        .randomVelocity(0.035f, 0.035f, 0.035f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 5);
            }

            if (mode == 1) {
                Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(5f));
                ParticleBuilder.create(FluffyFurParticles.WISP)
                        .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                        .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .setScaleData(GenericParticleData.create(0.3f, 2, 0).setEasing(Easing.ELASTIC_OUT).build())
                        .setLifetime(200, 100)
                        .randomVelocity(0.35f, 0.35f, 0.35f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 50);
            }

            if (mode == 2) {
                Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(3f));
                ParticleBuilder.create(FluffyFurParticles.WISP)
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
                ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                        .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                        .setTransparencyData(GenericParticleData.create(1f, 0).build())
                        .setScaleData(GenericParticleData.create(0.3f, 0).build())
                        .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.1f).build())
                        .setLifetime(100)
                        .flatRandomOffset(0.35f, 0.35f, 0.35f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 10);
            }

            if (mode == 4) {
                Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(3f));
                ParticleBuilder.create(FluffyFurParticles.SMOKE)
                        .setRenderType(FluffyFurRenderTypes.DELAYED_PARTICLE)
                        .setColorData(ColorParticleData.create(0, 0, 0).build())
                        .setTransparencyData(GenericParticleData.create(1f, 0).build())
                        .setScaleData(GenericParticleData.create(0.3f, 0).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                        .setLightData(LightParticleData.DEFAULT)
                        .setLifetime(100)
                        .flatRandomOffset(0.35f, 0.35f, 0.35f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 10);
            }

            if (mode == 5) {
                Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(10f));
                ParticleBuilder.create(FluffyFurParticles.WISP)
                        .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                        .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .setScaleData(GenericParticleData.create(0.1f, 1, 0).setEasing(Easing.ELASTIC_OUT).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                        .setLifetime(200)
                        .randomVelocity(0.35f, 0.35f, 0.35f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 5);
                ParticleBuilder.create(FluffyFurParticles.TINY_WISP)
                        .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                        .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .setScaleData(GenericParticleData.create(0.1f, 1, 0).setEasing(Easing.ELASTIC_OUT).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                        .setLifetime(200)
                        .randomVelocity(0.35f, 0.35f, 0.35f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 5);
                ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                        .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                        .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .setScaleData(GenericParticleData.create(0.1f, 1, 0).setEasing(Easing.ELASTIC_OUT).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                        .setLifetime(200)
                        .randomVelocity(0.35f, 0.35f, 0.35f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 5);
                ParticleBuilder.create(FluffyFurParticles.STAR)
                        .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                        .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .setScaleData(GenericParticleData.create(0.1f, 1, 0).setEasing(Easing.ELASTIC_OUT).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                        .setLifetime(200)
                        .randomVelocity(0.35f, 0.35f, 0.35f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 5);
                ParticleBuilder.create(FluffyFurParticles.TINY_STAR)
                        .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                        .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .setScaleData(GenericParticleData.create(0.1f, 1, 0).setEasing(Easing.ELASTIC_OUT).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                        .setLifetime(200)
                        .randomVelocity(0.35f, 0.35f, 0.35f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 5);
                ParticleBuilder.create(FluffyFurParticles.SQUARE)
                        .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                        .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .setScaleData(GenericParticleData.create(0.1f, 1, 0).setEasing(Easing.ELASTIC_OUT).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                        .setLifetime(200)
                        .randomVelocity(0.35f, 0.35f, 0.35f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 5);
                ParticleBuilder.create(FluffyFurParticles.DOT)
                        .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                        .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .setScaleData(GenericParticleData.create(0.1f, 1, 0).setEasing(Easing.ELASTIC_OUT).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                        .setLifetime(200)
                        .randomVelocity(0.35f, 0.35f, 0.35f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 5);
                ParticleBuilder.create(FluffyFurParticles.CIRCLE)
                        .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                        .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .setScaleData(GenericParticleData.create(0.1f, 1, 0).setEasing(Easing.ELASTIC_OUT).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                        .setLifetime(200)
                        .randomVelocity(0.35f, 0.35f, 0.35f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 5);
                ParticleBuilder.create(FluffyFurParticles.TINY_CIRCLE)
                        .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                        .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .setScaleData(GenericParticleData.create(0.1f, 1, 0).setEasing(Easing.ELASTIC_OUT).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                        .setLifetime(200)
                        .randomVelocity(0.35f, 0.35f, 0.35f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 5);
                ParticleBuilder.create(FluffyFurParticles.HEART)
                        .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                        .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .setScaleData(GenericParticleData.create(0.1f, 1, 0).setEasing(Easing.ELASTIC_OUT).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                        .setLifetime(200)
                        .randomVelocity(0.35f, 0.35f, 0.35f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 5);
                ParticleBuilder.create(FluffyFurParticles.SMOKE)
                        .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                        .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .setScaleData(GenericParticleData.create(0.1f, 1, 0).setEasing(Easing.ELASTIC_OUT).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                        .setLifetime(200)
                        .randomVelocity(0.35f, 0.35f, 0.35f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 5);
            }

            if (mode == 6) {
                Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(5f));
                ParticleBuilder.create(FluffyFurParticles.DOT)
                        .setBehavior(CubeParticleBehavior.create().build())
                        .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                        .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .setScaleData(GenericParticleData.create(0.1f, 1, 0).setEasing(Easing.ELASTIC_OUT).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                        .setLifetime(200)
                        .randomVelocity(0.35f, 0.35f, 0.35f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 5);
            }

            if (mode == 7) {
                Vec3 pos = player.getEyePosition();
                ParticleBuilder.create(FluffyFurParticles.WISP)
                        .setColorData(ColorParticleData.create(1, 1, 1, 1, 0, 0).build())
                        .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .setScaleData(GenericParticleData.create(1f, 2, 0).setEasing(Easing.ELASTIC_OUT).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                        .setLifetime(100)
                        .randomVelocity(0.35f, 0.35f, 0.35f)
                        .flatRandomOffset(1, 2, 1)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 50);
                level.playSound(player, pos.x(), pos.y(), pos.z(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.PLAYERS, 1f, 1f);
            }

            if (mode == 8) {
                Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(0.75f));
                ItemParticleOptions options = new ItemParticleOptions(FluffyFurParticles.ITEM.get(), stack);
                ParticleBuilder.create(options)
                        .setRenderType(FluffyFurRenderTypes.DELAYED_TERRAIN_PARTICLE)
                        .setColorData(ColorParticleData.create(Color.WHITE).build())
                        .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.ELASTIC_OUT).build())
                        .setLifetime(200)
                        .randomVelocity(0.035f, 0.035f, 0.035f)
                        .setLightData(LightParticleData.DEFAULT)
                        .spawn(level, pos.x(), pos.y(), pos.z());
            }

            if (mode == 9) {
                Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(5f));
                ParticleBuilder.create(FluffyFurParticles.WISP)
                        .setRenderType(FluffyFurRenderTypes.DELAYED_PARTICLE)
                        .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                        .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .setScaleData(GenericParticleData.create(0.3f, 2, 0).setEasing(Easing.ELASTIC_OUT).build())
                        .setLightData(LightParticleData.DEFAULT)
                        .setLifetime(200, 100)
                        .randomVelocity(0.35f, 0.35f, 0.35f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 50);
            }

            if (mode == 10) {
                Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(5f));
                ParticleBuilder.create(FluffyFurParticles.SQUARE)
                        .setBehavior(SphereParticleBehavior.create().build())
                        .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                        .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .setScaleData(GenericParticleData.create(0.1f, 1, 0).setEasing(Easing.ELASTIC_OUT).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                        .setLifetime(200)
                        .randomVelocity(0.35f, 0.35f, 0.35f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 5);
            }

            if (mode == 11) {
                Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(3f));
                ParticleBuilder.create(FluffyFurParticles.PANCAKE)
                        .setRenderType(FluffyFurRenderTypes.DELAYED_PARTICLE)
                        .setColorData(ColorParticleData.create(Color.WHITE).build())
                        .setTransparencyData(GenericParticleData.create(1f, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .setScaleData(GenericParticleData.create(0.3f, 2, 0).setEasing(Easing.ELASTIC_OUT).build())
                        .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.1f).build())
                        .setLightData(LightParticleData.DEFAULT)
                        .setLifetime(200)
                        .randomVelocity(0.035f, 0.035f, 0.035f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 1);
            }

            if (mode == 12) {
                Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(3f));
                ParticleBuilder.create(FluffyFurParticles.DEATH)
                        .setRenderType(FluffyFurRenderTypes.DELAYED_PARTICLE)
                        .setColorData(ColorParticleData.create(Color.WHITE).build())
                        .setTransparencyData(GenericParticleData.create(1f, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .setScaleData(GenericParticleData.create(0.3f, 2, 0).setEasing(Easing.ELASTIC_OUT).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.01f).build())
                        .setLightData(LightParticleData.DEFAULT)
                        .setLifetime(200)
                        .randomVelocity(0.035f, 0.035f, 0.035f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 1);
            }

            if (mode == 13) {
                Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(5f));
                ParticleBuilder.create(FluffyFurParticles.WISP)
                        .setColorData(ColorParticleData.create().setRandomColor(Color.WHITE, Color.GREEN, Color.WHITE, Color.WHITE).build())
                        .setTransparencyData(GenericParticleData.create().setRandomValue(0.1f, 0.5f).setEasing(Easing.QUARTIC_OUT).build())
                        .setScaleData(GenericParticleData.create(0.3f, 2, 0).setEasing(Easing.ELASTIC_OUT).build())
                        .setLifetime(200, 100)
                        .randomVelocity(0.35f, 0.35f, 0.35f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 50);
            }

            if (mode == 14) {
                Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(5f));
                ParticleBuilder.create(FluffyFurParticles.WISP)
                        .setRenderType(FluffyFurRenderTypes.DELAYED_PARTICLE)
                        .setBehavior(ParticleBehavior.create(90, 0, 0)
                                .setXSpinData(SpinParticleData.create().randomOffsetDegrees(-5, 5).build())
                                .setYSpinData(SpinParticleData.create().randomOffsetDegrees(-5, 5).build())
                                .build())
                        .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                        .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .setScaleData(GenericParticleData.create(0.3f, 2, 0).setEasing(Easing.ELASTIC_OUT).build())
                        .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                        .setLightData(LightParticleData.DEFAULT)
                        .setLifetime(200, 100)
                        .randomVelocity(0.15f, 0, 0.15f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 50);
            }

            if (mode == 15) {
                Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(5f));
                ParticleBuilder.create(FluffyFurParticles.CHERRY_LEAVES)
                        .setParticleRenderType(ParticleRenderType.PARTICLE_SHEET_OPAQUE)
                        .setLightData(LightParticleData.DEFAULT)
                        .randomVelocity(0.1f)
                        .setLifetime(59)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 100);
            }

            if (mode == 16) {
                Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(5f));
                ParticleBuilder.create(FluffyFurParticles.WISP)
                        .setBehavior(SparkParticleBehavior.create().build())
                        .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                        .setTransparencyData(GenericParticleData.create(1, 1, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.ELASTIC_OUT).build())
                        .setLifetime(20, 10)
                        .randomVelocity(1f)
                        .setFriction(0.9f)
                        .enablePhysics()
                        .setGravity(1f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 50);
            }

            if (mode == 17) {
                Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(5f));
                ParticleBuilder.create(FluffyFurParticles.WISP)
                        .setBehavior(SparkParticleBehavior.create()
                                .enableSecondColor()
                                .setColorData(ColorParticleData.create().setRandomColor().build())
                                .setTransparencyData(GenericParticleData.create(0).build())
                                .build())
                        .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                        .setTransparencyData(GenericParticleData.create(1, 1, 0).setEasing(Easing.QUARTIC_OUT).build())
                        .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.ELASTIC_OUT).build())
                        .setLifetime(20, 10)
                        .randomVelocity(1f)
                        .setFriction(0.9f)
                        .enablePhysics()
                        .setGravity(1f)
                        .repeat(level, pos.x(), pos.y(), pos.z(), 50);
            }
        }

        return InteractionResultHolder.success(stack);
    }
}
