package mod.maxbogomol.fluffy_fur.client.shader.postprocess;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;

public class SimplePostProcess extends PostProcess {
    public ResourceLocation resourceLocation;
    public boolean screen = false;
    public boolean window = false;

    public SimplePostProcess(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public SimplePostProcess enableScreen() {
        return setScreen(true);
    }

    public SimplePostProcess disableScreen() {
        return setScreen(false);
    }

    public SimplePostProcess setScreen(boolean screen) {
        this.screen = screen;
        return this;
    }

    public SimplePostProcess enableWindow() {
        return setWindow(true);
    }

    public SimplePostProcess disableWindow() {
        return setWindow(false);
    }

    public SimplePostProcess setWindow(boolean window) {
        this.window = window;
        return this;
    }

    @Override
    public ResourceLocation getPostChainLocation() {
        return resourceLocation;
    }

    @Override
    public void beforeProcess(PoseStack viewModelStack) {

    }

    @Override
    public void afterProcess() {

    }

    @Override
    public boolean isScreen() {
        return screen;
    }

    @Override
    public boolean isWindow() {
        return window;
    }
}
