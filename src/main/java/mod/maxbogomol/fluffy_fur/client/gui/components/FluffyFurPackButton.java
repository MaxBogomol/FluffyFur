package mod.maxbogomol.fluffy_fur.client.gui.components;

import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurPackSelectionScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

public class FluffyFurPackButton extends Button {
    public static ItemStack icon;

    public FluffyFurPackButton(int x, int y) {
        super(x, y, 20, 20, Component.empty(), FluffyFurPackButton::click, DEFAULT_NARRATION);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
        if (icon == null) icon = new ItemStack(Items.PINK_PETALS);
        guiGraphics.renderItem(icon, this.getX() + 2, this.getY() + 2);
    }

    public static void click(Button button) {
        Minecraft.getInstance().setScreen(new FluffyFurPackSelectionScreen(Minecraft.getInstance().screen));
    }

    @Override
    public boolean isFocused() {
        return false;
    }

    @EventBusSubscriber(value = Dist.CLIENT)
    public static class OpenConfigButtonHandler1 {
        @SubscribeEvent
        public static void onScreenInit1(ScreenEvent.Init event) {
            Screen screen = event.getScreen();

            if (screen instanceof PackSelectionScreen) {
                event.addListener(new FluffyFurPackButton(screen.width / 2 - 154 - 24, screen.height - 48));
            }
        }
    }
}
