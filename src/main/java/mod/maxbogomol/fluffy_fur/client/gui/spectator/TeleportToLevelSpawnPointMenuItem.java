package mod.maxbogomol.fluffy_fur.client.gui.spectator;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.common.network.FluffyFurPacketHandler;
import mod.maxbogomol.fluffy_fur.common.network.spectator.TeleportToLevelSpawnPointPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.SpectatorMenuItem;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TeleportToLevelSpawnPointMenuItem implements SpectatorMenuItem {
    public static final ResourceLocation SPECTATOR_LOCATION = new ResourceLocation(FluffyFur.MOD_ID, "textures/gui/spectator_widgets.png");
    public static final Component TELEPORT_TEXT = Component.translatable("gui.spectator_menu.level_spawn_point_teleport");

    @Override
    public void selectItem(SpectatorMenu menu) {
        FluffyFurPacketHandler.sendToServer(new TeleportToLevelSpawnPointPacket());
    }

    @Override
    public Component getName() {
        return TELEPORT_TEXT;
    }

    @Override
    public void renderIcon(GuiGraphics guiGraphics, float shadeColor, int alpha) {
        guiGraphics.blit(SPECTATOR_LOCATION, 0, 0, 16, 0, 16, 16, 64, 64);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
