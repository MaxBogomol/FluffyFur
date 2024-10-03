package mod.maxbogomol.fluffy_fur.registry.common.item;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.model.item.CustomModel;
import mod.maxbogomol.fluffy_fur.client.model.item.ItemSkinItemOverrides;
import mod.maxbogomol.fluffy_fur.client.model.item.ItemSkinsModels;
import mod.maxbogomol.fluffy_fur.client.render.item.LargeItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

public class FluffyFurItemSkins {

    @Mod.EventBusSubscriber(modid = FluffyFur.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void modelRegistrySkins(ModelEvent.RegisterAdditional event) {
            for (String skin : ItemSkinsModels.getSkins()) {
                event.register(ItemSkinsModels.getModelLocationSkin(skin));
            }
        }

        @SubscribeEvent
        public static void modelBakeSkins(ModelEvent.ModifyBakingResult event) {
            Map<ResourceLocation, BakedModel> map = event.getModels();

            for (String skin : ItemSkinsModels.getSkins()) {
                BakedModel model = map.get(ItemSkinsModels.getModelLocationSkin(skin));
                ItemSkinsModels.addModelSkins(skin, model);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void addSkinModel(Map<ResourceLocation, BakedModel> map, ResourceLocation id) {
        BakedModel model = map.get(new ModelResourceLocation(id, "inventory"));
        CustomModel newModel = new CustomModel(model, new ItemSkinItemOverrides());
        map.replace(new ModelResourceLocation(id, "inventory"), newModel);
    }

    @OnlyIn(Dist.CLIENT)
    public static void addLargeModel(Map<ResourceLocation, BakedModel> map, String modId, String skin) {
        LargeItemRenderer.bakeModel(map, modId, "skin/"+skin);
        ItemSkinsModels.addModelSkins(modId+":"+skin, map.get(ItemSkinsModels.getModelLocationSkin(modId+":"+skin)));
    }
}
