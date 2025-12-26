package mod.maxbogomol.fluffy_fur.client.gui.components;

import mod.maxbogomol.fluffy_fur.client.gui.screen.HardcoreShareToLanScreenHandler;
import mod.maxbogomol.fluffy_fur.client.shader.postprocess.HardcoreLanPostProcess;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;

public class HardcoreLanCommandsButton extends Button {

    public static final Component ALLOW_COMMANDS_LABEL = Component.translatable("selectWorld.allowCommands");

    public HardcoreLanCommandsButton(int x, int y, int width, int height) {
        super(x, y, width, height, ALLOW_COMMANDS_LABEL, HardcoreLanCommandsButton::click, DEFAULT_NARRATION);
        setMessage(getComponent());
    }

    public static Component getComponent() {
        return ALLOW_COMMANDS_LABEL.copy().append(": ").append(CommonComponents.OPTION_OFF);
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
