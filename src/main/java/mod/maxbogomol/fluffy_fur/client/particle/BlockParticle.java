package mod.maxbogomol.fluffy_fur.client.particle;

import mod.maxbogomol.fluffy_fur.client.particle.options.BlockParticleOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraftforge.client.model.data.ModelData;

public class BlockParticle extends GenericParticle {

    private final float uo;
    private final float vo;

    public BlockParticle(ClientLevel level, BlockParticleOptions options, double x, double y, double z, double vx, double vy, double vz) {
        super(level, options, null, x, y, z, vx, vy, vz);
        var model = Minecraft.getInstance().getBlockRenderer().getBlockModel(options.blockState);
        this.setSprite(model.getParticleIcon(ModelData.EMPTY));
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
