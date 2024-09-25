package mod.maxbogomol.fluffy_fur.registry.client;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.model.armor.EmptyArmorModel;
import mod.maxbogomol.fluffy_fur.client.model.item.CustomItemOverrides;
import mod.maxbogomol.fluffy_fur.client.model.item.CustomModel;
import mod.maxbogomol.fluffy_fur.client.model.item.CustomRenderModel;
import mod.maxbogomol.fluffy_fur.client.model.playerskin.FoxEarsModel;
import mod.maxbogomol.fluffy_fur.client.model.playerskin.FoxTailModel;
import mod.maxbogomol.fluffy_fur.client.model.playerskin.NanachiTailModel;
import mod.maxbogomol.fluffy_fur.client.model.playerskin.RabbitEarsModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

public class FluffyFurModels {
    public static final ModelLayerLocation FOX_EARS_LAYER = addLayer(FluffyFur.MOD_ID, "fox_ears");
    public static final ModelLayerLocation FOX_TAIL_LAYER = addLayer(FluffyFur.MOD_ID, "fox_tail");
    public static final ModelLayerLocation RABBIT_EARS_LAYER = addLayer(FluffyFur.MOD_ID, "rabbit_ears");
    public static final ModelLayerLocation NANACHI_TAIL_LAYER = addLayer(FluffyFur.MOD_ID, "nanachi_tail");

    public static final ModelLayerLocation EMPTY_ARMOR_LAYER = addLayer(FluffyFur.MOD_ID, "empty_armor");

    public static FoxEarsModel FOX_EARS = null;
    public static FoxTailModel FOX_TAIL = null;
    public static RabbitEarsModel RABBIT_EARS = null;
    public static NanachiTailModel NANACHI_TAIL = null;

    public static EmptyArmorModel EMPTY_ARMOR = null;

    @Mod.EventBusSubscriber(modid = FluffyFur.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(FOX_EARS_LAYER, FoxEarsModel::createBodyLayer);
            event.registerLayerDefinition(FOX_TAIL_LAYER, FoxTailModel::createBodyLayer);
            event.registerLayerDefinition(RABBIT_EARS_LAYER, RabbitEarsModel::createBodyLayer);
            event.registerLayerDefinition(NANACHI_TAIL_LAYER, NanachiTailModel::createBodyLayer);

            event.registerLayerDefinition(EMPTY_ARMOR_LAYER, EmptyArmorModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void addLayers(EntityRenderersEvent.AddLayers event) {
            FOX_EARS = new FoxEarsModel(event.getEntityModels().bakeLayer(FOX_EARS_LAYER));
            FOX_TAIL = new FoxTailModel(event.getEntityModels().bakeLayer(FOX_TAIL_LAYER));
            RABBIT_EARS = new RabbitEarsModel(event.getEntityModels().bakeLayer(RABBIT_EARS_LAYER));
            NANACHI_TAIL = new NanachiTailModel(event.getEntityModels().bakeLayer(NANACHI_TAIL_LAYER));

            EMPTY_ARMOR = new EmptyArmorModel(event.getEntityModels().bakeLayer(EMPTY_ARMOR_LAYER));
        }
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
}
