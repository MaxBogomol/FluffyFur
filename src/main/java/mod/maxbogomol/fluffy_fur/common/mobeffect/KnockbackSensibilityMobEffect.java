package mod.maxbogomol.fluffy_fur.common.mobeffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class KnockbackSensibilityMobEffect extends MobEffect {

    public KnockbackSensibilityMobEffect() {
        super(MobEffectCategory.HARMFUL, 0x867b86);
        addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, "63B66ECF-B2BB-4339-8D12-4D534A3F06BB", -1f, AttributeModifier.Operation.ADDITION);
    }
}
