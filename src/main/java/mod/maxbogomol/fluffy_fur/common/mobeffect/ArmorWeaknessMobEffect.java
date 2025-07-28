package mod.maxbogomol.fluffy_fur.common.mobeffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ArmorWeaknessMobEffect extends MobEffect {

    public ArmorWeaknessMobEffect() {
        super(MobEffectCategory.HARMFUL, 0xfcfadd);
        addAttributeModifier(Attributes.ARMOR, "BEF46BDC-E3D0-4B71-BFC3-45D7264D3F5C", -1f, AttributeModifier.Operation.ADDITION);
    }
}
