package mod.maxbogomol.fluffy_fur.client.tooltip;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;

public class AttributeTooltipModifier {

    public boolean isToolBase(AttributeModifier modifier, Player player, TooltipFlag flag) {
        return false;
    }

    public boolean isModifiable(Attribute key, AttributeModifier modifier, Player player, TooltipFlag flag) {
        return isToolBase(modifier, player, flag);
    }

    public ModifyResult modify(AttributeModifier modifier, double amount, AttributeModifier.Operation operation) {
        return new ModifyResult(modifier, amount, operation);
    }

    public static class ModifyResult {
        public AttributeModifier modifier;
        public double amount;
        public AttributeModifier.Operation operation;

        public ModifyResult(AttributeModifier modifier, double amount, AttributeModifier.Operation operation) {
            this.modifier = modifier;
            this.amount = amount;
            this.operation = operation;
        }

        public AttributeModifier getModifier() {
            return modifier;
        }

        public double getAmount() {
            return amount;
        }

        public AttributeModifier.Operation getOperation() {
            return operation;
        }
    }
}
