package mod.maxbogomol.fluffy_fur.client.playerskin;

import mod.maxbogomol.fluffy_fur.client.model.playerskin.EarsModel;
import mod.maxbogomol.fluffy_fur.client.model.playerskin.TailModel;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurModels;
import net.minecraft.world.entity.player.Player;

public class FoxPlayerSkin extends FurryPlayerSkin {

    public FoxPlayerSkin(String id) {
        super(id);
    }

    public EarsModel getEarsModel(Player player) {
        return FluffyFurModels.FOX_EARS;
    }

    public TailModel getTailModel(Player player) {
        return FluffyFurModels.FOX_TAIL;
    }
}
