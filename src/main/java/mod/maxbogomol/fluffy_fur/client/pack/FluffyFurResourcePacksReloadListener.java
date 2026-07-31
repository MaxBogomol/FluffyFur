package mod.maxbogomol.fluffy_fur.client.pack;

import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurModsHandler;
import mod.maxbogomol.fluffy_fur.client.shader.postprocess.PostProcessHandler;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

public class FluffyFurResourcePacksReloadListener extends SimplePreparableReloadListener<Object> {

    @Override
    protected Object prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        FluffyFurModsHandler.fileLoaded = false;

        return null;
    }

    @Override
    protected void apply(Object object, ResourceManager resourceManager, ProfilerFiller profiler) {
        PostProcessHandler.reload();
    }
}
