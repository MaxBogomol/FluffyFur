package mod.maxbogomol.fluffy_fur.client.playerskin;

import mod.maxbogomol.fluffy_fur.client.model.playerskin.EarsModel;
import mod.maxbogomol.fluffy_fur.client.model.playerskin.TailModel;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurModels;
import net.minecraft.world.entity.player.Player;

public class CatPlayerSkin extends FurryPlayerSkin {

    public CatPlayerSkin(String id) {
        super(id);
    }

    public EarsModel getEarsModel(Player player) {
        return FluffyFurModels.CAT_EARS;
    }

    public TailModel getTailModel(Player player) {
        return FluffyFurModels.CAT_TAIL;
    }
}
