package mod.maxbogomol.fluffy_fur.client.model.item;

import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class ItemSkinItemOverrides extends CustomItemOverrides {

    @Override
    public BakedModel resolve(BakedModel originalModel, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        ItemSkin skin = ItemSkin.getSkinFromItem(stack);
        if (skin != null) {
            String skinStr = skin.getItemModelName(stack);
            if (skinStr != null) return ItemSkinModels.getModelSkins(skinStr);
        }
        return originalModel;
    }
}