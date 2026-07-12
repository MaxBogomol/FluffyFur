package mod.maxbogomol.fluffy_fur.client.gui.screen;

import mod.maxbogomol.fluffy_fur.client.gui.components.FluffyFurLogoRenderer;
import mod.maxbogomol.fluffy_fur.client.gui.components.FluffyFurPanoramaRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class FluffyFurPanorama {
    public String id;
    public Component name;
    public ResourceLocation texture = new ResourceLocation("textures/gui/title/background/panorama");
    public ResourceLocation logo;
    public FluffyFurPanoramaRenderer customRenderer = new FluffyFurPanoramaRenderer();
    public FluffyFurLogoRenderer customLogoRenderer = new FluffyFurLogoRenderer();
    public ItemStack itemStack = new ItemStack(Items.DIRT);
    public FluffyFurMod mod;
    public int sort = 0;

    public FluffyFurPanorama(String id, Component name) {
        this.id = id;
        this.name = name;
    }

    public FluffyFurPanorama setTexture(ResourceLocation texture) {
        this.texture = texture;
        return this;
    }

    public FluffyFurPanorama setLogo(ResourceLocation logo) {
        this.logo = logo;
        return this;
    }

    public FluffyFurPanorama setCustomRenderer(FluffyFurPanoramaRenderer customRenderer) {
        this.customRenderer = customRenderer;
        return this;
    }

    public FluffyFurPanorama setCustomLogoRenderer(FluffyFurLogoRenderer customLogoRenderer) {
        this.customLogoRenderer = customLogoRenderer;
        return this;
    }

    public FluffyFurPanorama setItem(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public FluffyFurPanorama setMod(FluffyFurMod mod) {
        this.mod = mod;
        return this;
    }

    public FluffyFurPanorama setSort(int sort) {
        this.sort = sort;
        return this;
    }

    public String getId() {
        return id;
    }

    public Component getName() {
        return name;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public ResourceLocation getLogo() {
        return logo;
    }

    public FluffyFurPanoramaRenderer getCustomRenderer() {
        return customRenderer;
    }

    public FluffyFurLogoRenderer getCustomLogoRenderer() {
        return customLogoRenderer;
    }

    public ItemStack getItem() {
        return itemStack;
    }

    public FluffyFurMod getMod() {
        return mod;
    }

    public int getSort() {
        return sort;
    }
}
