package mod.maxbogomol.fluffy_fur.common.mobeffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class AttackKnockbackStrengthMobEffect extends MobEffect {

    public AttackKnockbackStrengthMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x867b86);
        addAttributeModifier(Attributes.ATTACK_KNOCKBACK, "7DAB66B5-DCA3-4B18-A649-4407857FC360", 0.5f, AttributeModifier.Operation.ADDITION);
    }
}
