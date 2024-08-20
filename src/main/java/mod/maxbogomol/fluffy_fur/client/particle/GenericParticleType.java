package mod.maxbogomol.fluffy_fur.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;

public class GenericParticleType extends AbstractParticleType<GenericParticleOptions> {
    public GenericParticleType() {
        super();
    }

    public static class Factory implements ParticleProvider<GenericParticleOptions> {
        private final SpriteSet sprite;

        public Factory(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Override
        public Particle createParticle(GenericParticleOptions data, ClientLevel world, double x, double y, double z, double mx, double my, double mz) {
            GenericParticle ret = new GenericParticle(world, data, x, y, z, mx, my, mz);
            ret.pickSprite(sprite);
            return ret;
        }
    }
}