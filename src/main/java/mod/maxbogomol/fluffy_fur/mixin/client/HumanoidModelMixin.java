package mod.maxbogomol.fluffy_fur.mixin.client;

import mod.maxbogomol.fluffy_fur.client.animation.ItemAnimation;
import mod.maxbogomol.fluffy_fur.common.item.ICustomAnimationItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin<T extends LivingEntity>  {

    @Inject(at = @At("RETURN"), method = "setupAnim")
    public void fluffy_fur$setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        HumanoidModel self = (HumanoidModel) ((Object) this);
        if (entity instanceof Player player) {
            for (InteractionHand hand : InteractionHand.values()) {
                if (player.getItemInHand(hand).getItem() instanceof ICustomAnimationItem item) {
                    ItemStack stack = player.getItemInHand(hand);
                    ItemAnimation animation = item.getAnimation(stack);
                    if (animation != null) {
                        boolean use = true;
                        if (animation.isOnlyItemUse()) use = ItemAnimation.isItemUse(player) && player.getUsedItemHand() == hand;
                        if (use) {
                            if (ItemAnimation.isRightHand(player, hand)) {
                                item.getAnimation(stack).setupAnimRight(self, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                            } else {
                                item.getAnimation(stack).setupAnimLeft(self, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                            }
                            item.getAnimation(stack).setupAnim(self, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                        }
                    }
                }
            }
        }
    }
}
