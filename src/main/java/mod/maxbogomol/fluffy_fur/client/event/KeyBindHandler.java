package mod.maxbogomol.fluffy_fur.client.event;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.FluffyFurClient;
import mod.maxbogomol.fluffy_fur.client.playerskin.PlayerSkinHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class KeyBindHandler {

    @SubscribeEvent
    public static void onInput(InputEvent event) {
        if (FluffyFurClient.SKIN_MENU_KEY.isDown()) {
            if (PlayerSkinHandler.getSkin(FluffyFur.proxy.getPlayer()) == FluffyFurClient.MAXBOGOMOL_SKIN) {
                PlayerSkinHandler.setSkinPacket("");
            } else {
                PlayerSkinHandler.setSkinPacket(FluffyFurClient.MAXBOGOMOL_SKIN);
            }
        }
    }
}
