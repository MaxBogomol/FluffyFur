package mod.maxbogomol.fluffy_fur.common.mobeffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class AttackSpeedMobEffect extends MobEffect {

    public AttackSpeedMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xfcfadd);
        addAttributeModifier(Attributes.ATTACK_SPEED, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", 0.1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
