package mod.maxbogomol.fluffy_fur.mixin.common;

import mod.maxbogomol.fluffy_fur.config.FluffyFurConfig;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Zombie.class)
public abstract class ZombieMixin {

    @Inject(method = "handleAttributes", at = @At("RETURN"))
    private void fluffy_fur$handleAttributes(float difficulty, CallbackInfo ci) {
        if (FluffyFurConfig.LEADER_ZOMBIE_FIX.get()) {
            Zombie self = (Zombie) ((Object) this);
            boolean leader = false;
            for (AttributeInstance attributeInstance : self.getAttributes().getDirtyAttributes()) {
                for (AttributeModifier attributeModifier : attributeInstance.getModifiers()) {
                    if (attributeModifier.getName().equals("Leader zombie bonus")) {
                        self.setHealth(self.getMaxHealth());
                        leader = true;
                        break;
                    }
                }
                if (leader) break;
            }
        }
    }
}
