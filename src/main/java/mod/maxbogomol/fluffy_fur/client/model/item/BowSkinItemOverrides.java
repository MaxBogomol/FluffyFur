package mod.maxbogomol.fluffy_fur.client.model.item;

import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class BowSkinItemOverrides extends BowItemOverrides {

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
        if (getPulling(stack, level, entity, seed) > 0) {
            float pull = getPull(stack, level, entity, seed);
            String skinStr = skin.getItemModelName(stack) + "_pulling_0";
            if (pull >= 0.65f) {
                skinStr = skin.getItemModelName(stack) + "_pulling_1";
            }
            if (pull >= 0.9f) {
                skinStr = skin.getItemModelName(stack) + "_pulling_2";
            }
            return skinStr;
        }
        return skin.getItemModelName(stack);
    }
}