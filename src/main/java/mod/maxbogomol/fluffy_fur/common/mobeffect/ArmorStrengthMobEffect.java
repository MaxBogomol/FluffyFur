package mod.maxbogomol.fluffy_fur.common.mobeffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ArmorStrengthMobEffect extends MobEffect {

    public ArmorStrengthMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xfcfadd);
        addAttributeModifier(Attributes.ARMOR, "2BC7BD0D-5865-4C7A-A2CC-648E6C720A43", 1f, AttributeModifier.Operation.ADDITION);
    }
}
