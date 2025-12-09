package mod.maxbogomol.fluffy_fur.client.gui.screen;

import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurFonts;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FluffyFurMod {
    public String id;
    public String name;
    public String version;
    public int edition = 0;
    public String dev = "Unknown";
    public ItemStack itemStack = new ItemStack(Items.DIRT);
    public Color nameColor = Color.WHITE;
    public Color versionColor = Color.GRAY;
    public Component description = Component.empty();
    public List<Link> links = new ArrayList<>();

    public static Color fluffyVillageColor = new Color(150, 71, 71);
    public static Color githubColor = new Color(60, 116, 195);
    public static Color curseForgeColor = new Color(241, 100, 54);
    public static Color modrinthColor = new Color(0, 175, 92);
    public static Color discordColor = new Color(88, 101, 242);

    public FluffyFurMod(String id, String name, String version) {
        this.id = id;
        this.name = name;
        this.version = version;
    }

    public FluffyFurMod setEdition(int edition) {
        this.edition = edition;
        return this;
    }

    public FluffyFurMod setDev(String dev) {
        this.dev = dev;
        return this;
    }

    public FluffyFurMod setItem(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public FluffyFurMod setNameColor(Color nameColor) {
        this.nameColor = nameColor;
        return this;
    }

    public FluffyFurMod setVersionColor(Color versionColor) {
        this.versionColor = versionColor;
        return this;
    }

    public FluffyFurMod setDescription(Component description) {
        this.description = description;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public int getEdition() {
        return edition;
    }

    public String getDev() {
        return dev;
    }

    public ItemStack getItem() {
        return itemStack;
    }

    public Color getNameColor() {
        return nameColor;
    }

    public Color getVersionColor() {
        return versionColor;
    }

    public Component getDescription() {
        return description;
    }

    public static class Link {
        public Component component;
        public String link;

        public Link(Component component, String link) {
            this.component = component;
            this.link = link;
        }

        public Component getComponent() {
            return component;
        }

        public String getLink() {
            return link;
        }
    }

    public List<Link> getLinks() {
        return links;
    }

    public FluffyFurMod addLink(Component component, String string) {
        links.add(new Link(component, string));
        return this;
    }

    public FluffyFurMod addIconLink(String string, String name, Color color, String icon, ResourceLocation font) {
        addLink(Component.empty()
                        .append(Component.literal(icon).withStyle(Style.EMPTY.withColor(ColorUtil.packColor(color)).withFont(font)))
                        .append(CommonComponents.SPACE.copy().withStyle(Style.EMPTY.withColor(ColorUtil.packColor(color))))
                        .append(Component.literal(name).withStyle(Style.EMPTY.withColor(ColorUtil.packColor(color)))),
                string);
        return this;
    }

    public FluffyFurMod addFluffyVillageLink(String string) {
        return addIconLink(string, "The Fluffy Village", fluffyVillageColor, "\u0001", FluffyFurFonts.ICONS_LOCATION);
    }

    public FluffyFurMod addGithubLink(String string) {
        return addIconLink(string, "Github", githubColor, "\u0002", FluffyFurFonts.ICONS_LOCATION);
    }

    public FluffyFurMod addCurseForgeLink(String string) {
        return addIconLink(string, "CurseForge", curseForgeColor, "\u0003", FluffyFurFonts.ICONS_LOCATION);
    }

    public FluffyFurMod addModrinthLink(String string) {
        return addIconLink(string, "Modrinth", modrinthColor, "\u0004", FluffyFurFonts.ICONS_LOCATION);
    }

    public FluffyFurMod addDiscordLink(String string) {
        return addIconLink(string, "Discord", discordColor, "\u0005", FluffyFurFonts.ICONS_LOCATION);
    }
}
