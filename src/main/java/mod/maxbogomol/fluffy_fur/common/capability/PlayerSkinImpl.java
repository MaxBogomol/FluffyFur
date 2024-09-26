package mod.maxbogomol.fluffy_fur.common.capability;

import mod.maxbogomol.fluffy_fur.client.model.playerskin.PlayerSkinData;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class PlayerSkinImpl implements IPlayerSkin, INBTSerializable<CompoundTag> {
    PlayerSkinData data;
    String skinId = "";
    String skinEffectId = "";
    String skinCapeId = "";

    @Override
    public void setSkinData(PlayerSkinData data) {
        this.data = data;
    }

    @Override
    public PlayerSkinData getSkinData() {
        return data;
    }

    @Override
    public void setSkin(String id) {
        this.skinId = id;
    }

    @Override
    public String getSkin() {
        return skinId;
    }

    @Override
    public void setSkinEffect(String id) {
        this.skinEffectId = id;
    }

    @Override
    public String getSkinEffect() {
        return skinEffectId;
    }

    @Override
    public void setSkinCape(String id) {
        this.skinCapeId = id;
    }

    @Override
    public String getSkinCape() {
        return skinCapeId;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag wrapper = new CompoundTag();
        wrapper.putString("skin", skinId);
        wrapper.putString("skinEffect", skinEffectId);
        wrapper.putString("skinCape", skinCapeId);

        return wrapper;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("skin")) {
            skinId = nbt.getString("skin");
        }
        if (nbt.contains("skinEffect")) {
            skinEffectId = nbt.getString("skinEffect");
        }
        if (nbt.contains("skinCape")) {
            skinCapeId = nbt.getString("skinCape");
        }
    }
}
