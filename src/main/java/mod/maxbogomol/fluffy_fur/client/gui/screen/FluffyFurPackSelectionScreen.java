package mod.maxbogomol.fluffy_fur.client.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class FluffyFurPackSelectionScreen extends Screen {
    public Screen lastScreen;

    public FluffyFurPackSelectionScreen(Screen lastScreen) {
        super(Component.empty());
        this.lastScreen = lastScreen;
    }

    @Override
    public void init() {
        addRenderableWidget(Button.builder(Component.translatable("pack.openFolder"), (button) -> {
            onClose();
        }).bounds(width / 2 - 204, height - 24, 200, 20).build());

        addRenderableWidget(Button.builder(CommonComponents.GUI_BACK, (button) -> {
            onClose();
        }).bounds(width / 2 + 4, height - 24, 98, 20).build());

        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (button) -> {
            onClose();
        }).bounds(width / 2 + 106, height - 24, 98, 20).build());
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        renderBackground(gui);

        super.render(gui, mouseX, mouseY, partialTicks);
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(lastScreen);
    }
}
