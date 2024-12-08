package mod.maxbogomol.fluffy_fur.client.particle.options;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.level.block.state.BlockState;

public class BlockParticleOptions extends GenericParticleOptions {
    public final BlockState blockState;

    public BlockParticleOptions(ParticleType<?> type, BlockState blockState) {
        super(type);
        this.blockState = blockState;
    }
}
