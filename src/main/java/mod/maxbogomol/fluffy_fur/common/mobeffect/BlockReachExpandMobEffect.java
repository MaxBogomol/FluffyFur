package mod.maxbogomol.fluffy_fur.common.mobeffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;

public class BlockReachExpandMobEffect extends MobEffect {

    public BlockReachExpandMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xa2faed);
        addAttributeModifier(ForgeMod.BLOCK_REACH.get(), "5560D74F-A481-441A-AF78-29AC7718415C", 0.5f, AttributeModifier.Operation.ADDITION);
    }
}
