package mod.maxbogomol.fluffy_fur.client.shader.postprocess;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class PostProcessHandler {
    public static final List<PostProcess> instances = new ArrayList<>();
    public static boolean didCopyDepth = false;

    public static void addInstance(PostProcess instance) {
        instances.add(instance);
    }

    public static List<PostProcess> getInstances() {
        return instances;
    }

    public static List<PostProcess> getSortedInstances() {
        List<PostProcess> sortedInstances = new ArrayList<>(instances);
        sortedInstances.sort((i1, i2) -> Float.compare(i1.getPriority(), i2.getPriority()));
        return sortedInstances;
    }

    public static void copyDepthBuffer() {
        if (didCopyDepth) return;
        instances.forEach(PostProcess::copyDepthBuffer);
        didCopyDepth = true;
    }

    public static void resize(int width, int height) {
        instances.forEach(i -> i.resize(width, height));
    }

    public static void setupRender() {
        RenderSystem.disableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.resetTextureMatrix();
    }

    public static void applyPostProcess(PostProcess postProcess) {
        setupRender();
        postProcess.applyPostProcess();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLevelRender(RenderLevelStageEvent event) {
        if (event.getStage().equals(RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS)) {
            PostProcess.viewModelStack = event.getPoseStack();
        }
        if (event.getStage().equals(RenderLevelStageEvent.Stage.AFTER_LEVEL)) {
            copyDepthBuffer();
            for (PostProcess postProcess : getSortedInstances()) {
                if (!postProcess.isScreen()) {
                    applyPostProcess(postProcess);
                }
            }
            didCopyDepth = false;
        }
    }

    public static void onScreenRender(GameRenderer gameRenderer, float partialTicks, long nanoTime, boolean renderLevel) {
        for (PostProcess postProcess : getSortedInstances()) {
            if (postProcess.isScreen() && !postProcess.isWindow()) {
                applyPostProcess(postProcess);
            }
        }
    }

    public static void onWindowRender(GameRenderer gameRenderer, float partialTicks, long nanoTime, boolean renderLevel) {
        for (PostProcess postProcess : getSortedInstances()) {
            if (postProcess.isScreen() && postProcess.isWindow()) {
                applyPostProcess(postProcess);
            }
        }
    }

    public static void tick() {
        for (PostProcess postProcess : getSortedInstances()) {
            postProcess.tick();
        }
    }
}
