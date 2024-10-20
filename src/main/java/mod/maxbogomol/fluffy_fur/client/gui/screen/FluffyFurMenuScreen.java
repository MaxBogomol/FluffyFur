package mod.maxbogomol.fluffy_fur.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.FluffyFurClient;
import mod.maxbogomol.fluffy_fur.config.FluffyFurClientConfig;
import mod.maxbogomol.fluffy_fur.client.gui.components.CustomLogoRenderer;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FluffyFurMenuScreen extends Screen {
    public Screen lastScreen;
    public CubeMap CUBE_MAP = new CubeMap(new ResourceLocation(FluffyFur.MOD_ID, "textures/gui/title/background/panorama"));
    public PanoramaRenderer panorama = new PanoramaRenderer(CUBE_MAP);
    public CustomLogoRenderer logoRenderer;
    public long fadeInStart;

    private static final ResourceLocation LOGO = new ResourceLocation(FluffyFur.MOD_ID, "textures/gui/title/fluffy_fur.png");
    public static ResourceLocation BACKGROUND = new ResourceLocation(FluffyFur.MOD_ID, "textures/gui/menu_background.png");
    public static int ticks = 0;

    public static List<FluffyFurMod> mods = new ArrayList<>();
    public static List<FluffyFurPanorama> panoramas = new ArrayList<>();

    public static int descriptionScroll = 0;
    public static int panoramasScroll = 0;
    public static int modsScroll = 0;

    public static int selectedPanorama = 0;
    public static int selectedMod = 0;

    public FluffyFurMenuScreen(Screen lastScreen) {
        super(Component.empty());
        this.lastScreen = lastScreen;
        this.logoRenderer = new CustomLogoRenderer(LOGO, false);
        if (lastScreen instanceof TitleScreen titleScreen) {
            copyPanorama(titleScreen);
        }
        this.fadeInStart = Util.getMillis();

        mods = FluffyFurModsHandler.getSortedMods();
        panoramas = FluffyFurModsHandler.getSortedPanoramas();

        FluffyFurPanorama panorama = FluffyFurModsHandler.getPanorama(FluffyFurClientConfig.CUSTOM_PANORAMA.get());
        if (panorama != null) {
            if (panoramas.contains(panorama)) {
                selectedPanorama = panoramas.indexOf(panorama);
            }
        }
        descriptionScroll = 0;
    }

    public void copyPanorama(TitleScreen titleScreen) {
        CUBE_MAP = TitleScreen.CUBE_MAP;
        panorama = new PanoramaRenderer(CUBE_MAP);

        panorama.spin = titleScreen.panorama.spin;
        panorama.bob = titleScreen.panorama.bob;
    }

    public void setLocalPanorama(FluffyFurPanorama panorama) {
        float spin = this.panorama.spin;
        float bob = this.panorama.bob;
        ResourceLocation base = new ResourceLocation("textures/gui/title/background/panorama");
        if (panorama.getTexture() != null) {
            base = panorama.getTexture();
        }
        CUBE_MAP = new CubeMap(base);
        this.panorama = new PanoramaRenderer(CUBE_MAP);
        this.panorama.spin = spin;
        this.panorama.bob = bob;
    }

    @Override
    public void init() {
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_BACK, (button) -> {
            this.minecraft.setScreen(this.lastScreen);
            if (lastScreen instanceof TitleScreen titleScreen) {
                titleScreen.panorama.spin = panorama.spin;
                titleScreen.panorama.bob = panorama.bob;
            }
        }).bounds(this.width / 2 - 80, this.height / 4 + 152, 160, 20).build());
    }

    @Override
    public void tick() {
        ticks++;
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        float f = (float) (Util.getMillis() - this.fadeInStart) / 250.0F;
        this.panorama.render(partialTicks, 1f);
        if (lastScreen instanceof TitleScreen titleScreen) {
            titleScreen.logoRenderer.renderLogo(gui, this.width, 1f - f);
        }
        this.logoRenderer.renderLogo(gui, this.width, f);

        drawDescription(gui, this.width / 2 - 80, this.height / 4 + 48, mouseX, mouseY, partialTicks);
        drawPanoramaList(gui, this.width / 2 - 204, this.height / 4 + 48, mouseX, mouseY, partialTicks);
        drawModList(gui, this.width / 2 + 84, this.height / 4 + 48, mouseX, mouseY, partialTicks);

        super.render(gui, mouseX, mouseY, partialTicks);
    }

    public static void drawDescription(GuiGraphics gui, int x, int y, int mouseX, int mouseY, float partialTicks) {
        gui.blit(BACKGROUND, x, y, 0, 0, 160, 100, 256, 256);

        FluffyFurMod mod = mods.get(selectedMod);

        Font font = Minecraft.getInstance().font;
        Component component = Component.literal(mod.getName()).withStyle(Style.EMPTY.withColor(ColorUtil.packColor(mod.getNameColor())))
                .append(" ").append(Component.literal("v" + mod.getVersion()).withStyle(Style.EMPTY.withColor(ColorUtil.packColor(mod.getVersionColor()))));
        drawBlackBackground(gui, x + 80, y - 12, font.width(component) + 8, mouseX, mouseY, partialTicks);
        gui.drawCenteredString(font, component, x + 80, y - 11, 16777215);

        List<Component> lines = getDescription(mod);
        int links = mod.getLinks().size();
        int l = lines.size() - links;
        for (int i = 0; i < 9; i++) {
            int index = descriptionScroll + i;
            if (index < 0) break;
            if (index > lines.size() - 1) break;
            MutableComponent line = Component.empty().append(lines.get(index));
            if (index >= l) {
                if (mouseX >= x + 5 && mouseY >= y + 5 + (i * (font.lineHeight + 1)) && mouseX <= x + 5 + font.width(line) && mouseY < y + 5 + (i * (font.lineHeight + 1) + font.lineHeight)) {
                    line.withStyle(ChatFormatting.UNDERLINE);
                }
            }
            gui.drawString(font, line, x + 5, y + 5 + (i * (font.lineHeight + 1)), 16777215);
        }

        int s = lines.size() - 9;
        if (s > 0) {
            float slider = (float) descriptionScroll / s;
            drawSlider(gui, x + 147, y + 4, slider, mouseX, mouseY, partialTicks);
        }
    }

    public static void drawPanoramaList(GuiGraphics gui, int x, int y, int mouseX, int mouseY, float partialTicks) {
        gui.blit(BACKGROUND, x, y, 0, 100, 120, 100, 256, 256);
        int s = panoramas.size() - 5;
        if (s > 0) {
            float slider = (float) panoramasScroll / s;
            drawSlider(gui, x + 107, y + 4, slider, mouseX, mouseY, partialTicks);
        }

        Font font = Minecraft.getInstance().font;
        Component component = Component.translatable("gui.fluffy_fur.menu.panoramas");
        drawBlackBackground(gui, x + 60, y - 12, font.width(component) + 8, mouseX, mouseY, partialTicks);
        gui.drawCenteredString(font, component, x + 60, y - 11, 16777215);

        for (int i = 0; i < 5; i++) {
            int index = panoramasScroll + i;
            if (index < 0) break;
            if (index > panoramas.size() - 1) break;
            FluffyFurPanorama panorama = panoramas.get(index);
            MutableComponent name = Component.empty().append(panorama.getName());
            if (selectedPanorama == index) {
                name.withStyle(ChatFormatting.UNDERLINE);
            }
            gui.renderItem(panorama.getItem(), x + 2, y + 2 + (i * 20));
            gui.drawString(font, name, x + 20, y + 6 + (i * 20), 16777215);

            if (mouseX >= x && mouseY >= y && mouseX <= x + 120 && mouseY < y + 100) {
                if (mouseX >= x + 2 && mouseY >= y + 2 + (i * 20) && mouseX <= x + 102 && mouseY < y + 22 + (i * 20)) {
                    List<Component> list = new ArrayList<>();
                    list.add(panorama.getName());
                    if (panorama.getMod() != null) {
                        list.add(Component.empty());
                        list.add(Component.translatable("gui.fluffy_fur.menu.mod").append(" ").append(Component.literal(panorama.getMod().getName()).withStyle(ChatFormatting.GRAY)));
                    }
                    if (panorama == FluffyFurClient.VANILLA_PANORAMA) {
                        list.add(Component.empty());
                        list.add(Component.literal("Minecraft").withStyle(ChatFormatting.GRAY));
                    }

                    gui.renderTooltip(Minecraft.getInstance().font, list, Optional.empty(), mouseX, mouseY);
                }
            }
        }
    }

    public static void drawModList(GuiGraphics gui, int x, int y, int mouseX, int mouseY, float partialTicks) {
        gui.blit(BACKGROUND, x, y, 0, 100, 120, 100, 256, 256);
        int s = mods.size() - 5;
        if (s > 0) {
            float slider = (float) modsScroll / s;
            drawSlider(gui, x + 107, y + 4, slider, mouseX, mouseY, partialTicks);
        }

        Font font = Minecraft.getInstance().font;
        Component component = Component.translatable("gui.fluffy_fur.menu.mods");
        drawBlackBackground(gui, x + 60, y - 12, font.width(component) + 8, mouseX, mouseY, partialTicks);
        gui.drawCenteredString(font, component, x + 60, y - 11, 16777215);

        for (int i = 0; i < 5; i++) {
            int index = modsScroll + i;
            if (index < 0) break;
            if (index > mods.size() - 1) break;
            FluffyFurMod mod = mods.get(index);
            gui.renderItem(mod.getItem(), x + 2, y + 2 + (i * 20));
            MutableComponent name = Component.empty().append(mod.getName());
            if (mod.getDev().equals("MaxBogomol")) {
                name = name.withStyle(Style.EMPTY.withColor(ColorUtil.packColor(ColorUtil.rainbowColor((ticks + partialTicks) * 0.005f))));
            }
            if (mod.getId().equals("valoria")) {
                name = name.withStyle(Style.EMPTY.withColor(ColorUtil.packColor(255, 253, 145, 195)));
            }
            if (mod.getId().equals("implosion")) {
                name = name.withStyle(Style.EMPTY.withColor(ColorUtil.packColor(255, 132, 190, 224)));
            }
            if (selectedMod == index) {
                name.withStyle(ChatFormatting.UNDERLINE);
            }
            gui.drawString(font, name, x + 20, y + 6 + (i * 20), 16777215);

            if (mouseX >= x && mouseY >= y && mouseX <= x + 120 && mouseY < y + 100) {
                if (mouseX >= x + 2 && mouseY >= y + 2 + (i * 20) && mouseX <= x + 102 && mouseY < y + 22 + (i * 20)) {
                    List<Component> list = new ArrayList<>();
                    list.add(Component.literal(mod.getName()));
                    list.add(Component.empty());
                    list.add(Component.translatable("gui.fluffy_fur.menu.id").append(" ").append(Component.literal(mod.getId()).withStyle(ChatFormatting.GRAY)));
                    list.add(Component.translatable("gui.fluffy_fur.menu.version").append(" ").append(Component.literal(mod.getVersion()).withStyle(ChatFormatting.GRAY)));
                    if (mod.getEdition() > 0)list.add(Component.translatable("gui.fluffy_fur.menu.edition").append(" ").append(Component.literal(String.valueOf(mod.getEdition())).withStyle(ChatFormatting.GRAY)));
                    list.add(Component.translatable("gui.fluffy_fur.menu.autor").append(" ").append(Component.literal(mod.getDev()).withStyle(ChatFormatting.GRAY)));

                    gui.renderTooltip(Minecraft.getInstance().font, list, Optional.empty(), mouseX, mouseY);
                }
            }
        }
    }

    public static void drawSlider(GuiGraphics gui, int x, int y, float progress, int mouseX, int mouseY, float partialTicks) {
        int i = (int) (progress * 80);
        gui.blit(BACKGROUND, x, y, 160, 0, 10, 92, 256, 256);
        gui.blit(BACKGROUND, x + 1, y + 1 + i, 170, 0, 8, 10, 256, 256);
    }

    public static void drawBlackBackground(GuiGraphics gui, int x, int y, int size, float alpha, int mouseX, int mouseY, float partialTicks) {
        if (size < 4) size = 4;
        if (size % 2 != 0) size++;

        RenderSystem.enableBlend();
        gui.setColor(1f, 1f, 1f, alpha);
        gui.blit(BACKGROUND, x - (size / 2), y, 178, 0, 1, 10, 256, 256);
        gui.blit(BACKGROUND, x - (size / 2) + 1, y, size - 2, 10, 179, 0, 1, 10, 256, 256);
        gui.blit(BACKGROUND, x + (size / 2) - 1, y, 178, 0, 1, 10, 256, 256);
        gui.setColor(1f, 1f, 1f, 1f);
        RenderSystem.disableBlend();
    }

    public static void drawBlackBackground(GuiGraphics gui, int x, int y, int size, int mouseX, int mouseY, float partialTicks) {
        drawBlackBackground(gui, x, y , size, 0.5f, mouseX, mouseY, partialTicks);
    }

    public static List<Component> getDescription(FluffyFurMod mod) {
        String text = mod.getDescription().getString();
        int w = 146;

        Font font = Minecraft.getInstance().font;
        List<Component> lines = new ArrayList<>();
        String[] words = text.split(" ");
        String line = "";
        for (String s : words) {
            if (s.equals("\n")) {
                lines.add(Component.literal(line));
                line = "";
            } else if (font.width(line) + font.width(s) > w) {
                lines.add(Component.literal(line));
                line = s + " ";
            }
            else line += s + " ";
        }
        if (!line.isEmpty()) lines.add(Component.literal(line));
        if (mod.getLinks().size() > 0) {
            lines.add(Component.empty());
            lines.add(Component.translatable("gui.fluffy_fur.menu.links").withStyle(ChatFormatting.GRAY));
            for (FluffyFurMod.Link link : mod.getLinks()) {
                lines.add(link.getComponent());
            }
        }

        return lines;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (descriptionMouseClicked(this.width / 2 - 80, this.height / 4 + 48, mouseX, mouseY, button)) return true;
        if (panoramaListMouseClicked(this.width / 2 - 204, this.height / 4 + 48, mouseX, mouseY, button)) return true;
        if (modListMouseClicked(this.width / 2 + 84, this.height / 4 + 48, mouseX, mouseY, button)) return true;

        return  super.mouseClicked(mouseX, mouseY, button);
    }

    public boolean descriptionMouseClicked(int x, int y, double mouseX, double mouseY, int button) {
        if (mouseX >= x && mouseY >= y && mouseX <= x + 160 && mouseY < y + 100) {
            FluffyFurMod mod = mods.get(selectedMod);
            List<Component> lines = getDescription(mod);
            int links = mod.getLinks().size();
            int l = lines.size() - links;
            for (int i = 0; i < 9; i++) {
                int index = descriptionScroll + i;
                if (index < 0) break;
                if (index > lines.size() - 1) break;
                MutableComponent line = Component.empty().append(lines.get(index));
                if (index >= l) {
                    if (mouseX >= x + 5 && mouseY >= y + 5 + (i * (font.lineHeight + 1)) && mouseX <= x + 5 + font.width(line) && mouseY < y + 5 + (i * (font.lineHeight + 1) + font.lineHeight)) {
                        linkTo(mod.getLinks().get(index - l).getLink());
                        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK.get(), 1.0f, 0.25f));
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean panoramaListMouseClicked(int x, int y, double mouseX, double mouseY, int button) {
        if (mouseX >= x && mouseY >= y && mouseX <= x + 120 && mouseY < y + 100) {
            for (int i = 0; i < 5; i++) {
                int index = panoramasScroll + i;
                if (index < 0) break;
                if (index > panoramas.size() - 1) break;
                if (mouseX >= x + 2 && mouseY >= y + 2 + (i * 20) && mouseX <= x + 102 && mouseY < y + 22 + (i * 20)) {
                    selectedPanorama = index;
                    FluffyFurModsHandler.setPanorama(panoramas.get(selectedPanorama));
                    setLocalPanorama(panoramas.get(selectedPanorama));
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK.get(), 1.0f, 0.25f));
                    return true;
                }
            }
        }

        return false;
    }

    public boolean modListMouseClicked(int x, int y, double mouseX, double mouseY, int button) {
        if (mouseX >= x && mouseY >= y && mouseX <= x + 120 && mouseY < y + 100) {
            for (int i = 0; i < 5; i++) {
                int index = modsScroll + i;
                if (index < 0) break;
                if (index > mods.size() - 1) break;
                if (mouseX >= x + 2 && mouseY >= y + 2 + (i * 20) && mouseX <= x + 102 && mouseY < y + 22 + (i * 20)) {
                    selectedMod = index;
                    descriptionScroll = 0;
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK.get(), 1.0f, 0.25f));
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (descriptionMouseScrolled(this.width / 2 - 80, this.height / 4 + 48, mouseX, mouseY, delta)) return true;
        if (panoramaListMouseScrolled(this.width / 2 - 204, this.height / 4 + 48, mouseX, mouseY, delta)) return true;
        if (modListMouseScrolled(this.width / 2 + 84, this.height / 4 + 48, mouseX, mouseY, delta)) return true;

        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    public boolean descriptionMouseScrolled(int x, int y, double mouseX, double mouseY, double delta) {
        if (mouseX >= x && mouseY >= y && mouseX <= x + 160 && mouseY < y + 100) {
            List<Component> lines = getDescription(mods.get(selectedMod));
            int add = (int) delta;
            if (descriptionScroll - add < 0) {
                return false;
            }
            if (descriptionScroll - add > lines.size() - 9) {
                return false;
            }
            descriptionScroll = descriptionScroll - add;
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK.get(), 2.0f, 0.1f));
            return true;
        }

        return false;
    }

    public boolean panoramaListMouseScrolled(int x, int y, double mouseX, double mouseY, double delta) {
        if (mouseX >= x && mouseY >= y && mouseX <= x + 120 && mouseY < y + 100) {
            int add = (int) delta;
            if (panoramasScroll - add < 0) {
                return false;
            }
            if (panoramasScroll - add > panoramas.size() - 5) {
                return false;
            }
            panoramasScroll = panoramasScroll - add;
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK.get(), 2.0f, 0.1f));
            return true;
        }

        return false;
    }

    public boolean modListMouseScrolled(int x, int y, double mouseX, double mouseY, double delta) {
        if (mouseX >= x && mouseY >= y && mouseX <= x + 120 && mouseY < y + 100) {
            int add = (int) delta;
            if (modsScroll - add < 0) {
                return false;
            }
            if (modsScroll - add > mods.size() - 5) {
                return false;
            }
            modsScroll = modsScroll - add;
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK.get(), 2.0f, 0.1f));
            return true;
        }

        return false;
    }

    public void linkTo(String url) {
        this.minecraft.setScreen(new ConfirmLinkScreen((p_213069_2_) -> {
            if (p_213069_2_)
                Util.getPlatform()
                        .openUri(url);
            this.minecraft.setScreen(this);
        }, url, true));
    }

    public boolean isPauseScreen() {
        return false;
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }
}