package mod.maxbogomol.fluffy_fur.client.gui.components;

import mod.maxbogomol.fluffy_fur.config.FluffyFurClientConfig;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraftforge.client.event.ScreenEvent;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FluffyFurButtonsHandler {

    public static void onScreenInit(ScreenEvent.Init event) {
        Screen screen = event.getScreen();

        boolean fluffyFurButton = false;
        boolean packButton = false;

        MenuRows menu = null;
        int rowIdx = 1, offsetX = 0, offsetFreeX = 0, offsetFreeY = 0;
        if (FluffyFurClientConfig.MENU_BUTTON.get()) {
            if (screen instanceof TitleScreen) {
                fluffyFurButton = true;
                menu = MenuRows.MAIN_MENU;
                rowIdx = FluffyFurClientConfig.MENU_BUTTON_ROW.get();
                offsetX = FluffyFurClientConfig.MENU_BUTTON_ROW_X_OFFSET.get();
                offsetFreeX = FluffyFurClientConfig.MENU_BUTTON_X_OFFSET.get();
                offsetFreeY = FluffyFurClientConfig.MENU_BUTTON_Y_OFFSET.get();
            }
        }
        if (FluffyFurClientConfig.PAUSE_BUTTON.get()) {
            if (screen instanceof PauseScreen) {
                fluffyFurButton = true;
                menu = MenuRows.PAUSE_MENU;
                rowIdx = FluffyFurClientConfig.PAUSE_BUTTON_ROW.get();
                offsetX = FluffyFurClientConfig.PAUSE_BUTTON_ROW_X_OFFSET.get();
                offsetFreeX = FluffyFurClientConfig.PAUSE_BUTTON_X_OFFSET.get();
                offsetFreeY = FluffyFurClientConfig.PAUSE_BUTTON_Y_OFFSET.get();
            }
        }
        if (screen instanceof PackSelectionScreen) {
            menu = MenuRows.SELECT_PACK_MENU;
            packButton = true;
            offsetX = - 4;
        }

        if (menu != null) {
            boolean onLeft = offsetX < 0;
            String target = (onLeft ? menu.leftButtons : menu.rightButtons).get(rowIdx - 1);

            boolean fluffyFurButton_ = fluffyFurButton;
            boolean packButton_ = packButton;

            int offsetX_ = offsetX;
            int offsetFreeX_ = offsetFreeX;
            int offsetFreeY_ = offsetFreeY;
            MutableObject<GuiEventListener> toAdd = new MutableObject<>(null);
            event.getListenersList()
                    .stream()
                    .filter(w -> w instanceof AbstractWidget)
                    .map(w -> (AbstractWidget) w)
                    .filter(w -> w.getMessage().getString().equals(I18n.get(target)))
                    .findFirst()
                    .ifPresent(w -> {
                        if (fluffyFurButton_) {
                            toAdd.setValue(new FluffyFurMenuButton(w.getX() + offsetX_ + (onLeft ? -20 : w.getWidth()) + offsetFreeX_, w.getY() + offsetFreeY_));
                        }
                        if (packButton_) {
                            toAdd.setValue(new FluffyFurPackButton(w.getX() + offsetX_ + (onLeft ? -20 : w.getWidth()) + offsetFreeX_, w.getY() + offsetFreeY_));
                        }
                    });
            if (toAdd.getValue() != null) {
                event.addListener(toAdd.getValue());
            }
        }
    }

    public static class MenuRows {
        public static final MenuRows MAIN_MENU = new MenuRows(Arrays.asList(
                new SingleMenuRow("menu.singleplayer"),
                new SingleMenuRow("menu.multiplayer"),
                new SingleMenuRow("fml.menu.mods", "menu.online"),
                new SingleMenuRow("narrator.button.language", "narrator.button.accessibility")
        ));

        public static final MenuRows PAUSE_MENU = new MenuRows(Arrays.asList(
                new SingleMenuRow("menu.returnToGame"),
                new SingleMenuRow("gui.advancements", "gui.stats"),
                new SingleMenuRow("menu.sendFeedback", "menu.reportBugs"),
                new SingleMenuRow("menu.options", "menu.shareToLan"),
                new SingleMenuRow("fml.menu.mods"),
                new SingleMenuRow("menu.returnToMenu")
        ));

        public static final MenuRows SELECT_PACK_MENU = new MenuRows(List.of(
                new SingleMenuRow("pack.openFolder", "gui.done")
        ));

        protected final List<String> leftButtons, rightButtons;

        public MenuRows(List<SingleMenuRow> variants) {
            leftButtons = variants.stream().map(r -> r.left).collect(Collectors.toList());
            rightButtons = variants.stream().map(r -> r.right).collect(Collectors.toList());
        }
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
}
