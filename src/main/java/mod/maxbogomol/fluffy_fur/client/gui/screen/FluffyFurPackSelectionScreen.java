package mod.maxbogomol.fluffy_fur.client.gui.screen;

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
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_BACK, (button) -> {
            onClose();
        }).bounds(this.width / 2 - 80, this.height / 4 + 152, 160, 20).build());
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        this.renderDirtBackground(gui);

        super.render(gui, mouseX, mouseY, partialTicks);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.lastScreen);
    }
}
