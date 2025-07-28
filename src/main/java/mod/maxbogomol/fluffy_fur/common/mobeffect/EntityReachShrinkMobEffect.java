package mod.maxbogomol.fluffy_fur.common.mobeffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;

public class EntityReachShrinkMobEffect extends MobEffect {

    public EntityReachShrinkMobEffect() {
        super(MobEffectCategory.HARMFUL, 0xa2faed);
        addAttributeModifier(ForgeMod.ENTITY_REACH.get(), "7E842736-E0B4-4176-A0A0-7DE9F5F29840", -0.5f, AttributeModifier.Operation.ADDITION);
    }
}
