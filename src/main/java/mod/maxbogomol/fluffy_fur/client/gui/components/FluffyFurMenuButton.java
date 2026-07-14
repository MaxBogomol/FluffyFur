package mod.maxbogomol.fluffy_fur.client.gui.components;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurMenuScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class FluffyFurMenuButton extends Button {
    public static final ResourceLocation SLEEPING_FOX_LOCATION = new ResourceLocation(FluffyFur.MOD_ID, "textures/gui/menu/button/sleeping_fox.png");

    public FluffyFurMenuButton(int x, int y) {
        super(x, y, 20, 20, Component.empty(), FluffyFurMenuButton::click, DEFAULT_NARRATION);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.blit(SLEEPING_FOX_LOCATION, getX() + 2, getY() + 2, 0, 0, 16, 16, 16, 16);
    }

    public static void click(Button button) {
        Minecraft.getInstance().setScreen(new FluffyFurMenuScreen(Minecraft.getInstance().screen));
    }

    @Override
    public boolean isFocused() {
        return false;
    }
}
