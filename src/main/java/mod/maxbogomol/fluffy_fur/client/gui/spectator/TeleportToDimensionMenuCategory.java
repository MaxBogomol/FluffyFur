package mod.maxbogomol.fluffy_fur.client.gui.spectator;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.spectator.SpectatorDimensionHandler;
import mod.maxbogomol.fluffy_fur.config.FluffyFurServerConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.SpectatorMenuCategory;
import net.minecraft.client.gui.spectator.SpectatorMenuItem;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class TeleportToDimensionMenuCategory implements SpectatorMenuCategory, SpectatorMenuItem {
    public static final ResourceLocation SPECTATOR_LOCATION = new ResourceLocation(FluffyFur.MOD_ID, "textures/gui/spectator_widgets.png");
    public static final Component TELEPORT_TEXT = Component.translatable("gui.spectator_menu.dimension_teleport");
    public static final Component TELEPORT_PROMPT = Component.translatable("gui.spectator_menu.dimension_teleport.prompt");
    public final ArrayList<SpectatorMenuItem> items = new ArrayList<>();

    @Override
    public void selectItem(SpectatorMenu menu) {
        createItems();
        menu.selectCategory(this);
    }

    @Override
    public Component getName() {
        return TELEPORT_TEXT;
    }

    @Override
    public void renderIcon(GuiGraphics guiGraphics, float shadeColor, int alpha) {
        guiGraphics.blit(SPECTATOR_LOCATION, 0, 0, 32, 0, 16, 16, 64, 64);
    }

    @Override
    public boolean isEnabled() {
        return FluffyFurServerConfig.TELEPORT_TO_DIMENSION_SPECTATOR.get();
    }

    @Override
    public List<SpectatorMenuItem> getItems() {
        return items;
    }

    @Override
    public Component getPrompt() {
        return TELEPORT_PROMPT;
    }

    public void createItems() {
        items.clear();
        for (ResourceKey<Level> dimension : SpectatorDimensionHandler.levelDimensions) {
            items.add(new TeleportToDimensionMenuItem(dimension));
        }
    }
}
