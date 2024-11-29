package mod.maxbogomol.fluffy_fur.client.render;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;

public class FluffyFurRenderType extends RenderType {
    public final CompositeState state;

    public static FluffyFurRenderType createRenderType(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, CompositeState state) {
        return new FluffyFurRenderType(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, state);
    }

    public FluffyFurRenderType(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, CompositeState state) {
        super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, () -> {
            state.states.forEach(RenderStateShard::setupRenderState);
        }, () -> {
            state.states.forEach(RenderStateShard::clearRenderState);
        });
        this.state = state;
    }
}
