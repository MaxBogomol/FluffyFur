package mod.maxbogomol.fluffy_fur.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;

public class CubeParticleType extends AbstractParticleType<GenericParticleOptions> {
    public CubeParticleType() {
        super();
    }

    public static class Factory implements ParticleProvider<GenericParticleOptions> {
        private final SpriteSet sprite;

        public Factory(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Override
        public Particle createParticle(GenericParticleOptions options, ClientLevel level, double x, double y, double z, double mx, double my, double mz) {
            CubeParticle ret = new CubeParticle(level, options, x, y, z, mx, my, mz);
            ret.pickSprite(sprite);
            return ret;
        }
    }
}