package mod.maxbogomol.fluffy_fur.common.capability;

import mod.maxbogomol.fluffy_fur.client.model.playerskin.PlayerSkinData;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class PlayerSkinImpl implements IPlayerSkin, INBTSerializable<CompoundTag> {
    PlayerSkinData data;
    String skinId = "";

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
    public CompoundTag serializeNBT() {
        CompoundTag wrapper = new CompoundTag();
        wrapper.putString("skin", skinId);

        return wrapper;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("skin")) {
            skinId = nbt.getString("skin");
        }
    }
}
