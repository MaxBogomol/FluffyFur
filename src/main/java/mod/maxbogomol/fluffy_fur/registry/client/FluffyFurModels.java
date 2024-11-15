package mod.maxbogomol.fluffy_fur.registry.client;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.model.armor.EmptyArmorModel;
import mod.maxbogomol.fluffy_fur.client.model.book.CustomBookModel;
import mod.maxbogomol.fluffy_fur.client.model.item.BowItemOverrides;
import mod.maxbogomol.fluffy_fur.client.model.item.CustomItemOverrides;
import mod.maxbogomol.fluffy_fur.client.model.item.CustomModel;
import mod.maxbogomol.fluffy_fur.client.model.item.CustomRenderModel;
import mod.maxbogomol.fluffy_fur.client.model.playerskin.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Map;

public class FluffyFurModels {
    public static final ModelLayerLocation CAT_EARS_LAYER = addLayer("cat_ears");
    public static final ModelLayerLocation CAT_TAIL_LAYER = addLayer("cat_tail");
    public static final ModelLayerLocation FOX_EARS_LAYER = addLayer("fox_ears");
    public static final ModelLayerLocation FOX_TAIL_LAYER = addLayer("fox_tail");
    public static final ModelLayerLocation RABBIT_EARS_LAYER = addLayer("rabbit_ears");
    public static final ModelLayerLocation NANACHI_TAIL_LAYER = addLayer("nanachi_tail");

    public static final ModelLayerLocation EMPTY_ARMOR_LAYER = addLayer("empty_armor");

    public static final ModelLayerLocation BOOK_LAYER = addLayer("book");

    public static CatEarsModel CAT_EARS = null;
    public static CatTailModel CAT_TAIL = null;
    public static FoxEarsModel FOX_EARS = null;
    public static FoxTailModel FOX_TAIL = null;
    public static RabbitEarsModel RABBIT_EARS = null;
    public static NanachiTailModel NANACHI_TAIL = null;

    public static EmptyArmorModel EMPTY_ARMOR = null;

    public static CustomBookModel BOOK = null;

    @Mod.EventBusSubscriber(modid = FluffyFur.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(CAT_EARS_LAYER, CatEarsModel::createBodyLayer);
            event.registerLayerDefinition(CAT_TAIL_LAYER, CatTailModel::createBodyLayer);
            event.registerLayerDefinition(FOX_EARS_LAYER, FoxEarsModel::createBodyLayer);
            event.registerLayerDefinition(FOX_TAIL_LAYER, FoxTailModel::createBodyLayer);
            event.registerLayerDefinition(RABBIT_EARS_LAYER, RabbitEarsModel::createBodyLayer);
            event.registerLayerDefinition(NANACHI_TAIL_LAYER, NanachiTailModel::createBodyLayer);

            event.registerLayerDefinition(EMPTY_ARMOR_LAYER, EmptyArmorModel::createBodyLayer);

            event.registerLayerDefinition(BOOK_LAYER, CustomBookModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void addLayers(EntityRenderersEvent.AddLayers event) {
            CAT_EARS = new CatEarsModel(event.getEntityModels().bakeLayer(CAT_EARS_LAYER));
            CAT_TAIL = new CatTailModel(event.getEntityModels().bakeLayer(CAT_TAIL_LAYER));
            FOX_EARS = new FoxEarsModel(event.getEntityModels().bakeLayer(FOX_EARS_LAYER));
            FOX_TAIL = new FoxTailModel(event.getEntityModels().bakeLayer(FOX_TAIL_LAYER));
            RABBIT_EARS = new RabbitEarsModel(event.getEntityModels().bakeLayer(RABBIT_EARS_LAYER));
            NANACHI_TAIL = new NanachiTailModel(event.getEntityModels().bakeLayer(NANACHI_TAIL_LAYER));

            EMPTY_ARMOR = new EmptyArmorModel(event.getEntityModels().bakeLayer(EMPTY_ARMOR_LAYER));

            BOOK = new CustomBookModel(event.getEntityModels().bakeLayer(BOOK_LAYER));
        }
    }

    public static ModelLayerLocation addLayer(String layer) {
        return addLayer(FluffyFur.MOD_ID, layer);
    }

    public static ModelLayerLocation addLayer(String modId, String layer) {
        return new ModelLayerLocation(new ResourceLocation(modId, layer), "main");
    }

    public static ModelResourceLocation addCustomModel(String modId, String model) {
        return new ModelResourceLocation(modId, model, "");
    }

    public static void addCustomRenderItemModel(Map<ResourceLocation, BakedModel> map, ResourceLocation item) {
        BakedModel model = map.get(new ModelResourceLocation(item, "inventory"));
        CustomModel customModel = new CustomRenderModel(model, new CustomItemOverrides());
        map.replace(new ModelResourceLocation(item, "inventory"), customModel);
    }

    public static void addBowItemModel(Map<ResourceLocation, BakedModel> map, ResourceLocation item, BowItemOverrides itemOverrides) {
        BakedModel model = map.get(new ModelResourceLocation(item, "inventory"));
        CustomModel customModel = new CustomModel(model, itemOverrides);

        for (int i = 0; i < 3; i++) {
            BakedModel pullModel = map.get(new ModelResourceLocation(new ResourceLocation(item.toString() + "_pulling_" + String.valueOf(i)), "inventory"));
            itemOverrides.models.add(pullModel);
        }

        map.replace(new ModelResourceLocation(item, "inventory"), customModel);
    }

    public static void addBowItemModel(Map<ResourceLocation, BakedModel> map, ResourceLocation item) {
        addBowItemModel(map, item, new BowItemOverrides());
    }

    public static ArrayList<ModelResourceLocation> getBowModels(String modId, String item) {
        ArrayList<ModelResourceLocation> models = new ArrayList<>();
        models.add(new ModelResourceLocation(new ResourceLocation(modId, item), "inventory"));
        models.add(new ModelResourceLocation(new ResourceLocation(modId, item + "_pulling_0"), "inventory"));
        models.add(new ModelResourceLocation(new ResourceLocation(modId, item + "_pulling_1"), "inventory"));
        models.add(new ModelResourceLocation(new ResourceLocation(modId, item + "_pulling_2"), "inventory"));
        return models;
    }

    public static void addBowItemModel(ModelEvent.RegisterAdditional event, String modId, String item) {
        for (ModelResourceLocation model : getBowModels(modId, item)) {
            event.register(model);
        }
    }
}
