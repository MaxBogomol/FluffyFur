package mod.maxbogomol.fluffy_fur.common.creativetab;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;

public class MultiCreativeTab extends CreativeModeTab {

    public ResourceLocation multiBackgroundLocation;
    public ResourceLocation subArrowsImage = MultiCreativeTabBuilder.STANDARD_SUB_ARROWS;
    public ArrayList<SubCreativeTab> subCreativeTabs = new ArrayList<>();
    public ArrayList<SubCreativeTab> sortedSubCreativeTabs = new ArrayList<>();
    public SubCreativeTab currentSubTab;
    public int scroll = 0;

    protected MultiCreativeTab(Builder builder) {
        super(builder);
    }

    public MultiCreativeTab(MultiCreativeTabBuilder builder) {
        super(builder);
    }

    public static MultiCreativeTabBuilder builder() {
        return new MultiCreativeTabBuilder(Row.TOP, 0);
    }

    @Override
    public Component getDisplayName() {
        if (getCurrentSubTab() != null) {
            if (getCurrentSubTab().getDisplayName() != null) return getCurrentSubTab().getDisplayName();
        }
        return super.getDisplayName();
    }

    @Override
    public ItemStack getIconItem() {
        if (getCurrentSubTab() != null) {
            if (getCurrentSubTab().getIconItem() != null) return getCurrentSubTab().getIconItem();
        }
        return super.getIconItem();
    }

    @Override
    public boolean showTitle() {
        if (getCurrentSubTab() != null) {
            if (getCurrentSubTab().hasShowTitle()) return getCurrentSubTab().showTitle();
        }
        return super.showTitle();
    }

    @Override
    public boolean canScroll() {
        if (getCurrentSubTab() != null) {
            if (getCurrentSubTab().hasCanScroll()) return getCurrentSubTab().canScroll();
        }
        return super.canScroll();
    }

    @Override
    public Collection<ItemStack> getDisplayItems() {
        if (getCurrentSubTab() != null) {
            if (getCurrentSubTab().getDisplayItems() != null) return getCurrentSubTab().getDisplayItems();
        }
        return super.getDisplayItems();
    }

    @Override
    public Collection<ItemStack> getSearchTabDisplayItems() {
        if (getCurrentSubTab() != null) {
            if (getCurrentSubTab().getSearchTabDisplayItems() != null) return getCurrentSubTab().getSearchTabDisplayItems();
        }
        return super.getSearchTabDisplayItems();
    }

    @Override
    public boolean contains(ItemStack stack) {
        return getSearchTabDisplayItems().contains(stack);
    }

    @Override
    public ResourceLocation getBackgroundLocation() {
        if (getCurrentSubTab() != null) {
            if (getCurrentSubTab().getBackgroundLocation() != null) return getCurrentSubTab().getBackgroundLocation();
        }
        return multiBackgroundLocation;
    }

    @Override
    public boolean hasSearchBar() {
        if (getCurrentSubTab() != null) {
            if (getCurrentSubTab().hasSearch()) return getCurrentSubTab().hasSearchBar();
        }
        return super.hasSearchBar();
    }

    @Override
    public int getSearchBarWidth() {
        if (getCurrentSubTab() != null) {
            if (getCurrentSubTab().hasSearchBarWidth()) return getCurrentSubTab().getSearchBarWidth();
        }
        return super.getSearchBarWidth();
    }

    @Override
    public ResourceLocation getTabsImage() {
        if (getCurrentSubTab() != null) {
            if (getCurrentSubTab().getTabsImage() != null) return getCurrentSubTab().getTabsImage();
        }
        return super.getTabsImage();
    }

    @Override
    public int getLabelColor() {
        if (getCurrentSubTab() != null) {
            if (getCurrentSubTab().hasLabelColor()) return getCurrentSubTab().getLabelColor();
        }
        return super.getLabelColor();
    }

    @Override
    public int getSlotColor() {
        if (getCurrentSubTab() != null) {
            if (getCurrentSubTab().hasSlotColor()) return getCurrentSubTab().getSlotColor();
        }
        return super.getSlotColor();
    }

    public MultiCreativeTab withSubArrowsImage(ResourceLocation image) {
        this.subArrowsImage = image;
        return this;
    }

    public ResourceLocation getSubArrowsImage() {
        return subArrowsImage;
    }

    public MultiCreativeTab addSubTab(SubCreativeTab subTab) {
        this.subCreativeTabs.add(subTab);
        if (getCurrentSubTab() == null) {
            setCurrentSubTab(subTab);
        }
        return this;
    }

    public MultiCreativeTab sortSubTabs() {
        sortedSubCreativeTabs.clear();
        for (SubCreativeTab subTab : getSubTabs()) {
            if (subTab.getSubShow()) {
                sortedSubCreativeTabs.add(subTab);
            }
        }
        if (scroll < 0) {
            scroll = 0;
        } else if (scroll > getSortedSubTabs().size() - 6 && getSortedSubTabs().size() > 6) {
            scroll = getSortedSubTabs().size() - 6;
        }
        if (getSortedSubTabs().size() > 0) {
            if (!getSortedSubTabs().contains(getCurrentSubTab())) {
                setCurrentSubTab(getSortedSubTabs().get(0));
            }
        }
        return this;
    }

    public MultiCreativeTab setCurrentSubTab(SubCreativeTab subTab) {
        this.currentSubTab = subTab;
        return this;
    }

    public ArrayList<SubCreativeTab> getSubTabs() {
        return subCreativeTabs;
    }

    public ArrayList<SubCreativeTab> getSortedSubTabs() {
        return sortedSubCreativeTabs;
    }

    public SubCreativeTab getCurrentSubTab() {
        return currentSubTab;
    }
}
