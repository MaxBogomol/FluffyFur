package mod.maxbogomol.fluffy_fur.common.capability;

import mod.maxbogomol.fluffy_fur.client.model.playerskin.PlayerSkinData;
import mod.maxbogomol.fluffy_fur.common.playerskin.PlayerSkin;
import mod.maxbogomol.fluffy_fur.common.playerskin.PlayerSkinCape;
import mod.maxbogomol.fluffy_fur.common.playerskin.PlayerSkinEffect;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public interface IPlayerSkin {
    Capability<IPlayerSkin> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});

    void setSkinData(PlayerSkinData data);
    PlayerSkinData getSkinData();

    void setSkin(PlayerSkin skin);
    PlayerSkin getSkin();

    void setSkinEffect(PlayerSkinEffect effect);
    PlayerSkinEffect getSkinEffect();

    void setSkinCape(PlayerSkinCape cape);
    PlayerSkinCape getSkinCape();
}
