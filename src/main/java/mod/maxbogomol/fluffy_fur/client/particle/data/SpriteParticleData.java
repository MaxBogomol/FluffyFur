package mod.maxbogomol.fluffy_fur.client.particle.data;

import mod.maxbogomol.fluffy_fur.client.particle.GenericParticle;

public class SpriteParticleData {
    public static final SpriteParticleData RANDOM = new SpriteParticleData.Random();
    public static final SpriteParticleData FIRST = new SpriteParticleData.First();
    public static final SpriteParticleData LAST = new SpriteParticleData.Last();
    public static final SpriteParticleData WITH_AGE = new SpriteParticleData.WithAge();

    public void init(GenericParticle particle) {

    }

    public void tick(GenericParticle particle) {

    }

    public void renderTick(GenericParticle particle, float partialTicks) {

    }

    public float getU0(GenericParticle particle) {
        return particle.sprite.getU0();
    }

    public float getU1(GenericParticle particle) {
        return particle.sprite.getU1();
    }

    public float getV0(GenericParticle particle) {
        return particle.sprite.getV0();
    }

    public float getV1(GenericParticle particle) {
        return particle.sprite.getV1();
    }

    public static class Random extends SpriteParticleData {

        @Override
        public void init(GenericParticle particle) {
            if (particle.spriteSet != null) particle.pickSprite(particle.spriteSet);
        }
    }

    public static class First extends SpriteParticleData {

        @Override
        public void init(GenericParticle particle) {
            if (particle.spriteSet != null) particle.pickSprite(0);
        }
    }

    public static class Last extends SpriteParticleData {

        @Override
        public void init(GenericParticle particle) {
            if (particle.spriteSet != null) particle.pickSprite(particle.spriteSet.sprites.size() - 1);
        }
    }

    public static class Value extends SpriteParticleData {

        public final int value;

        public Value(int value) {
            this.value = value;
        }

        @Override
        public void init(GenericParticle particle) {
            if (particle.spriteSet != null) {
                if (value >= 0 && value < particle.spriteSet.sprites.size()) {
                    particle.pickSprite(value);
                }
            }
        }
    }

    public static class WithAge extends SpriteParticleData {

        @Override
        public void init(GenericParticle particle) {
            if (particle.spriteSet != null) particle.pickSprite(0);
        }

        @Override
        public void tick(GenericParticle particle) {
            if (particle.spriteSet != null) particle.pickSprite(particle.age * particle.spriteSet.sprites.size() / particle.lifetime);
        }
    }
}
