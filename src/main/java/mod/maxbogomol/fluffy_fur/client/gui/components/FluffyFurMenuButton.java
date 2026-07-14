package mod.maxbogomol.fluffy_fur.client.gui.components;

import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurMenuScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class FluffyFurMenuButton extends Button {
    public static ItemStack icon;

    public FluffyFurMenuButton(int x, int y) {
        super(x, y, 20, 20, Component.empty(), FluffyFurMenuButton::click, DEFAULT_NARRATION);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
        if (icon == null) icon = new ItemStack(Items.PINK_PETALS);
        guiGraphics.renderItem(icon, this.getX() + 2, this.getY() + 2);
    }

    public static void click(Button button) {
        Minecraft.getInstance().setScreen(new FluffyFurMenuScreen(Minecraft.getInstance().screen));
    }

    @Override
    public boolean isFocused() {
        return false;
    }
}
