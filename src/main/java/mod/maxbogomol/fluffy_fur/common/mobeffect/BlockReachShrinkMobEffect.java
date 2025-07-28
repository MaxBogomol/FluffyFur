package mod.maxbogomol.fluffy_fur.common.mobeffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;

public class BlockReachShrinkMobEffect extends MobEffect {

    public BlockReachShrinkMobEffect() {
        super(MobEffectCategory.HARMFUL, 0xa2faed);
        addAttributeModifier(ForgeMod.BLOCK_REACH.get(), "0C37A9D5-D83F-467A-A7BA-B79C2C85B5D9", -0.5f, AttributeModifier.Operation.ADDITION);
    }
}
