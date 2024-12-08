package mod.maxbogomol.fluffy_fur.client.particle.options;

import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.fluids.FluidStack;

public class FluidParticleOptions extends GenericParticleOptions {
    public final FluidStack fluidStack;
    public final boolean flowing;

    public FluidParticleOptions(ParticleType<?> type, FluidStack fluidStack, boolean flowing) {
        super(type);
        this.fluidStack = fluidStack;
        this.flowing = flowing;
    }
}
