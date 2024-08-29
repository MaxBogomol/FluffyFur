package mod.maxbogomol.fluffy_fur.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.client.event.BowHandler;
import mod.maxbogomol.fluffy_fur.common.item.ICustomAnimationItem;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"), method = "renderArmWithItem")
    public void fluffy_fur$renderArmWithItem(AbstractClientPlayer pPlayer, float pPartialTicks, float pPitch, InteractionHand pHand, float pSwingProgress, ItemStack pStack, float pEquippedProgress, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, CallbackInfo ci) {
        if (pPlayer.isUsingItem() && pPlayer.getUseItemRemainingTicks() > 0 && pPlayer.getUsedItemHand() == pHand) {
            if (pStack.getItem() instanceof ICustomAnimationItem item) {
                if (item.getAnimation(pStack) != null) {
                    item.getAnimation(pStack).renderArmWithItem(pPlayer, pPartialTicks, pPitch, pHand, pSwingProgress, pStack, pEquippedProgress, pPoseStack, pBuffer, pCombinedLight);
                }
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "evaluateWhichHandsToRender", cancellable = true)
    private static void fluffy_fur$evaluateWhichHandsToRender(LocalPlayer pPlayer, CallbackInfoReturnable<ItemInHandRenderer.HandRenderSelection> cir) {
        ItemStack itemStack = pPlayer.getUseItem();
        InteractionHand hand = pPlayer.getUsedItemHand();
        for (Item item : BowHandler.getBows()) {
            if (itemStack.is(item)) {
                cir.setReturnValue(ItemInHandRenderer.HandRenderSelection.onlyForHand(hand));
            }
        }
    }
}
