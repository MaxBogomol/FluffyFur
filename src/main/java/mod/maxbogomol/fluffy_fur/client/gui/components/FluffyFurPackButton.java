package mod.maxbogomol.fluffy_fur.client.gui.components;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurPackSelectionScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class FluffyFurPackButton extends Button {
    public static final ResourceLocation PACK_LOCATION = new ResourceLocation(FluffyFur.MOD_ID, "textures/gui/menu/button/pack.png");
    public static final ResourceLocation RESOURCE_PACK_LOCATION = new ResourceLocation(FluffyFur.MOD_ID, "textures/gui/menu/button/resource_pack.png");
    public static final ResourceLocation DATA_PACK_LOCATION = new ResourceLocation(FluffyFur.MOD_ID, "textures/gui/menu/button/data_pack.png");

    public FluffyFurPackButton(int x, int y) {
        super(x, y, 20, 20, Component.empty(), FluffyFurPackButton::click, DEFAULT_NARRATION);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
        ResourceLocation resourceLocation = PACK_LOCATION;
        if (isResourcePack()) resourceLocation = RESOURCE_PACK_LOCATION;
        if (isDataPack()) resourceLocation = DATA_PACK_LOCATION;
        guiGraphics.blit(resourceLocation, getX() + 2, getY() + 2, 0, 0, 16, 16, 16, 16);
    }

    public static void click(Button button) {
        Minecraft.getInstance().setScreen(new FluffyFurPackSelectionScreen(Minecraft.getInstance().screen));
    }

    @Override
    public boolean isFocused() {
        return false;
    }

    public boolean isResourcePack() {
        return Minecraft.getInstance().screen.getTitle().getString().equals(I18n.get("resourcePack.title"));
    }

    public boolean isDataPack() {
        return Minecraft.getInstance().screen.getTitle().getString().equals(I18n.get("dataPack.title"));
    }
}
