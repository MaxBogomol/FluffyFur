package mod.maxbogomol.fluffy_fur.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraftforge.client.model.data.ModelData;

public class ItemParticle extends GenericParticle {

    private final float uo;
    private final float vo;

    public ItemParticle(ClientLevel level, ItemParticleOptions data, double x, double y, double z, double vx, double vy, double vz) {
        super(level, data, x, y, z, vx, vy, vz);
        var model = Minecraft.getInstance().getItemRenderer().getModel(data.stack, level, null, 0);
        this.setSprite(model.getOverrides().resolve(model, data.stack, level, null, 0).getParticleIcon(ModelData.EMPTY));
        this.quadSize /= 2.0F;
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
