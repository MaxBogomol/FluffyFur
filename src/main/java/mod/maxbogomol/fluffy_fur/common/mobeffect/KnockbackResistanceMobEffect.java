package mod.maxbogomol.fluffy_fur.common.mobeffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class KnockbackResistanceMobEffect extends MobEffect {

    public KnockbackResistanceMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x867b86);
        addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, "3DA0207A-9549-4093-A93C-2C582E618719", 0.1f, AttributeModifier.Operation.ADDITION);
    }
}
