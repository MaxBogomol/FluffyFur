package mod.maxbogomol.fluffy_fur.client.gui.spectator;

import mod.maxbogomol.fluffy_fur.client.spectator.SpectatorDimensionHandler;
import mod.maxbogomol.fluffy_fur.client.spectator.SpectatorDimensionType;
import mod.maxbogomol.fluffy_fur.common.network.FluffyFurPacketHandler;
import mod.maxbogomol.fluffy_fur.common.network.spectator.TeleportToDimensionPacket;
import mod.maxbogomol.fluffy_fur.config.FluffyFurServerConfig;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurSpectatorDimensionTypes;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.SpectatorMenuItem;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class TeleportToDimensionMenuItem implements SpectatorMenuItem {
    public static final Component TELEPORT_TEXT = Component.translatable("gui.spectator_menu.dimension_teleport.unknown");
    public final ResourceKey<Level> dimension;

    public TeleportToDimensionMenuItem(ResourceKey<Level> dimension) {
        this.dimension = dimension;
    }

    @Override
    public void selectItem(SpectatorMenu menu) {
        FluffyFurPacketHandler.sendToServer(new TeleportToDimensionPacket(dimension.location()));
    }

    @Override
    public Component getName() {
        if (SpectatorDimensionHandler.levelDimensionSaves.get(dimension)) {
            return Component.literal(dimension.location().toString());
        }
        return TELEPORT_TEXT;
    }

    @Override
    public void renderIcon(GuiGraphics guiGraphics, float shadeColor, int alpha) {
        boolean render = false;
        for (SpectatorDimensionType dimensionType : SpectatorDimensionHandler.getDimensions()) {
            if (dimensionType.dimension.equals(dimension)) {
                if (SpectatorDimensionHandler.levelDimensionSaves.get(dimension)) {
                    dimensionType.renderIcon(guiGraphics, shadeColor, alpha);
                } else {
                    FluffyFurSpectatorDimensionTypes.UNKNOWN.renderIcon(guiGraphics, shadeColor, alpha);
                }
                render = true;
                break;
            }
        }
        if (!render) {
            if (SpectatorDimensionHandler.levelDimensionSaves.get(dimension)) {
                FluffyFurSpectatorDimensionTypes.OTHER.renderIcon(guiGraphics, shadeColor, alpha);
            } else {
                FluffyFurSpectatorDimensionTypes.UNKNOWN.renderIcon(guiGraphics, shadeColor, alpha);
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return FluffyFurServerConfig.TELEPORT_TO_DIMENSION_SPECTATOR.get();
    }
}
