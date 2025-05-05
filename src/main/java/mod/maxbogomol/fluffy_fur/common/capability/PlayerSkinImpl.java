package mod.maxbogomol.fluffy_fur.common.capability;

import mod.maxbogomol.fluffy_fur.client.model.playerskin.PlayerSkinData;
import mod.maxbogomol.fluffy_fur.common.playerskin.PlayerSkin;
import mod.maxbogomol.fluffy_fur.common.playerskin.PlayerSkinCape;
import mod.maxbogomol.fluffy_fur.common.playerskin.PlayerSkinEffect;
import mod.maxbogomol.fluffy_fur.common.playerskin.PlayerSkinHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class PlayerSkinImpl implements IPlayerSkin, INBTSerializable<CompoundTag> {
    PlayerSkinData data;
    PlayerSkin skin;
    PlayerSkinEffect skinEffect;
    PlayerSkinCape cape;

    @Override
    public void setSkinData(PlayerSkinData data) {
        this.data = data;
    }

    @Override
    public PlayerSkinData getSkinData() {
        return data;
    }

    @Override
    public void setSkin(PlayerSkin skin) {
        this.skin = skin;
    }

    @Override
    public PlayerSkin getSkin() {
        return skin;
    }

    @Override
    public void setSkinEffect(PlayerSkinEffect effect) {
        this.skinEffect = effect;
    }

    @Override
    public PlayerSkinEffect getSkinEffect() {
        return skinEffect;
    }

    @Override
    public void setSkinCape(PlayerSkinCape cape) {
        this.cape = cape;
    }

    @Override
    public PlayerSkinCape getSkinCape() {
        return cape;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag wrapper = new CompoundTag();
        if (skin != null) {
            wrapper.putString("skin", skin.getId());
        }
        if (skinEffect != null) {
            wrapper.putString("skinEffect", skinEffect.getId());
        }
        if (cape != null) {
            wrapper.putString("skinCape", cape.getId());
        }

        return wrapper;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("skin")) {
            skin = PlayerSkinHandler.getSkin(nbt.getString("skin"));
        }
        if (nbt.contains("skinEffect")) {
            skinEffect = PlayerSkinHandler.getSkinEffect(nbt.getString("skinEffect"));
        }
        if (nbt.contains("skinCape")) {
            cape = PlayerSkinHandler.getSkinCape(nbt.getString("skinCape"));
        }
    }
}
