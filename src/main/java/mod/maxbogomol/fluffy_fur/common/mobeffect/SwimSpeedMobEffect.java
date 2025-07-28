package mod.maxbogomol.fluffy_fur.common.mobeffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;

public class SwimSpeedMobEffect extends MobEffect {

    public SwimSpeedMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xfcfadd);
        addAttributeModifier(ForgeMod.SWIM_SPEED.get(), "77759BBE-BF50-490A-9919-5D0463C623AF", 0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
