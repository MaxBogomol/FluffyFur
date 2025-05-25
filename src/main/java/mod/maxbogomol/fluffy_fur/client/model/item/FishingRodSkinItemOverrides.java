package mod.maxbogomol.fluffy_fur.client.model.item;

import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class FishingRodSkinItemOverrides extends FishingRodItemOverrides {

    @Override
    public BakedModel resolve(BakedModel originalModel, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        ItemSkin skin = ItemSkin.getSkinFromItem(stack);
        if (skin != null) {
            String skinStr = getSkinModel(skin, originalModel, stack, level, entity, seed);
            if (skinStr != null) return ItemSkinModels.getModelSkins(skinStr);
        }
        return super.resolve(originalModel, stack, level, entity, seed);
    }

    public String getSkinModel(ItemSkin skin, BakedModel originalModel, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        if (getCast(stack, level, entity, seed) > 0) {
            return skin.getItemModelName(stack) + "_cast";
        }
        return skin.getItemModelName(stack);
    }
}