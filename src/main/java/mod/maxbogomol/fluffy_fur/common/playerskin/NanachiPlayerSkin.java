package mod.maxbogomol.fluffy_fur.common.playerskin;

import mod.maxbogomol.fluffy_fur.client.model.playerskin.EarsModel;
import mod.maxbogomol.fluffy_fur.client.model.playerskin.TailModel;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurModels;
import net.minecraft.world.entity.player.Player;

public class NanachiPlayerSkin extends FurryPlayerSkin {

    public NanachiPlayerSkin(String id) {
        super(id);
    }

    public EarsModel getEarsModel(Player player) {
        return FluffyFurModels.RABBIT_EARS;
    }

    public TailModel getTailModel(Player player) {
        return FluffyFurModels.NANACHI_TAIL;
    }
}
