package mod.maxbogomol.fluffy_fur.client.gui.components;

import mod.maxbogomol.fluffy_fur.client.gui.screen.HardcoreShareToLanScreenHandler;
import mod.maxbogomol.fluffy_fur.client.shader.postprocess.HardcoreLanPostProcess;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.GameType;

public class HardcoreLanGameModeButton extends Button {

    public static final Component GAME_MODE_LABEL = Component.translatable("selectWorld.gameMode");
    public GameType gameMode;

    public HardcoreLanGameModeButton(int x, int y, int width, int height, GameType gameMode) {
        super(x, y, width, height, GAME_MODE_LABEL, HardcoreLanGameModeButton::click, DEFAULT_NARRATION);
        this.gameMode = gameMode;
        setMessage(getComponent(gameMode));
    }

    public static Component getComponent(GameType gameMode) {
        return GAME_MODE_LABEL.copy().append(": ").append(gameMode.getShortDisplayName());
    }

    public static void click(Button button) {
        button.active = false;
        if (!HardcoreShareToLanScreenHandler.isDumbAss) {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.RESPAWN_ANCHOR_DEPLETE.get(), 1.0f, 0.6f));
            HardcoreShareToLanScreenHandler.isDumbAssTicks = 0;
            HardcoreShareToLanScreenHandler.isDumbAss = true;
            HardcoreLanPostProcess.INSTANCE.enable();
        }
    }

    @Override
    public void playDownSound(SoundManager handler) {
        if (HardcoreShareToLanScreenHandler.isDumbAss) {
            super.playDownSound(handler);
        }
    }
}
