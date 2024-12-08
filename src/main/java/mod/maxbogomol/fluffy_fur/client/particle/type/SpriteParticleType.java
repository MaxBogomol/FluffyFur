package mod.maxbogomol.fluffy_fur.client.particle.type;

import mod.maxbogomol.fluffy_fur.client.particle.SpriteParticle;
import mod.maxbogomol.fluffy_fur.client.particle.options.SpriteParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;

public class SpriteParticleType extends AbstractParticleType<SpriteParticleOptions> {

    public SpriteParticleType() {
        super();
    }

    public static class Factory implements ParticleProvider<SpriteParticleOptions> {
        public Factory(SpriteSet sprite) {}

        @Override
        public Particle createParticle(SpriteParticleOptions options, ClientLevel level, double x, double y, double z, double mx, double my, double mz) {
            return new SpriteParticle(level, options, x, y, z, mx, my, mz);
        }
    }
}