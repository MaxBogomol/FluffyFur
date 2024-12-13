package mod.maxbogomol.fluffy_fur.client.particle;

import mod.maxbogomol.fluffy_fur.client.particle.options.ItemParticleOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraftforge.client.model.data.ModelData;

public class ItemParticle extends GenericParticle {

    public ItemParticle(ClientLevel level, ItemParticleOptions options, double x, double y, double z, double vx, double vy, double vz) {
        super(level, options, null, x, y, z, vx, vy, vz);
        var model = Minecraft.getInstance().getItemRenderer().getModel(options.stack, level, null, 0);
        this.setSprite(model.getOverrides().resolve(model, options.stack, level, null, 0).getParticleIcon(ModelData.EMPTY));
    }
}
