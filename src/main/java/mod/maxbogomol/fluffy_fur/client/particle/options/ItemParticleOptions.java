package mod.maxbogomol.fluffy_fur.client.particle.options;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.item.ItemStack;

public class ItemParticleOptions extends GenericParticleOptions {
    public final ItemStack stack;

    public ItemParticleOptions(ParticleType<?> type, ItemStack stack) {
        super(type);
        this.stack = stack;
    }
}
