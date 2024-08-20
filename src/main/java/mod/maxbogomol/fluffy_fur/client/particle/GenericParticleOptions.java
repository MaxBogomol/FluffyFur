package mod.maxbogomol.fluffy_fur.client.particle;

import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.LightParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.utils.RenderUtils;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

import java.awt.*;

public class GenericParticleOptions implements ParticleOptions {

    ParticleType<?> type;

    public RenderType renderType = RenderUtils.GLOWING_PARTICLE;

    public static final ColorParticleData DEFAULT_COLOR = ColorParticleData.create(Color.WHITE, Color.WHITE).build();
    public static final GenericParticleData DEFAULT_GENERIC = GenericParticleData.create(1, 0).build();
    public static final SpinParticleData DEFAULT_SPIN = SpinParticleData.create(0).build();
    public static final LightParticleData DEFAULT_LIGHT = LightParticleData.GLOW;

    public ColorParticleData colorData = DEFAULT_COLOR;
    public GenericParticleData transparencyData = DEFAULT_GENERIC;
    public GenericParticleData scaleData = DEFAULT_GENERIC;
    public SpinParticleData spinData = DEFAULT_SPIN;
    public LightParticleData lightData = DEFAULT_LIGHT;

    public int lifetime = 20;
    public int additionalLifetime = 0;
    public float gravity = 0;
    public float additionalGravity = 0;
    public float friction = 0.98f;
    public float additionalFriction = 0;
    public boolean shouldCull = false;
    public boolean hasPhysics = true;
    public float randomSpin = 0;

    public GenericParticleOptions(ParticleType<?> type) {
        this.type = type;
    }

    @Override
    public ParticleType<?> getType() {
        return type;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer) {

    }

    @Override
    public String writeToString() {
        return getClass().getSimpleName() + ":internal";
    }
}