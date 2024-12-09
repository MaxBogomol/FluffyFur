package mod.maxbogomol.fluffy_fur.client.particle;

import mod.maxbogomol.fluffy_fur.client.particle.options.ItemParticleOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraftforge.client.model.data.ModelData;

public class ItemParticle extends GenericParticle {

    private final float uo;
    private final float vo;

    public ItemParticle(ClientLevel level, ItemParticleOptions options, double x, double y, double z, double vx, double vy, double vz) {
        super(level, options, null, x, y, z, vx, vy, vz);
        var model = Minecraft.getInstance().getItemRenderer().getModel(options.stack, level, null, 0);
        this.setSprite(model.getOverrides().resolve(model, options.stack, level, null, 0).getParticleIcon(ModelData.EMPTY));
        this.uo = this.random.nextFloat() * 3.0F;
        this.vo = this.random.nextFloat() * 3.0F;
    }

    @Override
    public float getU0() {
        return this.sprite.getU(((this.uo + 1.0F) / 4.0F * 16.0F));
    }

    @Override
    public float getU1() {
        return this.sprite.getU((this.uo / 4.0F * 16.0F));
    }

    @Override
    public float getV0() {
        return this.sprite.getV((this.vo / 4.0F * 16.0F));
    }

    @Override
    public float getV1() {
        return this.sprite.getV(((this.vo + 1.0F) / 4.0F * 16.0F));
    }
}
