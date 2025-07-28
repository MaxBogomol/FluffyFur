package mod.maxbogomol.fluffy_fur.common.mobeffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class AttackKnockbackWeaknessMobEffect extends MobEffect {

    public AttackKnockbackWeaknessMobEffect() {
        super(MobEffectCategory.HARMFUL, 0x867b86);
        addAttributeModifier(Attributes.ATTACK_KNOCKBACK, "E164AA50-9408-4690-9743-AF832C0E7AAA", -0.5f, AttributeModifier.Operation.ADDITION);
    }
}
