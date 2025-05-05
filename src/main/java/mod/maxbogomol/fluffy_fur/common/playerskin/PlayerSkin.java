package mod.maxbogomol.fluffy_fur.common.playerskin;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.model.playerskin.PlayerSkinData;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class PlayerSkin {
    public String id;

    public boolean slim = false;
    public ResourceLocation skinTexture;
    public ResourceLocation skinBlinkTexture;

    public int blinkTime = 3;
    public int blinkMinDelay = 40;
    public int blinkMaxDelay = 100;

    public static Random random = new Random();

    public PlayerSkin(String id) {
        this.id = id;
    }

    public PlayerSkin setSlim(boolean slim) {
        this.slim = slim;
        return this;
    }

    public PlayerSkin setSkinTexture(ResourceLocation skinTexture) {
        this.skinTexture = skinTexture;
        return this;
    }

    public PlayerSkin setSkinBlinkTexture(ResourceLocation skinBlinkTexture) {
        this.skinBlinkTexture = skinBlinkTexture;
        return this;
    }

    public PlayerSkin setBlinkTime(int blinkTime) {
        this.blinkTime = blinkTime;
        return this;
    }

    public PlayerSkin setBlinkDelay(int blinkMinDelay, int blinkMaxDelay) {
        this.blinkMinDelay = blinkMinDelay;
        this.blinkMaxDelay = blinkMaxDelay;
        return this;
    }

    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getSkin(Player player) {
        PlayerSkinData data = getData(player);
        int ticks = ClientTickHandler.ticksInGame;
        if (data.getBlinkTick() < ticks) {
            return skinBlinkTexture;
        }
        return skinTexture;
    }

    public boolean getSlim(Player player) {
        return slim;
    }

    public String getId() {
        return id;
    }

    public PlayerSkinData getDefaultData() {
        return new PlayerSkinData();
    }

    public PlayerSkinData getData(Player player) {
        PlayerSkinData data = PlayerSkinHandler.getSkinData(player);
        if (data == null) {
            data = getDefaultData();
            PlayerSkinHandler.setSkinData(player, data);
            return data;
        }
        return data;
    }

    public void tick(Player player) {
        if (player.level().isClientSide()) {
            PlayerSkinData data = getData(player);
            int ticks = ClientTickHandler.ticksInGame;
            if (data.getBlinkTick() + blinkTime < ticks) {
                data.setBlinkTick(ClientTickHandler.ticksInGame + random.nextInt(blinkMinDelay, blinkMaxDelay));
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void extraRender(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Player player, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch, HumanoidModel defaultModel) {

    }

    @OnlyIn(Dist.CLIENT)
    public static ResourceLocation getSkinLocation(String mod, String texture) {
        return new ResourceLocation(mod, "textures/entity/skin/" + texture + ".png");
    }
}
