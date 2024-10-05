package mod.maxbogomol.fluffy_fur.integration.common.curios;

import mod.maxbogomol.fluffy_fur.common.itemskin.ItemClassSkinEntry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CurioClassSkinEntry extends ItemClassSkinEntry {

    public CurioClassSkinEntry(Class item, String skin) {
        super(item, skin);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return skin;
    }
}
