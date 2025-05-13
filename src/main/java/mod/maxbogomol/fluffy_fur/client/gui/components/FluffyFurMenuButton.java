package mod.maxbogomol.fluffy_fur.client.gui.components;

import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurMenuScreen;
import mod.maxbogomol.fluffy_fur.config.FluffyFurClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public static class SingleMenuRow {
        public final String left, right;

        public SingleMenuRow(String left, String right) {
            this.left = left;
            this.right = right;
        }

        public SingleMenuRow(String center) {
            this(center, center);
        }
    }

    public static class MenuRows {
        public static final MenuRows MAIN_MENU = new MenuRows(Arrays.asList(
                new SingleMenuRow("menu.singleplayer"),
                new SingleMenuRow("menu.multiplayer"),
                new SingleMenuRow("fml.menu.mods", "menu.online"),
                new SingleMenuRow("narrator.button.language", "narrator.button.accessibility")
        ));

        protected final List<String> leftButtons, rightButtons;

        public MenuRows(List<SingleMenuRow> variants) {
            leftButtons = variants.stream().map(r -> r.left).collect(Collectors.toList());
            rightButtons = variants.stream().map(r -> r.right).collect(Collectors.toList());
        }
    }

    @EventBusSubscriber(value = Dist.CLIENT)
    public static class OpenConfigButtonHandler {
        @SubscribeEvent
        public static void onGuiInit(ScreenEvent.Init event) {
            if (FluffyFurClientConfig.MENU_BUTTON.get()) {
                Screen gui = event.getScreen();

                MenuRows menu = null;
                int rowIdx = 0, offsetX = 0, offsetFreeX = 0, offsetFreeY = 0;
                if (gui instanceof TitleScreen) {
                    menu = MenuRows.MAIN_MENU;
                    rowIdx = FluffyFurClientConfig.MENU_BUTTON_ROW.get();
                    offsetX = FluffyFurClientConfig.MENU_BUTTON_ROW_X_OFFSET.get();
                    offsetFreeX = FluffyFurClientConfig.MENU_BUTTON_X_OFFSET.get();
                    offsetFreeY = FluffyFurClientConfig.MENU_BUTTON_Y_OFFSET.get();
                }

                if (menu != null) {
                    boolean onLeft = offsetX < 0;
                    String target = (onLeft ? menu.leftButtons : menu.rightButtons).get(rowIdx - 1);

                    int offsetX_ = offsetX;
                    int offsetFreeX_ = offsetFreeX;
                    int offsetFreeY_ = offsetFreeY;
                    MutableObject<GuiEventListener> toAdd = new MutableObject<>(null);
                    event.getListenersList()
                            .stream()
                            .filter(w -> w instanceof AbstractWidget)
                            .map(w -> (AbstractWidget) w)
                            .filter(w -> w.getMessage()
                                    .getString()
                                    .equals(I18n.get(target)))
                            .findFirst()
                            .ifPresent(w -> toAdd
                                    .setValue(new FluffyFurMenuButton(w.getX() + offsetX_ + (onLeft ? -20 : w.getWidth()) + offsetFreeX_, w.getY() + offsetFreeY_)));
                    if (toAdd.getValue() != null) {
                        event.addListener(toAdd.getValue());
                    }
                }
            }
        }
    }
}
