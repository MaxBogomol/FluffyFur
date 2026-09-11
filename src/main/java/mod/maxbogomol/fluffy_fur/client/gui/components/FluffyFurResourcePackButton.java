package mod.maxbogomol.fluffy_fur.client.gui.components;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurPackSelectionScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.PackRepository;

public class FluffyFurResourcePackButton extends Button {
    public static final ResourceLocation RESOURCE_PACK_LOCATION = new ResourceLocation(FluffyFur.MOD_ID, "textures/gui/menu/button/resource_pack.png");

    public FluffyFurResourcePackButton(int x, int y) {
        super(x, y, 20, 20, Component.empty(), FluffyFurResourcePackButton::click, DEFAULT_NARRATION);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.blit(RESOURCE_PACK_LOCATION, getX() + 2, getY() + 2, 0, 0, 16, 16, 16, 16);
    }

    public static void click(Button button) {
        Minecraft minecraft = Minecraft.getInstance();
        FluffyFurPackSelectionScreen screen = new FluffyFurPackSelectionScreen(Minecraft.getInstance().screen);
        screen.setTitleName(Component.translatable("resourcePack.title"));
        screen.setPackSelectionModel(minecraft.getResourcePackRepository(), FluffyFurResourcePackButton::applyPacks);
        screen.setPath(minecraft.getResourcePackDirectory());
        minecraft.setScreen(screen);
    }

    public static void applyPacks(PackRepository packRepository) {
        Minecraft.getInstance().options.updateResourcePacks(packRepository);
    }

    @Override
    public boolean isFocused() {
        return false;
    }
}
