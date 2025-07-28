package mod.maxbogomol.fluffy_fur.common.mobeffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ArmorToughnessWeaknessMobEffect extends MobEffect {

    public ArmorToughnessWeaknessMobEffect() {
        super(MobEffectCategory.HARMFUL, 0xa2faed);
        addAttributeModifier(Attributes.ARMOR_TOUGHNESS, "D9192D9C-613D-4CC4-BEBC-0385C8412FE7", -1f, AttributeModifier.Operation.ADDITION);
    }
}
