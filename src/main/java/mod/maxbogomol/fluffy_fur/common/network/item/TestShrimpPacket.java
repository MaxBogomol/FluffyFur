package mod.maxbogomol.fluffy_fur.common.network.item;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.*;
import mod.maxbogomol.fluffy_fur.client.particle.data.*;
import mod.maxbogomol.fluffy_fur.client.particle.options.BlockParticleOptions;
import mod.maxbogomol.fluffy_fur.client.particle.options.FluidParticleOptions;
import mod.maxbogomol.fluffy_fur.client.particle.options.ItemParticleOptions;
import mod.maxbogomol.fluffy_fur.client.particle.options.SpriteParticleOptions;
import mod.maxbogomol.fluffy_fur.client.screenshake.ScreenshakeHandler;
import mod.maxbogomol.fluffy_fur.client.screenshake.ScreenshakeInstance;
import mod.maxbogomol.fluffy_fur.client.shader.postprocess.DepthPostProcess;
import mod.maxbogomol.fluffy_fur.client.shader.postprocess.GlowPostProcess;
import mod.maxbogomol.fluffy_fur.client.shader.postprocess.GlowPostProcessInstance;
import mod.maxbogomol.fluffy_fur.client.shader.postprocess.NormalGlowPostProcess;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.TwoPositionClientPacket;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.registry.common.block.FluffyFurBlocks;
import mod.maxbogomol.fluffy_fur.registry.common.item.FluffyFurItems;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import org.joml.Vector3f;

import java.awt.*;
import java.util.UUID;
import java.util.function.Supplier;

public class TestShrimpPacket extends TwoPositionClientPacket {
    protected final int mode;
    protected final UUID uuid;

    public TestShrimpPacket(double x1, double y1, double z1, double x2, double y2, double z2, int mode, UUID uuid) {
        super(x1, y1, z1, x2, y2, z2);
        this.mode = mode;
        this.uuid = uuid;
    }

    public TestShrimpPacket(Vec3 vec1, Vec3 vec2, int mode, UUID uuid) {
        super(vec1, vec2);
        this.mode = mode;
        this.uuid = uuid;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Player player = FluffyFur.proxy.getPlayer();
        Level level = FluffyFur.proxy.getLevel();
        Vec3 startPos = new Vec3(x1, y1, z1);
        Vec3 lookPos = new Vec3(x2, y2, z2);
        boolean isSamePlayer = player.getUUID().equals(uuid);

        if (mode == 0) {
            Vec3 pos = startPos.add(lookPos.scale(1.75f));
            ParticleBuilder.create(FluffyFurParticles.WISP)
                    .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                    .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                    .setScaleData(GenericParticleData.create(0.3f, 2, 0).setEasing(Easing.ELASTIC_OUT).build())
                    .setLifetime(200)
                    .randomVelocity(0.035f, 0.035f, 0.035f)
                    .repeat(level, pos.x(), pos.y(), pos.z(), 5);
        }

        if (mode == 1) {
            Vec3 pos = startPos.add(lookPos.scale(5f));
            ParticleBuilder.create(FluffyFurParticles.WISP)
                    .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                    .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                    .setScaleData(GenericParticleData.create(0.3f, 2, 0).setEasing(Easing.ELASTIC_OUT).build())
                    .setLifetime(200, 100)
                    .randomVelocity(0.35f, 0.35f, 0.35f)
                    .repeat(level, pos.x(), pos.y(), pos.z(), 50);
        }

        if (mode == 2) {
            Vec3 pos = startPos.add(lookPos.scale(5f));
            ParticleBuilder.create(FluffyFurParticles.WISP)
                    .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_PARTICLE)
                    .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                    .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                    .setScaleData(GenericParticleData.create(0.3f, 2, 0).setEasing(Easing.ELASTIC_OUT).build())
                    .setLightData(LightParticleData.DEFAULT)
                    .setLifetime(200, 100)
                    .randomVelocity(0.35f, 0.35f, 0.35f)
                    .repeat(level, pos.x(), pos.y(), pos.z(), 50);
        }

        if (mode == 3) {
            Vec3 pos = startPos.add(lookPos.scale(5f));
            ParticleBuilder.create(FluffyFurParticles.WISP)
                    .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_PARTICLE)
                    .setBehavior(ParticleBehavior.create(90, 0, 0)
                            .setXSpinData(SpinParticleData.create().randomOffsetDegrees(-5, 5).build())
                            .setYSpinData(SpinParticleData.create().randomOffsetDegrees(-5, 5).build())
                            .setZSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                            .build())
                    .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                    .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                    .setScaleData(GenericParticleData.create(0.3f, 2, 0).setEasing(Easing.ELASTIC_OUT).build())
                    .setLightData(LightParticleData.DEFAULT)
                    .setLifetime(200, 100)
                    .randomVelocity(0.15f, 0, 0.15f)
                    .repeat(level, pos.x(), pos.y(), pos.z(), 50);
        }

        if (mode == 4) {
            Vec3 pos = startPos.add(lookPos.scale(5f));
            ParticleBuilder.create(FluffyFurParticles.DOT)
                    .setRenderType(FluffyFurRenderTypes.ADDITIVE_PARTICLE_TEXTURE)
                    .setBehavior(CubeParticleBehavior.create().enableSided().build())
                    .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                    .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                    .setScaleData(GenericParticleData.create(0.1f, 1, 0).setEasing(Easing.ELASTIC_OUT).build())
                    .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                    .setLifetime(200)
                    .randomVelocity(0.35f)
                    .repeat(level, pos.x(), pos.y(), pos.z(), 5);
        }

        if (mode == 5) {
            Vec3 pos = startPos.add(lookPos.scale(5f));
            ParticleBuilder.create(FluffyFurParticles.SQUARE)
                    .setRenderType(FluffyFurRenderTypes.ADDITIVE_PARTICLE_TEXTURE)
                    .setBehavior(SphereParticleBehavior.create().build())
                    .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                    .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                    .setScaleData(GenericParticleData.create(0.1f, 1, 0).setEasing(Easing.ELASTIC_OUT).build())
                    .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                    .setLifetime(200)
                    .randomVelocity(0.35f)
                    .repeat(level, pos.x(), pos.y(), pos.z(), 5);
        }

        if (mode == 6) {
            Vec3 pos = startPos;
            ParticleBuilder.create(FluffyFurParticles.WISP)
                    .setColorData(ColorParticleData.create(1, 1, 1, 1, 0, 0).build())
                    .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                    .setScaleData(GenericParticleData.create(1f, 2, 0).setEasing(Easing.ELASTIC_OUT).build())
                    .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                    .setLifetime(100)
                    .randomVelocity(0.35f, 0.35f, 0.35f)
                    .flatRandomOffset(1, 2, 1)
                    .repeat(level, pos.x(), pos.y(), pos.z(), 50);
            level.playSound(FluffyFur.proxy.getPlayer(), pos.x(), pos.y(), pos.z(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.PLAYERS, 1f, 1f);
        }

        if (mode == 7) {
            Vec3 pos = startPos.add(lookPos.scale(0.75f));
            ItemParticleOptions options = new ItemParticleOptions(FluffyFurParticles.ITEM.get(), new ItemStack(FluffyFurItems.TEST_SHRIMP.get()));
            ParticleBuilder.create(options)
                    .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_BLOCK_PARTICLE)
                    .setColorData(ColorParticleData.create(Color.WHITE).build())
                    .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                    .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.ELASTIC_OUT).build())
                    .setSpriteData(SpriteParticleData.CRUMBS_RANDOM)
                    .setLifetime(200)
                    .randomVelocity(0.035f, 0.035f, 0.035f)
                    .setLightData(LightParticleData.DEFAULT)
                    .spawn(level, pos.x(), pos.y(), pos.z());
        }

        if (mode == 8) {
            Vec3 pos = startPos.add(lookPos.scale(0.75f));
            BlockParticleOptions options = new BlockParticleOptions(FluffyFurParticles.BLOCK.get(), FluffyFurBlocks.YONKABLOCK.get().defaultBlockState());
            ParticleBuilder.create(options)
                    .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_BLOCK_PARTICLE)
                    .setColorData(ColorParticleData.create(Color.WHITE).build())
                    .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                    .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.ELASTIC_OUT).build())
                    .setSpriteData(SpriteParticleData.CRUMBS_RANDOM)
                    .setLifetime(200)
                    .randomVelocity(0.035f, 0.035f, 0.035f)
                    .setLightData(LightParticleData.DEFAULT)
                    .spawn(level, pos.x(), pos.y(), pos.z());
        }

        if (mode == 9) {
            Vec3 pos = startPos.add(lookPos.scale(0.75f));
            FluidParticleOptions options = new FluidParticleOptions(FluffyFurParticles.FLUID.get(), new FluidStack(Fluids.LAVA, 1), false);
            ParticleBuilder.create(options)
                    .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_BLOCK_PARTICLE)
                    .setColorData(ColorParticleData.create(Color.WHITE).build())
                    .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                    .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.ELASTIC_OUT).build())
                    .setSpriteData(SpriteParticleData.CRUMBS_RANDOM)
                    .setLifetime(200)
                    .randomVelocity(0.035f, 0.035f, 0.035f)
                    .setLightData(LightParticleData.DEFAULT)
                    .spawn(level, pos.x(), pos.y(), pos.z());
        }

        if (mode == 10) {
            Vec3 pos = startPos.add(lookPos.scale(3f));
            ParticleBuilder.create(FluffyFurParticles.PANCAKE)
                    .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_PARTICLE)
                    .setColorData(ColorParticleData.create(Color.WHITE).build())
                    .setTransparencyData(GenericParticleData.create(1f, 0).setEasing(Easing.QUARTIC_OUT).build())
                    .setScaleData(GenericParticleData.create(0.3f, 2, 0).setEasing(Easing.ELASTIC_OUT).build())
                    .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.1f).build())
                    .setLightData(LightParticleData.DEFAULT)
                    .setLifetime(200)
                    .randomVelocity(0.035f, 0.035f, 0.035f)
                    .repeat(level, pos.x(), pos.y(), pos.z(), 1);
        }

        if (mode == 11) {
            Vec3 pos = startPos.add(lookPos.scale(3f));
            ParticleBuilder.create(FluffyFurParticles.DEATH)
                    .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_PARTICLE)
                    .setColorData(ColorParticleData.create(Color.WHITE).build())
                    .setTransparencyData(GenericParticleData.create(1f, 0).setEasing(Easing.QUARTIC_OUT).build())
                    .setScaleData(GenericParticleData.create(0.3f, 2, 0).setEasing(Easing.ELASTIC_OUT).build())
                    .setSpinData(SpinParticleData.create().randomSpin(0.01f).build())
                    .setLightData(LightParticleData.DEFAULT)
                    .setLifetime(200)
                    .randomVelocity(0.035f, 0.035f, 0.035f)
                    .repeat(level, pos.x(), pos.y(), pos.z(), 1);
        }

        if (mode == 12) {
            Vec3 pos = startPos.add(lookPos.scale(5f));
            ParticleBuilder.create(FluffyFurParticles.WISP)
                    .setColorData(ColorParticleData.create().setRandomColor(Color.WHITE, Color.GREEN, Color.WHITE, Color.WHITE).build())
                    .setTransparencyData(GenericParticleData.create().setRandomValue(0.1f, 0.5f).setEasing(Easing.QUARTIC_OUT).build())
                    .setScaleData(GenericParticleData.create(0.3f, 2, 0).setEasing(Easing.ELASTIC_OUT).build())
                    .setLifetime(200, 100)
                    .randomVelocity(0.35f, 0.35f, 0.35f)
                    .repeat(level, pos.x(), pos.y(), pos.z(), 50);
        }

        if (mode == 13) {
            Vec3 pos = startPos.add(lookPos.scale(5f));
            ParticleBuilder.create(FluffyFurParticles.CHERRY_LEAVES)
                    .setParticleRenderType(ParticleRenderType.PARTICLE_SHEET_OPAQUE)
                    .setLightData(LightParticleData.DEFAULT)
                    .randomVelocity(0.1f)
                    .setLifetime(59)
                    .repeat(level, pos.x(), pos.y(), pos.z(), 100);
        }

        if (mode == 14) {
            Vec3 pos = startPos.add(lookPos.scale(5f));
            ParticleBuilder.create(FluffyFurParticles.WISP)
                    .setBehavior(SparkParticleBehavior.create().build())
                    .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                    .setTransparencyData(GenericParticleData.create(1, 1, 0).setEasing(Easing.QUARTIC_OUT).build())
                    .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.ELASTIC_OUT).build())
                    .setLifetime(20, 10)
                    .randomVelocity(1f)
                    .setGravity(1f)
                    .setFriction(0.9f)
                    .repeat(level, pos.x(), pos.y(), pos.z(), 50);
        }

        if (mode == 15) {
            Vec3 pos = startPos.add(lookPos.scale(5f));
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
                    .setGravity(1f)
                    .setFriction(0.9f)
                    .repeat(level, pos.x(), pos.y(), pos.z(), 50);
        }

        if (mode == 16) {
            Vec3 pos = startPos.add(lookPos.scale(5f));
            ParticleBuilder.create(FluffyFurParticles.TRAIL)
                    .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_PARTICLE_TEXTURE)
                    .setBehavior(TrailParticleBehavior.create()
                            .setColorData(ColorParticleData.create().setRandomColor().build())
                            .setTransparencyData(GenericParticleData.create(1, 1, 0).setEasing(Easing.QUARTIC_OUT).build())
                            .enableSecondColor()
                            .setWidthFunction(RenderUtil.LINEAR_IN_ROUND_WIDTH_FUNCTION)
                            .build())
                    .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                    .setTransparencyData(GenericParticleData.create(1, 1, 0).setEasing(Easing.QUARTIC_OUT).build())
                    .setScaleData(GenericParticleData.create(0.5f).setEasing(Easing.ELASTIC_OUT).build())
                    .setLifetime(200, 100)
                    .randomVelocity(1f)
                    .setGravity(1f)
                    .repeat(level, pos.x(), pos.y(), pos.z(), 50);
        }

        if (mode == 17) {
            Vec3 pos = startPos.add(lookPos.scale(5f));
            ParticleBuilder builder = ParticleBuilder.create(FluffyFurParticles.WISP)
                    .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                    .setTransparencyData(GenericParticleData.create(0.5f, 0.5f, 0).setEasing(Easing.QUARTIC_OUT).build())
                    .setScaleData(GenericParticleData.create(0.5f).setEasing(Easing.ELASTIC_OUT).build())
                    .setLifetime(200, 100)
                    .randomVelocity(1f)
                    .setGravity(1f);
            ParticleBuilder.create(FluffyFurParticles.TRAIL)
                    .setRenderType(FluffyFurRenderTypes.ADDITIVE_PARTICLE_TEXTURE)
                    .setBehavior(TrailParticleBehavior.create()
                            .setColorData(ColorParticleData.create().setRandomColor().build())
                            .setTransparencyData(GenericParticleData.create(1, 1, 0).setEasing(Easing.QUARTIC_OUT).build())
                            .enableSecondColor()
                            .build())
                    .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                    .setTransparencyData(GenericParticleData.create(1, 1, 0).setEasing(Easing.QUARTIC_OUT).build())
                    .setScaleData(GenericParticleData.create(0.5f).setEasing(Easing.ELASTIC_OUT).build())
                    .setLifetime(200, 100)
                    .randomVelocity(1f)
                    .setGravity(1f)
                    .addAdditionalBuilder(builder)
                    .repeat(level, pos.x(), pos.y(), pos.z(), 50);
        }

        if (mode == 18) {
            ScreenshakeHandler.addScreenshake(new ScreenshakeInstance(50).setIntensity(0.5f, 0).setEasing(Easing.QUINTIC_IN_OUT));
            ScreenshakeHandler.addScreenshake(new ScreenshakeInstance(50).setIntensity(0.5f, 0).setEasing(Easing.QUINTIC_IN_OUT).disableRotation().enablePosition());
            ScreenshakeHandler.addScreenshake(new ScreenshakeInstance(50).setIntensity(0.2f, 0).setEasing(Easing.QUINTIC_IN_OUT).disableRotation().enableFov());
            ScreenshakeHandler.addScreenshake(new ScreenshakeInstance(20).setIntensity(0, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).disableRotation().enableVector().setRandomVector());
        }

        if (mode == 19) {
            Vec3 pos = startPos.add(lookPos.scale(5f));
            ParticleBuilder.create(FluffyFurParticles.SKULL)
                    .setColorData(ColorParticleData.create(0, 0, 1, 1, 0, 0).build())
                    .setTransparencyData(GenericParticleData.create(1, 1, 0).setEasing(Easing.QUARTIC_OUT).build())
                    .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.ELASTIC_OUT).build())
                    .setLifetime(20, 10)
                    .randomVelocity(0.1f)
                    .repeat(level, pos.x(), pos.y(), pos.z(), 50);
        }

        if (mode == 20) {
            Vec3 pos = startPos.add(lookPos.scale(10f));
            SpriteParticleOptions options = new SpriteParticleOptions(FluffyFurParticles.SPRITE.get(), RenderUtil.getSprite(new ResourceLocation(FluffyFur.MOD_ID, "particle/earth")));
            ParticleBuilder.create(options)
                    .setRenderType(RenderType.cutout())
                    .setBufferSource(Minecraft.getInstance().renderBuffers().bufferSource())
                    .setFormat(DefaultVertexFormat.BLOCK)
                    .setBehavior(SphereParticleBehavior.create().disableSecondSide().setSphereSize(64, 32).build())
                    .setColorData(ColorParticleData.create(Color.WHITE).build())
                    .setTransparencyData(GenericParticleData.create(1).build())
                    .setScaleData(GenericParticleData.create(0.1f, 10, 0).setEasing(Easing.ELASTIC_OUT).build())
                    .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                    .setLifetime(200)
                    .repeat(level, pos.x(), pos.y(), pos.z(), 1);
        }

        if (mode == 21) {
            Vec3 pos = startPos.add(lookPos.scale(10f));
            SpriteParticleOptions options = new SpriteParticleOptions(FluffyFurParticles.SPRITE.get(), RenderUtil.getSprite(new ResourceLocation(FluffyFur.MOD_ID, "particle/sun")));
            ParticleBuilder.create(options)
                    .setRenderType(RenderType.cutout())
                    .setBufferSource(Minecraft.getInstance().renderBuffers().bufferSource())
                    .setFormat(DefaultVertexFormat.BLOCK)
                    .setBehavior(SphereParticleBehavior.create().disableSecondSide().setSphereSize(64, 32).build())
                    .setColorData(ColorParticleData.create(Color.WHITE).build())
                    .setTransparencyData(GenericParticleData.create(1).build())
                    .setScaleData(GenericParticleData.create(0.1f, 10, 0).setEasing(Easing.ELASTIC_OUT).build())
                    .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                    .setLifetime(200)
                    .repeat(level, pos.x(), pos.y(), pos.z(), 1);
        }

        if (mode == 22) {
            Vec3 pos = startPos.add(lookPos.scale(100f));
            ParticleBuilder.create(FluffyFurParticles.SUN)
                    .setRenderType(FluffyFurRenderTypes.ADDITIVE_PARTICLE_TEXTURE)
                    .setBehavior(SphereParticleBehavior.create().setSphereSize(64, 32).build())
                    .setColorData(ColorParticleData.create(Color.WHITE).build())
                    .setTransparencyData(GenericParticleData.create(1).build())
                    .setScaleData(GenericParticleData.create(0.1f, 100, 0).setEasing(Easing.ELASTIC_OUT).build())
                    .setSpinData(SpinParticleData.create().randomOffset().randomSpin(1f).build())
                    .setLifetime(200)
                    .disableDistanceSpawn()
                    .repeat(level, pos.x(), pos.y(), pos.z(), 1);
            ScreenshakeHandler.addScreenshake(new ScreenshakeInstance(200).setIntensity(1f, 0).setEasing(Easing.QUINTIC_IN_OUT));
        }

        if (mode == 23) {
            Vec3 pos = startPos.add(lookPos.scale(15f));
            ParticleBuilder builder = ParticleBuilder.create(FluffyFurParticles.WISP)
                    .setColorData(ColorParticleData.create(Color.WHITE).build())
                    .setTransparencyData(GenericParticleData.create(1, 0).setEasing(Easing.QUARTIC_OUT).build())
                    .setScaleData(GenericParticleData.create(0.1f).build())
                    .setLifetime(20, 5);
            ParticleBuilder blushBuilder = ParticleBuilder.create(FluffyFurParticles.WISP)
                    .setColorData(ColorParticleData.create(Color.RED).build())
                    .setTransparencyData(GenericParticleData.create(1, 0).setEasing(Easing.QUARTIC_OUT).build())
                    .setScaleData(GenericParticleData.create(0.1f).build())
                    .setLifetime(20, 5);
            builder.spawnBoykisser(level, pos, 256, 256, 0.075f, blushBuilder, 3, 0.15f, 0.3f, 0.15f, 0.15f);
        }

        if (mode == 24 && isSamePlayer) {
            DepthPostProcess.INSTANCE.toggle();
        }

        if (mode == 25) {
            Vec3 pos = startPos.add(lookPos.scale(15f));
            GlowPostProcess.INSTANCE.addInstance(new GlowPostProcessInstance(pos.toVector3f(), new Vector3f(1, 0, 1)).setRadius(25).setIntensity(5).setFadeTime(50));
        }

        if (mode == 26) {
            Vec3 pos = startPos.add(lookPos.scale(15f));
            NormalGlowPostProcess.INSTANCE.addInstance(new GlowPostProcessInstance(pos.toVector3f(), new Vector3f(1, 1, 0)).setRadius(25).setIntensity(5).setFadeTime(50));
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, TestShrimpPacket.class, TestShrimpPacket::encode, TestShrimpPacket::decode, TestShrimpPacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(x1);
        buf.writeDouble(y1);
        buf.writeDouble(z1);
        buf.writeDouble(x2);
        buf.writeDouble(y2);
        buf.writeDouble(z2);
        buf.writeInt(mode);
        buf.writeUUID(uuid);
    }

    public static TestShrimpPacket decode(FriendlyByteBuf buf) {
        return new TestShrimpPacket(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readInt(), buf.readUUID());
    }
}
