package mod.maxbogomol.fluffy_fur.mixin.client.curios;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import mod.maxbogomol.fluffy_fur.client.tooltip.AttributeTooltipModifier;
import mod.maxbogomol.fluffy_fur.client.tooltip.TooltipModifierHandler;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import top.theillusivec4.curios.client.ClientEventHandler;

import java.util.Map;

@Mixin(ClientEventHandler.class)
public abstract class CurioTooltipMixin implements IMixinConfigPlugin {

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.startsWith("top.theillusivec4.curios.client.ClientEventHandler")) {
            return ModList.get().isLoaded("curios");
        }
        return false;
    }

    @ModifyVariable(method = "onTooltip", at = @At("STORE"), remap = false)
    public Multimap<Attribute, AttributeModifier> fluffy_fur$getTooltip(Multimap<Attribute, AttributeModifier> multimap, ItemTooltipEvent event) {
        if (event != null && multimap != null) {
            Multimap<Attribute, AttributeModifier> copied = LinkedHashMultimap.create();
            for (Map.Entry<Attribute, AttributeModifier> entry : multimap.entries()) {
                Attribute key = entry.getKey();
                if (key != null) {
                    AttributeModifier modifier = entry.getValue();
                    double amount = modifier.getAmount();
                    boolean flagAdd = false;
                    AttributeModifier.Operation operation = modifier.getOperation();

                    for (AttributeTooltipModifier tooltipModifier : TooltipModifierHandler.getModifiers()) {
                        if (tooltipModifier.isModifiable(key, modifier, event.getEntity(), event.getFlags())) {
                            AttributeTooltipModifier.ModifyResult result = tooltipModifier.modify(modifier, amount, operation);
                            modifier = result.getModifier();
                            amount = result.getAmount();
                            operation = result.getOperation();
                            flagAdd = true;
                            break;
                        }
                    }

                    if (flagAdd) {
                        copied.put(key, new AttributeModifier(modifier.getId(), modifier.getName(), amount, operation));
                    } else {
                        copied.put(key, modifier);
                    }
                }
            }

            return copied;
        }
        return multimap;
    }
}
