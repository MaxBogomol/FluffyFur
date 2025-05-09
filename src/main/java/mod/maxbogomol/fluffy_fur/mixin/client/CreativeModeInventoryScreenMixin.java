package mod.maxbogomol.fluffy_fur.mixin.client;

import mod.maxbogomol.fluffy_fur.client.event.FluffyFurClientEvents;
import mod.maxbogomol.fluffy_fur.client.gui.components.SubCreativeTabButton;
import mod.maxbogomol.fluffy_fur.common.creativetab.MultiCreativeTab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin {

    @Inject(at = @At("HEAD"), method = "selectTab")
    private void fluffy_fur$selectTab(CreativeModeTab tab, CallbackInfo ci) {
        for (SubCreativeTabButton sb : FluffyFurClientEvents.subCreativeTabButtons) {
            sb.refreshSubVisible(tab);
        }
    }

    @Inject(at = @At("HEAD"), method = "mouseScrolled", cancellable = true)
    private void fluffy_fur$mouseScrolled(double mouseX, double mouseY, double delta, CallbackInfoReturnable<Boolean> cir) {
        CreativeModeInventoryScreen self = (CreativeModeInventoryScreen) ((Object) this);
        int i = self.getGuiLeft();
        int j = self.getGuiTop();
        if (mouseX >= i - 26 && mouseY >= j && mouseX <= i && mouseY < j + 138) {
            if (CreativeModeInventoryScreen.selectedTab instanceof MultiCreativeTab multiCreativeTab) {
                if (multiCreativeTab.getSortedSubTabs().size() > 6) {
                    int add = (int) -delta;
                    multiCreativeTab.scroll = multiCreativeTab.scroll + add;
                    if (multiCreativeTab.scroll < 0) {
                        multiCreativeTab.scroll = 0;
                    } else if (multiCreativeTab.scroll > multiCreativeTab.getSortedSubTabs().size() - 6 && multiCreativeTab.getSortedSubTabs().size() > 6) {
                        multiCreativeTab.scroll = multiCreativeTab.getSortedSubTabs().size() - 6;
                    } else {
                        Minecraft.getInstance().player.playNotifySound(SoundEvents.UI_BUTTON_CLICK.get(), SoundSource.NEUTRAL, 0.1f, 2.0f);
                    }
                    for (SubCreativeTabButton sb : FluffyFurClientEvents.subCreativeTabButtons) {
                        if (multiCreativeTab.getSortedSubTabs().contains(sb.subTab)) {
                            sb.refreshSub();
                        }
                    }
                }
                cir.setReturnValue(true);
            }
        }
    }
}
