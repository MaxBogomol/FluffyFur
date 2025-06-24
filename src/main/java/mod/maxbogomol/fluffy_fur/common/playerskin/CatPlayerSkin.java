package mod.maxbogomol.fluffy_fur.common.playerskin;

import mod.maxbogomol.fluffy_fur.client.model.playerskin.EarsModel;
import mod.maxbogomol.fluffy_fur.client.model.playerskin.TailModel;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurModels;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CatPlayerSkin extends FurryPlayerSkin {

    public CatPlayerSkin(String id) {
        super(id);
    }

    @OnlyIn(Dist.CLIENT)
    public EarsModel getEarsModel(Player player) {
        return FluffyFurModels.CAT_EARS;
    }

    @OnlyIn(Dist.CLIENT)
    public TailModel getTailModel(Player player) {
        return FluffyFurModels.CAT_TAIL;
    }
}
