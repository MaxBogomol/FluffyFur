package mod.maxbogomol.fluffy_fur.mixin.client;

import mod.maxbogomol.fluffy_fur.client.gui.spectator.TeleportToDimensionMenuCategory;
import mod.maxbogomol.fluffy_fur.client.gui.spectator.TeleportToLevelSpawnPointMenuItem;
import mod.maxbogomol.fluffy_fur.client.gui.spectator.TeleportToSpawnPointMenuItem;
import net.minecraft.client.gui.spectator.RootSpectatorMenuCategory;
import net.minecraft.client.gui.spectator.SpectatorMenuItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(RootSpectatorMenuCategory.class)
public abstract class RootSpectatorMenuCategoryMixin {

    @Shadow
    @Final
    private List<SpectatorMenuItem> items;

    @Inject(at = @At("RETURN"), method = "<init>")
    private void fluffy_fur$getRootSpectatorMenuCategory(CallbackInfo ci) {
        items.add(new TeleportToSpawnPointMenuItem());
        items.add(new TeleportToLevelSpawnPointMenuItem());
        items.add(new TeleportToDimensionMenuCategory());
    }
}
