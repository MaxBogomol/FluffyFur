package mod.maxbogomol.fluffy_fur.client.playerskin;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.model.playerskin.EarsModel;
import mod.maxbogomol.fluffy_fur.client.model.playerskin.FurryPlayerSkinData;
import mod.maxbogomol.fluffy_fur.client.model.playerskin.PlayerSkinData;
import mod.maxbogomol.fluffy_fur.client.model.playerskin.TailModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class FurryPlayerSkin extends PlayerSkin {
    public ResourceLocation earsTexture;
    public ResourceLocation tailTexture;

    public int earsWiggleTime = 5;
    public int earsWiggleMinDelay = 60;
    public int earsWiggleMaxDelay = 120;

    public FurryPlayerSkin(String id) {
        super(id);
    }

    public FurryPlayerSkin setSlim(boolean slim) {
        this.slim = slim;
        return this;
    }

    public FurryPlayerSkin setSkinTexture(ResourceLocation skinTexture) {
        this.skinTexture = skinTexture;
        return this;
    }

    public FurryPlayerSkin setSkinBlinkTexture(ResourceLocation skinBlinkTexture) {
        this.skinBlinkTexture = skinBlinkTexture;
        return this;
    }

    public FurryPlayerSkin setBlinkTime(int blinkTime) {
        this.blinkTime = blinkTime;
        return this;
    }

    public FurryPlayerSkin setBlinkDelay(int blinkMinDelay, int blinkMaxDelay) {
        this.blinkMinDelay = blinkMinDelay;
        this.blinkMaxDelay = blinkMaxDelay;
        return this;
    }

    public FurryPlayerSkin setEarsTexture(ResourceLocation earsTexture) {
        this.earsTexture = earsTexture;
        return this;
    }

    public FurryPlayerSkin setTailTexture(ResourceLocation tailTexture) {
        this.tailTexture = tailTexture;
        return this;
    }

    public FurryPlayerSkin setEarsWiggleTime(int earsWiggleTime) {
        this.earsWiggleTime = earsWiggleTime;
        return this;
    }

    public FurryPlayerSkin setEarsWiggleDelay(int earsWiggleMinDelay, int earsWiggleMaxDelay) {
        this.earsWiggleMinDelay = earsWiggleMinDelay;
        this.earsWiggleMaxDelay = earsWiggleMaxDelay;
        return this;
    }

    public EarsModel getEarsModel(Player player) {
        return null;
    }

    public TailModel getTailModel(Player player) {
        return null;
    }

    public ResourceLocation getEars(Player player) {
        return earsTexture;
    }

    public ResourceLocation getTail(Player player) {
        return tailTexture;
    }

    public FurryPlayerSkinData getDefaultData() {
        return new FurryPlayerSkinData();
    }

    public FurryPlayerSkinData getData(Player player) {
        PlayerSkinData data = PlayerSkinHandler.getSkinData(player);
        if (data instanceof FurryPlayerSkinData furryData) {
            return furryData;
        }

        FurryPlayerSkinData furryData = getDefaultData();
        PlayerSkinHandler.setSkinData(player, furryData);
        return furryData;
    }

    @Override
    public void tick(Player player) {
        super.tick(player);
        FurryPlayerSkinData data = getData(player);
        int ticks = ClientTickHandler.ticksInGame;
        if (data.getEarsWiggleTick() + earsWiggleTime < ticks) {
            data.setEarsWiggleTick(ClientTickHandler.ticksInGame + random.nextInt(earsWiggleMinDelay, earsWiggleMaxDelay));
        }
    }

    @Override
    public void extraRender(PoseStack pose, MultiBufferSource buffer, int packedLight, Player player, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch, HumanoidModel defaultModel) {
        EarsModel earsModel = getEarsModel(player);
        ResourceLocation earsTexture = getEars(player);
        if (earsModel != null && earsTexture != null) {
            earsModel.young = player.isBaby();
            earsModel.copyFromDefault(defaultModel);
            earsModel.setupAnim(player, player.walkAnimation.position(partialTick), player.walkAnimation.speed(partialTick), player.tickCount + partialTick, netHeadYaw, headPitch);
            earsAnimation(earsModel, player, player.walkAnimation.position(partialTick), player.walkAnimation.speed(partialTick), player.tickCount + partialTick, netHeadYaw, headPitch);
            earsModel.renderToBuffer(pose, buffer.getBuffer(RenderType.entityCutoutNoCull(earsTexture)), packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        }

        TailModel tailModel = getTailModel(player);
        ResourceLocation tailTexture = getTail(player);
        if (tailModel != null && tailTexture != null) {
            tailModel.young = player.isBaby();
            tailModel.copyFromDefault(defaultModel);
            tailModel.setupAnim(player, player.walkAnimation.position(partialTick), player.walkAnimation.speed(partialTick), player.tickCount + partialTick, netHeadYaw, headPitch);
            tailAnimation(tailModel, player, player.walkAnimation.position(partialTick), player.walkAnimation.speed(partialTick), player.tickCount + partialTick, netHeadYaw, headPitch);
            tailModel.renderToBuffer(pose, buffer.getBuffer(RenderType.entityCutoutNoCull(tailTexture)), packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        }
    }

    public void earsAnimation(EarsModel model, Player player, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = player.isCrouching() ? 1f : 0;
        model.rightEar.zRot = (float) Math.toRadians(-15f - (20 * f));
        model.leftEar.zRot = (float) Math.toRadians(15f + (20 * f));
        model.rightEar.xRot = (float) Math.toRadians(10 * f);
        model.leftEar.xRot = (float) Math.toRadians(10 * f);
        model.rightEar.yRot = (float) Math.toRadians(-10 * f);
        model.leftEar.yRot = (float) Math.toRadians(10 * f);

        float partialTicks = Minecraft.getInstance().getPartialTick();
        int ticks = ClientTickHandler.ticksInGame;

        FurryPlayerSkinData data = getData(player);
        if (data.getEarsWiggleTick() < ticks) {
            float f1 = (float) Math.sin(Math.toRadians(360 * ((ticks + partialTicks - data.getEarsWiggleTick()) / (earsWiggleTime + 1f))));
            double f2 = Math.toRadians(f1 * 10f);
            model.rightEar.zRot = model.rightEar.zRot - (float) f2;
            model.leftEar.zRot = model.leftEar.zRot + (float) f2;
            model.rightEar.yRot = model.rightEar.yRot + (float) f2;
            model.leftEar.yRot = model.leftEar.yRot - (float) f2;
        }
    }

    public void tailAnimation(TailModel model, Player player, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float partialTicks = Minecraft.getInstance().getPartialTick();
        model.tail.xRot = (float) Math.toRadians(45f);
        if (player.isFallFlying()) model.tail.xRot = model.tail.xRot + (float) Math.toRadians(-65f);

        boolean flag = player.getFallFlyingTicks() > 4;
        float f = 1.0F;
        if (flag) {
            f = (float) player.getDeltaMovement().lengthSqr();
            f /= 0.2F;
            f *= f * f;
        }

        if (f < 1.0F) {
            f = 1.0F;
        }

        model.tail.yRot = (float) Math.toRadians(Math.cos(limbSwing * 0.6662F) * 15f * limbSwingAmount / f);

        double d0 = Mth.lerp(partialTicks, player.xCloakO, player.xCloak) - Mth.lerp(partialTicks, player.xo, player.getX());
        double d1 = Mth.lerp(partialTicks, player.yCloakO, player.yCloak) - Mth.lerp(partialTicks, player.yo, player.getY());
        double d2 = Mth.lerp(partialTicks, player.zCloakO, player.zCloak) - Mth.lerp(partialTicks, player.zo, player.getZ());
        f = Mth.rotLerp(partialTicks, player.yBodyRotO, player.yBodyRot);
        double d3 = Mth.sin(f * ((float) Math.PI / 180F));
        double d4 = (-Mth.cos(f * ((float) Math.PI / 180F)));
        float f1 = (float) d1 * 10.0F;
        f1 = Mth.clamp(f1, -6.0F, 32.0F);
        float f2 = (float) (d0 * d3 + d2 * d4) * 100.0F;
        f2 = Mth.clamp(f2, 0.0F, 150.0F);
        float f3 = (float) (d0 * d4 - d2 * d3) * 100.0F;
        f3 = Mth.clamp(f3, -20.0F, 20.0F);
        if (f2 < 0.0F) {
            f2 = 0.0F;
        }

        float f4 = Mth.lerp(partialTicks, player.oBob, player.bob);
        f1 += Mth.sin(Mth.lerp(partialTicks, player.walkDistO, player.walkDist) * 6.0F) * 32.0F * f4;
        if (player.isCrouching()) {
            f1 += 25.0F;
        }

        model.tail.xRot = model.tail.xRot + (float) Math.toRadians(6.0F + f2 / 2.0F + f1);
        model.tail.yRot = model.tail.yRot + (float) Math.toRadians(f3 / 2.0F);
        model.tail.zRot = (float) Math.toRadians(f3 / 2.0F);
    }
}
