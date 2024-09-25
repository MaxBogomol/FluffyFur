package mod.maxbogomol.fluffy_fur.client.model.item;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class CustomFinalisedModel implements BakedModel {
    public final BakedModel parentModel;
    public final BakedModel subModel;

    public CustomFinalisedModel(BakedModel parentModel, BakedModel subModel) {
        this.parentModel = parentModel;
        this.subModel = subModel;
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction direction, RandomSource random) {
        if (direction != null) {
            return parentModel.getQuads(state, direction, random);
        }

        List<BakedQuad> combinedQuadsList = new ArrayList<>(parentModel.getQuads(state, direction, random));
        combinedQuadsList.addAll(subModel.getQuads(state, direction, random));
        return combinedQuadsList;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return parentModel.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return parentModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return parentModel.isCustomRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return parentModel.getParticleIcon();
    }

    @Override
    public ItemTransforms getTransforms() {
        return parentModel.getTransforms();
    }

    @Override
    public ItemOverrides getOverrides() {
        throw new UnsupportedOperationException("The finalised model does not have an override list.");
    }
}