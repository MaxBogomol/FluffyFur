package mod.maxbogomol.fluffy_fur.client.particle.options;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.ParticleType;

public class SpriteParticleOptions extends GenericParticleOptions {
    public final TextureAtlasSprite sprite;

    public SpriteParticleOptions(ParticleType<?> type, TextureAtlasSprite sprite) {
        super(type);
        this.sprite = sprite;
    }
}
