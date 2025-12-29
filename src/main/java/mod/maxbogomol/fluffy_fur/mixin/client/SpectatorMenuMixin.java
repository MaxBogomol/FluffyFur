package mod.maxbogomol.fluffy_fur.mixin.client;

import mod.maxbogomol.fluffy_fur.client.spectator.SpectatorDimensionHandler;
import mod.maxbogomol.fluffy_fur.common.network.FluffyFurPacketHandler;
import mod.maxbogomol.fluffy_fur.common.network.spectator.SpectatorServerDimensionsPacket;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpectatorMenu.class)
public abstract class SpectatorMenuMixin {

    @Inject(at = @At("RETURN"), method = "<init>")
    private void fluffy_fur$getRootSpectatorMenuCategory(CallbackInfo ci) {
        SpectatorDimensionHandler.levelDimensions.clear();
        SpectatorDimensionHandler.levelDimensionSaves.clear();
        FluffyFurPacketHandler.sendToServer(new SpectatorServerDimensionsPacket());
    }
}
