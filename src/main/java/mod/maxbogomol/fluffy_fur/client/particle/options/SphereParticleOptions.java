package mod.maxbogomol.fluffy_fur.client.particle.options;

import net.minecraft.core.particles.ParticleType;

public class SphereParticleOptions extends GenericParticleOptions {
    public final int longs;
    public final int lats;

    public SphereParticleOptions(ParticleType<?> type, int longs, int lats) {
        super(type);
        this.longs = longs;
        this.lats = lats;
    }

    public SphereParticleOptions(ParticleType<?> type, int longs) {
        super(type);
        this.longs = longs;
        this.lats = longs;
    }

    public SphereParticleOptions(ParticleType<?> type) {
        super(type);
        this.longs = 8;
        this.lats = 8;
    }
}
