package mod.maxbogomol.fluffy_fur.client.playerskin;

import mod.maxbogomol.fluffy_fur.FluffyFurClient;
import mod.maxbogomol.fluffy_fur.client.model.playerskin.EarsModel;
import mod.maxbogomol.fluffy_fur.client.model.playerskin.TailModel;
import net.minecraft.world.entity.player.Player;

public class NanachiPlayerSkin extends FurryPlayerSkin {

    public NanachiPlayerSkin(String id) {
        super(id);
    }

    public EarsModel getEarsModel(Player player) {
        return FluffyFurClient.RABBIT_EARS_MODEL;
    }

    public TailModel getTailModel(Player player) {
        return FluffyFurClient.NANACHI_TAIL_MODEL;
    }
}
