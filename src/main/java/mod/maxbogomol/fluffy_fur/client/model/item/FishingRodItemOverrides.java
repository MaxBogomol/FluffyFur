package mod.maxbogomol.fluffy_fur.client.model.item;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class FishingRodItemOverrides extends CustomItemOverrides {
    public BakedModel castModel;

    @Override
    public BakedModel resolve(BakedModel originalModel, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        if (getCast(stack, level, entity, seed) > 0) {
            return castModel;
        }
        return originalModel;
    }

    public float getCast(ItemStack stack, ClientLevel level, LivingEntity entity, int seed) {
        if (entity == null) {
            return 0.0F;
        } else {
            boolean flag = entity.getMainHandItem() == stack;
            boolean flag1 = entity.getOffhandItem() == stack;
            if (entity.getMainHandItem().getItem() instanceof FishingRodItem) {
                flag1 = false;
            }

            return (flag || flag1) && entity instanceof Player && ((Player) entity).fishing != null ? 1.0F : 0.0F;
        }
    }
}