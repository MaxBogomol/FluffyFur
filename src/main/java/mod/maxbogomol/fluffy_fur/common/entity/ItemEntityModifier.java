package mod.maxbogomol.fluffy_fur.common.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemEntityModifier {

    public boolean isItem(Level level, ItemEntity entity, ItemStack stack) {
        return false;
    }

    public void tick(Level level, ItemEntity entity, ItemStack stack) {

    }

    public boolean rejectHurt(Level level, ItemEntity entity, ItemStack stack, DamageSource source, float amount) {
        return false;
    }
}
