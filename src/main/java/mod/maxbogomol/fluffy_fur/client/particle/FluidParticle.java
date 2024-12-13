package mod.maxbogomol.fluffy_fur.client.particle;

import mod.maxbogomol.fluffy_fur.client.particle.options.FluidParticleOptions;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;

public class FluidParticle extends GenericParticle {

    public FluidParticle(ClientLevel level, FluidParticleOptions options, double x, double y, double z, double vx, double vy, double vz) {
        super(level, options, null, x, y, z, vx, vy, vz);
        if (!options.fluidStack.isEmpty()) {
            FluidType type = options.fluidStack.getFluid().getFluidType();
            IClientFluidTypeExtensions clientType = IClientFluidTypeExtensions.of(type);
            TextureAtlasSprite sprite = RenderUtil.getSprite(clientType.getStillTexture(options.fluidStack));
            if (options.flowing) sprite = RenderUtil.getSprite(clientType.getFlowingTexture(options.fluidStack));
            this.setSprite(sprite);
        } else {
            IClientFluidTypeExtensions clientType = IClientFluidTypeExtensions.of(Fluids.WATER);
            TextureAtlasSprite sprite = RenderUtil.getSprite(clientType.getStillTexture());
            this.setSprite(sprite);
        }
    }
}
