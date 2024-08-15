package mod.maxbogomol.fluffy_fur.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.Music;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Random;

@Mixin(Minecraft.class)
public class ClientMusicChoiceMixin {
    private static Random random = new Random();

    @Shadow
    @Nullable
    public Screen screen;

    @Shadow
    @Nullable
    public LocalPlayer player;

    @Shadow
    @Final
    public Gui gui;

    @Inject(method = "getSituationalMusic", at = @At("HEAD"), cancellable = true)
    private void fluffy_fur$biomemusic$musicChoice(final CallbackInfoReturnable<Music> cir) {
        if (screen instanceof WinScreen) {
            return;
        }
/*
        if (screen instanceof TitleScreen) {
            if (ClientConfig.CUSTOM_PANORAMA.get()) {
                cir.setReturnValue(FluffyFurClient.REBORN_MUSIC);
            }
        }

        List<Music> possibleTracks = new ArrayList<>();

        if (this.player != null) {
            Holder<Biome> holder = this.player.level().getBiome(this.player.blockPosition());
            final Music biomeMusic = holder.value().getBackgroundMusic().orElse(null);
            if (holder.is(Tags.Biomes.IS_SWAMP)) {
                if (random.nextFloat() < 0.8f) {
                    cir.setReturnValue(FluffyFurClient.MOR_MUSIC);
                }
            }
            if (holder.is(Tags.Biomes.IS_CAVE)) {
                if (player.getY() >= -40 && player.getY() <= 30) {
                    if (random.nextFloat() < 0.6f) {
                        cir.setReturnValue(FluffyFurClient.SHIMMER_MUSIC);
                    }
                }
            }
        }*/
    }
}