package mod.maxbogomol.fluffy_fur.common.mobeffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;

public class SwimSlownessMobEffect extends MobEffect {

    public SwimSlownessMobEffect() {
        super(MobEffectCategory.HARMFUL, 0xfcfadd);
        addAttributeModifier(ForgeMod.SWIM_SPEED.get(), "ACCECDE9-EBFC-48BF-B3E9-88E6A3CF6419", -0.15f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
