package mod.maxbogomol.fluffy_fur.mixin.client;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import mod.maxbogomol.fluffy_fur.client.tooltip.AttributeTooltipModifier;
import mod.maxbogomol.fluffy_fur.client.tooltip.TooltipModifierHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Unique
    public AttributeModifier fluffy_fur$attributeModifier;

    @Unique
    public List<Component> fluffy_fur$componentList;

    @ModifyVariable(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;getId()Ljava/util/UUID;", ordinal = 0), index = 13)
    public AttributeModifier fluffy_fur$getTooltip(AttributeModifier value) {
        this.fluffy_fur$attributeModifier = value;
        return value;
    }

    @ModifyVariable(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;getOperation()Lnet/minecraft/world/entity/ai/attributes/AttributeModifier$Operation;", ordinal = 0), index = 16)
    public boolean fluffy_fur$getTooltip(boolean value, @Nullable Player player, TooltipFlag flag) {
        if (player != null) {
            for (AttributeTooltipModifier modifier : TooltipModifierHandler.getModifiers()) {
                if (modifier.isToolBase(fluffy_fur$attributeModifier, player, flag)) {
                    return true;
                }
            }
        }
        return value;
    }

    @ModifyVariable(method = "getTooltipLines", at = @At("STORE"))
    public Multimap<Attribute, AttributeModifier> fluffy_fur$getTooltip(Multimap<Attribute, AttributeModifier> map, @Nullable Player player, TooltipFlag flag) {
        if (player != null) {
            Multimap<Attribute, AttributeModifier> copied = LinkedHashMultimap.create();
            for (Map.Entry<Attribute, AttributeModifier> entry : map.entries()) {
                Attribute key = entry.getKey();
                AttributeModifier modifier = entry.getValue();
                double amount = modifier.getAmount();
                AttributeModifier.Operation operation = modifier.getOperation();
                boolean flagAdd = false;

                for (AttributeTooltipModifier tooltipModifier : TooltipModifierHandler.getModifiers()) {
                    if (tooltipModifier.isModifiable(key, modifier, player, flag)) {
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

    @ModifyVariable(method = "getTooltipLines", at = @At("STORE"))
    public List<Component> fluffy_fur$getTooltip(List<Component> list, @Nullable Player player, TooltipFlag flag) {
        fluffy_fur$componentList = list;
        return list;
    }

    @Inject(at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;getId()Ljava/util/UUID;"), method = "getTooltipLines")
    public void fluffy_fur$getTooltip(Player player, TooltipFlag isAdvanced, CallbackInfoReturnable<List<Component>> cir) {
        TooltipModifierHandler.attributeTooltipSize = fluffy_fur$componentList.size();
    }
}