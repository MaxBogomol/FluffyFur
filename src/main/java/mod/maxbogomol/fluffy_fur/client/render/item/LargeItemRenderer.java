package mod.maxbogomol.fluffy_fur.client.render.item;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.client.model.item.CustomItemOverrides;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class LargeItemRenderer {

    public static ModelResourceLocation getModelResourceLocation(String modId, String item) {
        return new ModelResourceLocation(new ResourceLocation(modId, item + "_in_hand"), "inventory");
    }

    public static ModelResourceLocation getInHandModelResourceLocation(String modId, String item) {
        return new ModelResourceLocation(new ResourceLocation(modId, item + "_in_hand"), "inventory");
    }

    public static ModelResourceLocation getInHeadModelResourceLocation(String modId, String item) {
        return new ModelResourceLocation(new ResourceLocation(modId, item + "_in_head"), "inventory");
    }

    public static void bakeModel(Map<ResourceLocation, BakedModel> map, String modId, String item, CustomItemOverrides itemOverrides) {
        bakeInHandModel(map, modId, item, itemOverrides);
    }

    public static void bakeModel(Map<ResourceLocation, BakedModel> map, String modId, String item) {
        bakeModel(map, modId, item, new CustomItemOverrides());
    }

    public static void bakeInHandModel(Map<ResourceLocation, BakedModel> map, String modId, String item, CustomItemOverrides itemOverrides) {
        ResourceLocation modelInventory = new ModelResourceLocation(new ResourceLocation(modId, item), "inventory");
        ResourceLocation modelHand = new ModelResourceLocation(new ResourceLocation(modId, item + "_in_hand"), "inventory");

        BakedModel bakedModelDefault = map.get(modelInventory);
        BakedModel bakedModelHand = map.get(modelHand);
        BakedModel modelWrapper = new LargeItemModel(bakedModelDefault, bakedModelHand, itemOverrides);
        map.put(modelInventory, modelWrapper);
    }

    public static void bakeInHandModel(Map<ResourceLocation, BakedModel> map, String modId, String item) {
        bakeInHandModel(map, modId, item, new CustomItemOverrides());
    }

    public static void bakeInHeadModel(Map<ResourceLocation, BakedModel> map, String modId, String item, CustomItemOverrides itemOverrides) {
        ResourceLocation modelInventory = new ModelResourceLocation(new ResourceLocation(modId, item), "inventory");
        ResourceLocation modelHand = new ModelResourceLocation(new ResourceLocation(modId, item + "_in_head"), "inventory");

        BakedModel bakedModelDefault = map.get(modelInventory);
        BakedModel bakedModelHand = map.get(modelHand);
        BakedModel modelWrapper = new HeadItemModel(bakedModelDefault, bakedModelHand, itemOverrides);
        map.put(modelInventory, modelWrapper);
    }

    public static void bakeInHeadModel(Map<ResourceLocation, BakedModel> map, String modId, String item) {
        bakeInHeadModel(map, modId, item, new CustomItemOverrides());
    }

    public static class LargeItemModel implements BakedModel {
        public final BakedModel bakedModelDefault;
        public final BakedModel bakedModelHand;
        public final CustomItemOverrides itemOverrides;

        public LargeItemModel(BakedModel bakedModelDefault, BakedModel bakedModelHand) {
            this.bakedModelDefault = bakedModelDefault;
            this.bakedModelHand = bakedModelHand;
            this.itemOverrides = new CustomItemOverrides();
        }

        public LargeItemModel(BakedModel bakedModelDefault, BakedModel bakedModelHand, CustomItemOverrides itemOverrides) {
            this.bakedModelDefault = bakedModelDefault;
            this.bakedModelHand = bakedModelHand;
            this.itemOverrides = itemOverrides;
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction direction, RandomSource random) {
            return bakedModelDefault.getQuads(state, direction, random);
        }

        @Override
        public boolean useAmbientOcclusion() {
            return bakedModelDefault.useAmbientOcclusion();
        }

        @Override
        public boolean isGui3d() {
            return bakedModelDefault.isGui3d();
        }

        @Override
        public boolean usesBlockLight() {
            return bakedModelDefault.usesBlockLight();
        }

        @Override
        public boolean isCustomRenderer() {
            return bakedModelDefault.isCustomRenderer();
        }

        @Override
        public TextureAtlasSprite getParticleIcon() {
            return bakedModelDefault.getParticleIcon();
        }

        @Override
        public ItemOverrides getOverrides() {
            return itemOverrides;
        }

        @Override
        public BakedModel applyTransform(ItemDisplayContext context, PoseStack poseStack, boolean applyLeftHandTransform) {
            BakedModel modelToUse = bakedModelDefault;
            if (context == ItemDisplayContext.THIRD_PERSON_LEFT_HAND || context == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND || context == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || context == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND) {
                modelToUse = bakedModelHand;
            }
            return ForgeHooksClient.handleCameraTransforms(poseStack, modelToUse, context, applyLeftHandTransform);
        }
    }

    public static class HeadItemModel implements BakedModel {
        public final BakedModel bakedModelDefault;
        public final BakedModel bakedModelHead;
        public final CustomItemOverrides itemOverrides;

        public HeadItemModel(BakedModel bakedModelDefault, BakedModel bakedModelHand) {
            this.bakedModelDefault = bakedModelDefault;
            this.bakedModelHead = bakedModelHand;
            this.itemOverrides = new CustomItemOverrides();
        }

        public HeadItemModel(BakedModel bakedModelDefault, BakedModel bakedModelHand, CustomItemOverrides itemOverrides) {
            this.bakedModelDefault = bakedModelDefault;
            this.bakedModelHead = bakedModelHand;
            this.itemOverrides = itemOverrides;
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction direction, RandomSource random) {
            return bakedModelDefault.getQuads(state, direction, random);
        }

        @Override
        public boolean useAmbientOcclusion() {
            return bakedModelDefault.useAmbientOcclusion();
        }

        @Override
        public boolean isGui3d() {
            return bakedModelDefault.isGui3d();
        }

        @Override
        public boolean usesBlockLight() {
            return bakedModelDefault.usesBlockLight();
        }

        @Override
        public boolean isCustomRenderer() {
            return bakedModelDefault.isCustomRenderer();
        }

        @Override
        public TextureAtlasSprite getParticleIcon() {
            return bakedModelDefault.getParticleIcon();
        }

        @Override
        public ItemOverrides getOverrides() {
            return itemOverrides;
        }

        @Override
        public BakedModel applyTransform(ItemDisplayContext context, PoseStack poseStack, boolean applyLeftHandTransform) {
            BakedModel modelToUse = bakedModelDefault;
            if (context == ItemDisplayContext.HEAD) {
                modelToUse = bakedModelHead;
            }
            return ForgeHooksClient.handleCameraTransforms(poseStack, modelToUse, context, applyLeftHandTransform);
        }
    }
}