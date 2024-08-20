package mod.maxbogomol.fluffy_fur.client.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

public class AbstractParticleType <T extends GenericParticleOptions> extends ParticleType<T> {

    public AbstractParticleType() {
        super(false, new ParticleOptions.Deserializer<>() {
            @Override
            public T fromCommand(ParticleType<T> type, StringReader reader) {
                return (T) new GenericParticleOptions(type);
            }

            @Override
            public T fromNetwork(ParticleType<T> type, FriendlyByteBuf buf) {
                return (T) new GenericParticleOptions(type);
            }
        });
    }

    @Override
    public Codec<T> codec() {
        return genericCodec(this);
    }

    public static <K extends GenericParticleOptions> Codec<K> genericCodec(ParticleType<K> type) {
        return Codec.unit(() -> (K) new GenericParticleOptions(type));
    }
}
