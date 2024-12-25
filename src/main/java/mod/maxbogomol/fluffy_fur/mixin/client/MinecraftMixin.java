package mod.maxbogomol.fluffy_fur.mixin.client;

import mod.maxbogomol.fluffy_fur.FluffyFurClient;
import mod.maxbogomol.fluffy_fur.client.sound.MusicHandler;
import mod.maxbogomol.fluffy_fur.client.sound.MusicModifier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

    @Unique
    Random fluffy_fur$random = new Random();

    @Inject(method = "getSituationalMusic", at = @At("TAIL"), cancellable = true)
    private void fluffy_fur$getSituationalMusic(final CallbackInfoReturnable<Music> cir) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.screen instanceof WinScreen) {
            return;
        }

        List<Music> possibleMusic = new ArrayList<>();
        List<Music> menuMusic = new ArrayList<>();
        Music defaultMusic = cir.getReturnValue();

        for (MusicModifier modifier : MusicHandler.getModifiers()) {
            if (modifier.isCanPlay(defaultMusic, minecraft)) {
                Music music = modifier.play(defaultMusic, minecraft);
                if (music != null) {
                    if (modifier.isMenu(defaultMusic, minecraft)) {
                        menuMusic.add(music);
                    } else {
                        possibleMusic.add(music);
                    }
                }
            }
        }

        if (menuMusic.size() > 0) {
            if (minecraft.screen instanceof TitleScreen || defaultMusic == Musics.MENU) {
                if (!menuMusic.contains(defaultMusic)) {
                    cir.setReturnValue(menuMusic.get(fluffy_fur$random.nextInt(0, menuMusic.size())));
                }
            }
        }

        if (possibleMusic.size() > 0) {
            cir.setReturnValue(possibleMusic.get(fluffy_fur$random.nextInt(0, possibleMusic.size())));
        }
    }

    @Inject(at = @At("RETURN"), method = "isDemo", cancellable = true)
    public void fluffy_fur$isDemo(CallbackInfoReturnable<Boolean> cir) {
        if (FluffyFurClient.optifinePresent) {
            cir.setReturnValue(true);
        }
    }

/*    @Inject(at = @At("RETURN"), method = "createTitle", cancellable = true)
    public void fluffy_fur$createTitle(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(cir.getReturnValue() + " DOWNLOAD IMPLOSION NOW");
    }*/
}
