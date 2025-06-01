package mod.maxbogomol.fluffy_fur.common.creativetab;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;
import java.util.function.Supplier;

public class MultiCreativeTabBuilder extends CreativeModeTab.Builder {

    public static ResourceLocation STANDARD_SUB_ARROWS = new ResourceLocation(FluffyFur.MOD_ID, "textures/gui/sub_arrows.png");
    public ResourceLocation multiBackgroundLocation;
    public ResourceLocation subArrowsImage = STANDARD_SUB_ARROWS;

    public MultiCreativeTabBuilder(CreativeModeTab.Row row, int column) {
        super(row, column);
    }

    @Override
    public MultiCreativeTabBuilder title(Component title) {
        super.title(title);
        return this;
    }

    @Override
    public MultiCreativeTabBuilder icon(Supplier<ItemStack> icon) {
        super.icon(icon);
        return this;
    }

    @Override
    public MultiCreativeTabBuilder displayItems(CreativeModeTab.DisplayItemsGenerator displayItemsGenerator) {
        super.displayItems(displayItemsGenerator);
        return this;
    }

    @Override
    public MultiCreativeTabBuilder alignedRight() {
        super.alignedRight();
        return this;
    }

    @Override
    public MultiCreativeTabBuilder hideTitle() {
        super.hideTitle();
        return this;
    }

    @Override
    public MultiCreativeTabBuilder noScrollBar() {
        super.noScrollBar();
        return this;
    }

    @Override
    protected MultiCreativeTabBuilder type(CreativeModeTab.Type type) {
        super.type(type);
        return this;
    }

    @Override
    public MultiCreativeTabBuilder backgroundSuffix(String backgroundSuffix) {
        return withBackgroundLocation(new ResourceLocation("textures/gui/container/creative_inventory/tab_" + backgroundSuffix));
    }

    @Override
    public MultiCreativeTabBuilder withBackgroundLocation(ResourceLocation background) {
        this.multiBackgroundLocation = background;
        super.withBackgroundLocation(background);
        return this;
    }

    @Override
    public MultiCreativeTabBuilder withSearchBar() {
        super.withSearchBar();
        return this;
    }

    @Override
    public MultiCreativeTabBuilder withSearchBar(int searchBarWidth) {
        super.withSearchBar(searchBarWidth);
        return withSearchBar();
    }

    @Override
    public MultiCreativeTabBuilder withTabsImage(ResourceLocation tabsImage) {
        super.withTabsImage(tabsImage);
        return this;
    }

    @Override
    public MultiCreativeTabBuilder withLabelColor(int labelColor) {
        super.withLabelColor(labelColor);
        return this;
    }

    @Override
    public MultiCreativeTabBuilder withSlotColor(int slotColor) {
        super.withSlotColor(slotColor);
        return this;
    }

    @Override
    public MultiCreativeTabBuilder withTabFactory(Function<CreativeModeTab.Builder, CreativeModeTab> tabFactory) {
        super.withTabFactory(tabFactory);
        return this;
    }

    @Override
    public MultiCreativeTabBuilder withTabsBefore(ResourceLocation... tabs) {
        super.withTabsBefore(tabs);
        return this;
    }

    @Override
    public MultiCreativeTabBuilder withTabsAfter(ResourceLocation... tabs) {
        super.withTabsAfter(tabs);
        return this;
    }

    public MultiCreativeTabBuilder withSubArrowsImage(ResourceLocation image) {
        this.subArrowsImage = image;
        return this;
    }

    public MultiCreativeTab multiBuild() {
        if ((this.type == CreativeModeTab.Type.HOTBAR || this.type == CreativeModeTab.Type.INVENTORY) && this.displayItemsGenerator != EMPTY_GENERATOR) {
            throw new IllegalStateException("Special tabs can't have display items");
        } else {
            MultiCreativeTab creativeTab = new MultiCreativeTab(this);
            creativeTab.alignedRight = this.alignedRight;
            creativeTab.showTitle = this.showTitle;
            creativeTab.canScroll = this.canScroll;
            creativeTab.backgroundSuffix = this.backgroundSuffix;
            creativeTab.multiBackgroundLocation = this.multiBackgroundLocation != null ? this.multiBackgroundLocation : new ResourceLocation("textures/gui/container/creative_inventory/tab_" + this.backgroundSuffix);
            creativeTab.subArrowsImage = this.subArrowsImage;
            return creativeTab;
        }
    }
}
