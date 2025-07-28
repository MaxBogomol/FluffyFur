package mod.maxbogomol.fluffy_fur.common.mobeffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class AttackSlowdownMobEffect extends MobEffect {

    public AttackSlowdownMobEffect() {
        super(MobEffectCategory.HARMFUL, 0xfcfadd);
        addAttributeModifier(Attributes.ATTACK_SPEED, "55FCED67-E92A-486E-9800-B47F202C4386", -0.1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
