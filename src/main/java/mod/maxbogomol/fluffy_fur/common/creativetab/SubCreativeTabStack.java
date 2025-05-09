package mod.maxbogomol.fluffy_fur.common.creativetab;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

public class SubCreativeTabStack extends SubCreativeTab {

    public ArrayList<SubCreativeTab> subCreativeTabs = new ArrayList<>();

    public static SubCreativeTabStack create() {
        return new SubCreativeTabStack();
    }

    public SubCreativeTabStack title(Component title) {
        this.displayName = title;
        return this;
    }

    public SubCreativeTabStack icon(Supplier<ItemStack> icon) {
        this.iconItemStack = icon;
        return this;
    }

    public SubCreativeTabStack hideTitle() {
        this.hasShowTitle = true;
        this.showTitle = false;
        return this;
    }

    public SubCreativeTabStack noScrollBar() {
        this.hasCanScroll = true;
        this.canScroll = false;
        return this;
    }

    public SubCreativeTabStack withTitle() {
        this.hasShowTitle = true;
        this.showTitle = true;
        return this;
    }

    public SubCreativeTabStack witScrollBar() {
        this.hasCanScroll = true;
        this.canScroll = true;
        return this;
    }

    public SubCreativeTabStack withBackgroundLocation(ResourceLocation background) {
        this.backgroundLocation = background;
        return this;
    }

    public SubCreativeTabStack hideSearchBar() {
        this.hasSearch = true;
        this.hasSearchBar = false;
        return this;
    }

    public SubCreativeTabStack withSearchBar() {
        this.hasSearch = true;
        this.hasSearchBar = true;
        return this;
    }

    public SubCreativeTabStack withSearchBar(int searchBarWidth) {
        this.hasSearchBarWidth = true;
        this.searchBarWidth = searchBarWidth;
        return withSearchBar();
    }

    public SubCreativeTabStack withSearchBarWidth(int searchBarWidth) {
        this.hasSearchBarWidth = true;
        this.searchBarWidth = searchBarWidth;
        return this;
    }

    public SubCreativeTabStack withTabsImage(ResourceLocation tabsImage) {
        this.tabsImage = tabsImage;
        return this;
    }

    public SubCreativeTabStack withLabelColor(int labelColor) {
        this.hasLabelColor = true;
        this.labelColor = labelColor;
        return this;
    }

    public SubCreativeTabStack withSlotColor(int slotColor) {
        this.hasSlotColor = true;
        this.slotColor = slotColor;
        return this;
    }

    @Override
    public boolean hasShowTitle() {
        if (!getSubTabs().isEmpty()) return getSubTabs().get(0).hasShowTitle();
        return hasShowTitle;
    }

    @Override
    public boolean hasCanScroll() {
        if (!getSubTabs().isEmpty()) return getSubTabs().get(0).hasCanScroll();
        return hasCanScroll;
    }

    @Override
    public boolean hasSearch() {
        if (!getSubTabs().isEmpty()) return getSubTabs().get(0).hasSearch();
        return hasSearch;
    }

    @Override
    public boolean hasSearchBarWidth() {
        if (!getSubTabs().isEmpty()) return getSubTabs().get(0).hasSearchBarWidth();
        return hasSearchBarWidth;
    }

    @Override
    public boolean hasLabelColor() {
        if (!getSubTabs().isEmpty()) return getSubTabs().get(0).hasLabelColor();
        return hasLabelColor;
    }

    @Override
    public boolean hasSlotColor() {
        if (!getSubTabs().isEmpty()) return getSubTabs().get(0).hasSlotColor();
        return hasSlotColor;
    }

    @Override
    public Component getDisplayName() {
        if (displayName != null) return displayName;
        if (!getSubTabs().isEmpty()) return getSubTabs().get(0).getDisplayName();
        return null;
    }

    @Override
    public ItemStack getIconItem() {
        if (iconItemStack.get() != null) return iconItemStack.get();
        if (!getSubTabs().isEmpty()) return getSubTabs().get(0).getIconItem();
        return null;
    }

    @Override
    public boolean showTitle() {
        if (!getSubTabs().isEmpty()) return getSubTabs().get(0).showTitle();
        return super.showTitle();
    }

    @Override
    public boolean canScroll() {
        if (!getSubTabs().isEmpty()) return getSubTabs().get(0).canScroll();
        return super.canScroll();
    }

    @Override
    public Collection<ItemStack> getDisplayItems() {
        Collection<ItemStack> list = new ArrayList<>();
        if (!displayItems.isEmpty()) list.addAll(displayItems);
        if (!getSubTabs().isEmpty()) {
            for (SubCreativeTab sub : getSubTabs()) {
                if (sub.getDisplayItems() != null) list.addAll(sub.getDisplayItems());
            }
        }
        if (!list.isEmpty()) return list;
        return null;
    }

    @Override
    public Collection<ItemStack> getSearchTabDisplayItems() {
        Collection<ItemStack> list = new ArrayList<>();
        if (!displayItemsSearchTab.isEmpty()) list.addAll(displayItemsSearchTab);
        if (!getSubTabs().isEmpty()) {
            for (SubCreativeTab sub : getSubTabs()) {
                if (sub.getSearchTabDisplayItems() != null) list.addAll(sub.getSearchTabDisplayItems());
            }
        }
        if (!list.isEmpty()) return list;
        return null;
    }

    @Override
    public ResourceLocation getBackgroundLocation() {
        if (backgroundLocation != null) return backgroundLocation;
        if (!getSubTabs().isEmpty()) return getSubTabs().get(0).getBackgroundLocation();
        return super.getBackgroundLocation();
    }

    @Override
    public boolean hasSearchBar() {
        if (!getSubTabs().isEmpty()) return getSubTabs().get(0).hasSearchBar();
        return super.hasSearchBar();
    }

    @Override
    public int getSearchBarWidth() {
        if (!getSubTabs().isEmpty()) return getSubTabs().get(0).getSearchBarWidth();
        return super.getSearchBarWidth();
    }

    @Override
    public ResourceLocation getTabsImage() {
        if (tabsImage != null) return tabsImage;
        if (!getSubTabs().isEmpty()) return getSubTabs().get(0).getTabsImage();
        return super.getTabsImage();
    }

    @Override
    public int getLabelColor() {
        if (!getSubTabs().isEmpty()) return getSubTabs().get(0).getLabelColor();
        return super.getLabelColor();
    }

    @Override
    public int getSlotColor() {
        if (!getSubTabs().isEmpty()) return getSubTabs().get(0).getSlotColor();
        return super.getSlotColor();
    }

    @Override
    public SubCreativeTabStack subTitle(Component title) {
        super.subTitle(title);
        return this;
    }

    @Override
    public SubCreativeTabStack subIcon(Supplier<ItemStack> icon) {
        super.subIcon(icon);
        return this;
    }

    @Override
    public SubCreativeTabStack withSubTabImage(ResourceLocation image) {
        super.withSubTabImage(image);
        return this;
    }

    @Override
    public SubCreativeTabStack setSubShow(Supplier<Boolean> subShow) {
        super.setSubShow(subShow);
        return this;
    }

    public SubCreativeTabStack addSubTab(SubCreativeTab subTab) {
        this.subCreativeTabs.add(subTab);
        return this;
    }

    public ArrayList<SubCreativeTab> getSubTabs() {
        return subCreativeTabs;
    }
}
