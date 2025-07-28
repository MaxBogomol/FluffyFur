package mod.maxbogomol.fluffy_fur.common.mobeffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;

public class EntityReachExpandMobEffect extends MobEffect {

    public EntityReachExpandMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xa2faed);
        addAttributeModifier(ForgeMod.ENTITY_REACH.get(), "37BDB3D4-F061-4522-BBFA-D39CCF3F01F6", 0.5f, AttributeModifier.Operation.ADDITION);
    }
}
