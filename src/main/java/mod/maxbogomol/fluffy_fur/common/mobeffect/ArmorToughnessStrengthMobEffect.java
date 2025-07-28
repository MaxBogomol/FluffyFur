package mod.maxbogomol.fluffy_fur.common.mobeffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ArmorToughnessStrengthMobEffect extends MobEffect {

    public ArmorToughnessStrengthMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xa2faed);
        addAttributeModifier(Attributes.ARMOR_TOUGHNESS, "52E46DF1-ACC4-468D-B182-45063E54013F", 1f, AttributeModifier.Operation.ADDITION);
    }
}
