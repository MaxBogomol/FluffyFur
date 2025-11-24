package mod.maxbogomol.fluffy_fur.mixin.client;

import com.google.common.collect.Maps;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.tooltip.AttributeTooltipModifier;
import mod.maxbogomol.fluffy_fur.client.tooltip.TooltipModifierHandler;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Map;

@Mixin(PotionUtils.class)
public class PotionUtilsMixin {

    @ModifyVariable(method = "addPotionTooltip*", at = @At("STORE"), ordinal = 0)
    private static Map<Attribute, AttributeModifier> fluffy_fur$addPotionTooltipModifier(Map<Attribute, AttributeModifier> map) {
        Player player = FluffyFur.proxy.getPlayer();
        if (player != null && map != null && !map.isEmpty()) {
            Map<Attribute, AttributeModifier> copied = Maps.newHashMap();
            for (Map.Entry<Attribute, AttributeModifier> entry : map.entrySet()) {
                Attribute key = entry.getKey();
                AttributeModifier modifier = entry.getValue();
                double amount = modifier.getAmount();
                AttributeModifier.Operation operation = modifier.getOperation();
                boolean flagAdd = false;

                for (AttributeTooltipModifier tooltipModifier : TooltipModifierHandler.getModifiers()) {
                    if (tooltipModifier.isModifiable(key, modifier, player, TooltipFlag.NORMAL)) {
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

            return copied;
        }
        return map;
    }
}