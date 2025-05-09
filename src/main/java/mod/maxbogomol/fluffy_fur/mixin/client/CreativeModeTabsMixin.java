package mod.maxbogomol.fluffy_fur.mixin.client;

import mod.maxbogomol.fluffy_fur.common.creativetab.MultiCreativeTab;
import mod.maxbogomol.fluffy_fur.common.creativetab.SubCreativeTab;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeTabs.class)
public abstract class CreativeModeTabsMixin {

    @Inject(at = @At("HEAD"), method = "buildAllTabContents")
    private static void fluffy_fur$buildAllTabContentsHead(CreativeModeTab.ItemDisplayParameters parameters, CallbackInfo ci) {
        for (CreativeModeTab tab : CreativeModeTabs.allTabs()) {
            if (tab instanceof MultiCreativeTab multiCreativeTab) {
                for (SubCreativeTab subTab : multiCreativeTab.getSubTabs()) {
                    subTab.clearItems();
                }
            }
        }
    }

    @Inject(at = @At("TAIL"), method = "buildAllTabContents")
    private static void fluffy_fur$buildAllTabContentsTail(CreativeModeTab.ItemDisplayParameters parameters, CallbackInfo ci) {
        for (CreativeModeTab tab : CreativeModeTabs.allTabs()) {
            if (tab instanceof MultiCreativeTab multiCreativeTab) {
                multiCreativeTab.sortSubTabs();
            }
        }
    }
}
