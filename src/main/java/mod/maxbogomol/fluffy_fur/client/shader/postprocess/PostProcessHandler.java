package mod.maxbogomol.fluffy_fur.client.shader.postprocess;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.client.event.RenderLevelStageEvent;

import java.util.ArrayList;
import java.util.List;

public class PostProcessHandler {
    public static final List<PostProcess> instances = new ArrayList<>();
    public static boolean didCopyDepth = false;

    public static void addInstance(PostProcess instance) {
        instances.add(instance);
    }

    public static void copyDepthBuffer() {
        if (didCopyDepth) return;
        instances.forEach(PostProcess::copyDepthBuffer);
        didCopyDepth = true;
    }

    public static void resize(int width, int height) {
        instances.forEach(i -> i.resize(width, height));
    }

    public static void onLevelRender(RenderLevelStageEvent event) {
        if (event.getStage().equals(RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS)) {
            PostProcess.viewModelStack = event.getPoseStack();
        }
        if (event.getStage().equals(RenderLevelStageEvent.Stage.AFTER_LEVEL)) {
            copyDepthBuffer();
            for (PostProcess postProcess : instances) {
                if (!postProcess.isScreen()) {
                    postProcess.applyPostProcess();
                }
            }
            didCopyDepth = false;
        }
    }

    public static void onScreenRender(GameRenderer gameRenderer, float partialTicks, long nanoTime, boolean renderLevel) {
        for (PostProcess postProcess : instances) {
            if (postProcess.isScreen() && !postProcess.isWindow()) {
                postProcess.applyPostProcess();
            }
        }
    }

    public static void onWindowRender(GameRenderer gameRenderer, float partialTicks, long nanoTime, boolean renderLevel) {
        for (PostProcess postProcess : instances) {
            if (postProcess.isScreen() && postProcess.isWindow()) {
                postProcess.applyPostProcess();
            }
        }
    }

    public static void tick() {
        for (PostProcess postProcess : instances) {
            postProcess.tick();
        }
    }
}
