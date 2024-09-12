package mod.maxbogomol.fluffy_fur.client.particle.type;

import mod.maxbogomol.fluffy_fur.client.particle.SphereParticle;
import mod.maxbogomol.fluffy_fur.client.particle.options.SphereParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;

public class SphereParticleType extends AbstractParticleType<SphereParticleOptions> {

    public SphereParticleType() {
        super();
    }

    public static class Factory implements ParticleProvider<SphereParticleOptions> {
        private final SpriteSet sprite;

        public Factory(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Override
        public Particle createParticle(SphereParticleOptions options, ClientLevel level, double x, double y, double z, double mx, double my, double mz) {
            return new SphereParticle(level, options, (ParticleEngine.MutableSpriteSet) sprite, x, y, z, mx, my, mz);
        }
    }
}