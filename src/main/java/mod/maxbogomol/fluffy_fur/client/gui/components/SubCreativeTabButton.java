package mod.maxbogomol.fluffy_fur.client.gui.components;

import mod.maxbogomol.fluffy_fur.client.event.FluffyFurClientEvents;
import mod.maxbogomol.fluffy_fur.common.creativetab.MultiCreativeTab;
import mod.maxbogomol.fluffy_fur.common.creativetab.SubCreativeTab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubCreativeTabButton extends Button {
    public CreativeModeInventoryScreen screen;
    public MultiCreativeTab multiTab;
    public SubCreativeTab subTab;
    public int ry;
    public int rx;
    public int arrow = 0;

    public SubCreativeTabButton(CreativeModeInventoryScreen screen, MultiCreativeTab multiTab, SubCreativeTab subTab, int x, int y, int ry) {
        super(x, y, 20, 20, Component.empty(), SubCreativeTabButton::click, DEFAULT_NARRATION);
        this.screen = screen;
        this.multiTab = multiTab;
        this.subTab = subTab;
        this.rx = x;
        this.ry = ry;
        refreshSub();
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (arrow == 0) {
            if (subTab.getSubTabImage() == null) {
                super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
            } else {
                guiGraphics.blit(subTab.getSubTabImage(), this.getX(), this.getY(), isHoveredOrFocused() ? 20 : 0, 0, 20, 20, 64, 64);
            }
            if (subTab.getSubIconItem() != null) {
                guiGraphics.renderItem(subTab.getSubIconItem(), this.getX() + 2, this.getY() + 2);
            } else if (multiTab.getIconItem() != null) {
                guiGraphics.renderItem(multiTab.getIconItem(), this.getX() + 2, this.getY() + 2);
            }

            if (mouseX >= this.getX() && mouseY >= this.getY() && mouseX <= this.getX() + 20 && mouseY < this.getY() + 20) {
                List<Component> list = new ArrayList<>();
                if (subTab.getSubDisplayName() != null) {
                    list.add(subTab.getSubDisplayName());
                } else if (multiTab.getDisplayName() != null) {
                    list.add(multiTab.getDisplayName());
                }
                guiGraphics.renderTooltip(Minecraft.getInstance().font, list, Optional.empty(), mouseX, mouseY);
            }
        } else {
            guiGraphics.blit(multiTab.getSubArrowsImage(), this.getX(), this.getY(), isHoveredOrFocused() ? 20 : 0, arrow == 1 ? 20 : 0, 20, 20, 64, 64);
        }
    }

    public static void click(Button button) {
        if (button instanceof SubCreativeTabButton subButton) {
            if (subButton.arrow == 0) {
                subButton.multiTab.setCurrentSubTab(subButton.subTab);
                subButton.screen.scrollOffs = 0;
                subButton.screen.refreshCurrentTabContents(subButton.multiTab.getDisplayItems());
            } else {
                subButton.multiTab.scroll = subButton.multiTab.scroll + subButton.arrow;
            }
            for (SubCreativeTabButton sb : FluffyFurClientEvents.subCreativeTabButtons) {
                if (sb.multiTab.equals(subButton.multiTab)) {
                    sb.refreshSub();
                }
            }
        }
    }

    @Override
    public boolean isFocused() {
        return false;
    }

    public void refreshSub() {
        refreshSubVisible(CreativeModeInventoryScreen.selectedTab);
        if (multiTab.getCurrentSubTab().equals(subTab) && arrow == 0) {
            setX(rx - 4);
        } else {
            setX(rx);
        }
        if (multiTab.getSortedSubTabs().contains(subTab)) {
            int i = multiTab.getSortedSubTabs().indexOf(subTab);
            setY(ry + ((i - multiTab.scroll) * 22));
        }
    }

    public void refreshSubVisible(CreativeModeTab tab) {
        boolean exist = tab.equals(multiTab);
        if (exist) {
            if (multiTab.getSortedSubTabs().contains(subTab)) {
                int i = multiTab.getSortedSubTabs().indexOf(subTab);
                visible = i - multiTab.scroll >= -1 && i - multiTab.scroll < 7;
                arrow = 0;
                if (multiTab.scroll > 0) {
                    if (i - multiTab.scroll == -1) arrow = -1;
                }
                if (i - multiTab.scroll == 6) arrow = 1;
            } else {
                visible = false;
            }
        } else {
            visible = false;
        }
    }
}
