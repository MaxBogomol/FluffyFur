package mod.maxbogomol.fluffy_fur.mixin.common;

import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Attributes.class)
public abstract class AttributesMixin {

    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/attributes/RangedAttribute;<init>(Ljava/lang/String;DDD)V", ordinal = 0), slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=attribute.name.generic.armor")), index = 3)
    private static double fluffy_fur$modifyArmor(double max) {
        if (max == 30.0D) return 100.0D;
        return max;
    }
}
