package mod.maxbogomol.fluffy_fur.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.client.playerskin.PlayerSkin;
import mod.maxbogomol.fluffy_fur.client.playerskin.PlayerSkinHandler;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class ExtraLayer <T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    public final HumanoidModel defaultModel;

    public ExtraLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
        this.defaultModel = (HumanoidModel) renderer.getModel();
    }

    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (livingEntity instanceof Player player) {
            PlayerSkin skin = PlayerSkinHandler.getSkin(player);

            if (skin != null) {
                skin.extraRender(poseStack, buffer, packedLight, player, limbSwing, limbSwingAmount, partialTick, ageInTicks, netHeadYaw, headPitch, defaultModel);
            }
        }
    }
}