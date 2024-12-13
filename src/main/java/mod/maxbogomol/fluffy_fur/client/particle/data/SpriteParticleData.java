package mod.maxbogomol.fluffy_fur.client.particle.data;

import mod.maxbogomol.fluffy_fur.client.particle.GenericParticle;

public class SpriteParticleData {
    public static final SpriteParticleData RANDOM = new SpriteParticleData.Random();
    public static final SpriteParticleData FIRST = new SpriteParticleData.First();
    public static final SpriteParticleData LAST = new SpriteParticleData.Last();
    public static final SpriteParticleData WITH_AGE = new SpriteParticleData.WithAge();

    public static final SpriteParticleData CRUMBS_RANDOM = new SpriteParticleData.CrumbsRandom(3, 1);
    public static final SpriteParticleData CRUMBS_FIRST = new SpriteParticleData.CrumbsFirst(3, 1);
    public static final SpriteParticleData CRUMBS_LAST = new SpriteParticleData.CrumbsLast(3, 1);
    public static final SpriteParticleData CRUMBS_WITH_AGE = new SpriteParticleData.CrumbsWithAge(3, 1);

    public static final SpriteParticleData CRUMBS_LARGE_RANDOM = new SpriteParticleData.CrumbsRandom(1, 1);
    public static final SpriteParticleData CRUMBS_LARGE_FIRST = new SpriteParticleData.CrumbsFirst(1, 1);
    public static final SpriteParticleData CRUMBS_LARGE_LAST = new SpriteParticleData.CrumbsLast(1, 1);
    public static final SpriteParticleData CRUMBS_LARGE_WITH_AGE = new SpriteParticleData.CrumbsWithAge(1, 1);

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

        public int value;

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

    public static class Crumbs extends SpriteParticleData {

        public float offset;
        public float size;

        public Crumbs(float offset, float size) {
            this.offset = offset;
            this.size = size;
        }


        @Override
        public float getU0(GenericParticle particle) {
            return particle.sprite.getU0() + ((particle.sprite.getU1() - particle.sprite.getU0()) * ((particle.uo * offset + size) / (offset + size)));
        }

        @Override
        public float getU1(GenericParticle particle) {
            return particle.sprite.getU0() + ((particle.sprite.getU1() - particle.sprite.getU0()) * ((particle.uo * offset) / (offset + size)));
        }

        @Override
        public float getV0(GenericParticle particle) {
            return particle.sprite.getV0() + ((particle.sprite.getV1() - particle.sprite.getV0()) * ((particle.vo * offset) / (offset + size)));
        }

        @Override
        public float getV1(GenericParticle particle) {
            return particle.sprite.getV0() + ((particle.sprite.getV1() - particle.sprite.getV0()) * ((particle.vo * offset + size) / (offset + size)));
        }
    }

    public static class CrumbsRandom extends Crumbs {

        public CrumbsRandom(float offset, float size) {
            super(offset, size);
        }

        @Override
        public void init(GenericParticle particle) {
            if (particle.spriteSet != null) particle.pickSprite(particle.spriteSet);
        }
    }

    public static class CrumbsFirst extends Crumbs {

        public CrumbsFirst(float offset, float size) {
            super(offset, size);
        }

        @Override
        public void init(GenericParticle particle) {
            if (particle.spriteSet != null) particle.pickSprite(0);
        }
    }

    public static class CrumbsLast extends Crumbs {

        public CrumbsLast(float offset, float size) {
            super(offset, size);
        }

        @Override
        public void init(GenericParticle particle) {
            if (particle.spriteSet != null) particle.pickSprite(particle.spriteSet.sprites.size() - 1);
        }
    }

    public static class CrumbsValue extends Crumbs {

        public int value;

        public CrumbsValue(int value, float offset, float size) {
            super(offset, size);
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

    public static class CrumbsWithAge extends Crumbs {

        public CrumbsWithAge(float offset, float size) {
            super(offset, size);
        }

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
