package mod.maxbogomol.fluffy_fur;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import mod.maxbogomol.fluffy_fur.client.event.ClientEvents;
import mod.maxbogomol.fluffy_fur.client.model.armor.EmptyArmorModel;
import mod.maxbogomol.fluffy_fur.client.model.playerskin.FoxEarsModel;
import mod.maxbogomol.fluffy_fur.client.model.playerskin.FoxTailModel;
import mod.maxbogomol.fluffy_fur.client.model.playerskin.NanachiTailModel;
import mod.maxbogomol.fluffy_fur.client.model.playerskin.RabbitEarsModel;
import mod.maxbogomol.fluffy_fur.client.particle.CubeParticleType;
import mod.maxbogomol.fluffy_fur.client.particle.GenericParticleType;
import mod.maxbogomol.fluffy_fur.client.playerskin.FoxPlayerSkin;
import mod.maxbogomol.fluffy_fur.client.playerskin.NanachiPlayerSkin;
import mod.maxbogomol.fluffy_fur.client.playerskin.PlayerSkin;
import mod.maxbogomol.fluffy_fur.client.playerskin.PlayerSkinHandler;
import mod.maxbogomol.fluffy_fur.client.render.LevelRenderHandler;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.util.Map;

public class FluffyFurClient {

    private static final String CATEGORY_KEY = "key.category."+FluffyFur.MOD_ID+".general";
    public static final KeyMapping SKIN_MENU_KEY = new KeyMapping("key."+FluffyFur.MOD_ID+".skin_menu", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, CATEGORY_KEY);

    public static final ModelLayerLocation FOX_EARS_LAYER = new ModelLayerLocation(new ResourceLocation(FluffyFur.MOD_ID, "fox_ears"), "main");
    public static final ModelLayerLocation FOX_TAIL_LAYER = new ModelLayerLocation(new ResourceLocation(FluffyFur.MOD_ID, "fox_tail"), "main");
    public static final ModelLayerLocation RABBIT_EARS_LAYER = new ModelLayerLocation(new ResourceLocation(FluffyFur.MOD_ID, "rabbit_ears"), "main");
    public static final ModelLayerLocation NANACHI_TAIL_LAYER = new ModelLayerLocation(new ResourceLocation(FluffyFur.MOD_ID, "nanachi_tail"), "main");

    public static final ModelLayerLocation EMPTY_ARMOR_LAYER = new ModelLayerLocation(new ResourceLocation(FluffyFur.MOD_ID, "empty_armor"), "main");

    public static FoxEarsModel FOX_EARS_MODEL = null;
    public static FoxTailModel FOX_TAIL_MODEL = null;
    public static RabbitEarsModel RABBIT_EARS_MODEL = null;
    public static NanachiTailModel NANACHI_TAIL_MODEL = null;

    public static EmptyArmorModel EMPTY_ARMOR_MODEL = null;

    public static ShaderInstance GLOWING_SHADER, GLOWING_SPRITE_SHADER, GLOWING_PARTICLE_SHADER, SPRITE_PARTICLE_SHADER, FLUID_SHADER;

    public static ShaderInstance getGlowingShader() { return GLOWING_SHADER; }
    public static ShaderInstance getGlowingSpriteShader() { return GLOWING_SPRITE_SHADER; }
    public static ShaderInstance getGlowingParticleShader() { return GLOWING_PARTICLE_SHADER; }
    public static ShaderInstance getSpriteParticleShader() { return SPRITE_PARTICLE_SHADER; }
    public static ShaderInstance getFluidShader() { return FLUID_SHADER; }

    public static boolean optifinePresent = false;
    public static boolean firstScreen = true;

    public static class ClientOnly {
        public static void clientInit() {
            IEventBus forgeBus = MinecraftForge.EVENT_BUS;
            forgeBus.addListener(LevelRenderHandler::onLevelRender);
            forgeBus.register(new ClientEvents());
        }
    }

    public static void clientSetup(final FMLClientSetupEvent event) {
        try {
            Class.forName("net.optifine.Config");
            FluffyFurClient.optifinePresent = true;
        } catch (ClassNotFoundException e) {
            FluffyFurClient.optifinePresent = false;
        }

        event.enqueueWork(() -> {
            setupPlayerSkins();
        });
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onRenderTypeSetup(FMLClientSetupEvent event) {
            BlockEntityRenderers.register(FluffyFur.SIGN_BLOCK_ENTITY.get(), SignRenderer::new);
            BlockEntityRenderers.register(FluffyFur.HANGING_SIGN_BLOCK_ENTITY.get(), HangingSignRenderer::new);
        }

        @SubscribeEvent
        public static void onModelRegistryEvent(ModelEvent.RegisterAdditional event) {

        }

        @SubscribeEvent
        public static void onModelBakeEvent(ModelEvent.ModifyBakingResult event) {
            Map<ResourceLocation, BakedModel> map = event.getModels();
        }

        @SubscribeEvent
        public static void afterModelBake(ModelEvent.BakingCompleted event) {

        }

        @SubscribeEvent
        public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
            event.register(FluffyFurClient.SKIN_MENU_KEY);
        }

        @SubscribeEvent
        public static void registerFactories(RegisterParticleProvidersEvent event) {
            ParticleEngine particleEngine = Minecraft.getInstance().particleEngine;
            particleEngine.register(FluffyFur.WISP_PARTICLE.get(), GenericParticleType.Factory::new);
            particleEngine.register(FluffyFur.TINY_WISP_PARTICLE.get(), GenericParticleType.Factory::new);
            particleEngine.register(FluffyFur.SPARKLE_PARTICLE.get(), GenericParticleType.Factory::new);
            particleEngine.register(FluffyFur.STAR_PARTICLE.get(), GenericParticleType.Factory::new);
            particleEngine.register(FluffyFur.TINY_STAR_PARTICLE.get(), GenericParticleType.Factory::new);
            particleEngine.register(FluffyFur.SQUARE_PARTICLE.get(), GenericParticleType.Factory::new);
            particleEngine.register(FluffyFur.DOT_PARTICLE.get(), GenericParticleType.Factory::new);
            particleEngine.register(FluffyFur.CIRCLE_PARTICLE.get(), GenericParticleType.Factory::new);
            particleEngine.register(FluffyFur.TINY_CIRCLE_PARTICLE.get(), GenericParticleType.Factory::new);
            particleEngine.register(FluffyFur.HEART_PARTICLE.get(), GenericParticleType.Factory::new);
            particleEngine.register(FluffyFur.SMOKE_PARTICLE.get(), GenericParticleType.Factory::new);
            particleEngine.register(FluffyFur.CUBE_PARTICLE.get(), CubeParticleType.Factory::new);
            particleEngine.register(FluffyFur.TRAIL_PARTICLE.get(), GenericParticleType.Factory::new);
        }

        @SubscribeEvent
        public static void shaderRegistry(RegisterShadersEvent event) throws IOException {
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("fluffy_fur:glowing"), DefaultVertexFormat.POSITION_COLOR), shader -> { GLOWING_SHADER = shader; });
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("fluffy_fur:glowing_sprite"), DefaultVertexFormat.POSITION_TEX_COLOR), shader -> { GLOWING_SPRITE_SHADER = shader; });
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("fluffy_fur:glowing_particle"), DefaultVertexFormat.PARTICLE), shader -> { GLOWING_PARTICLE_SHADER = shader; });
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("fluffy_fur:sprite_particle"), DefaultVertexFormat.PARTICLE), shader -> { SPRITE_PARTICLE_SHADER = shader; });
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("fluffy_fur:fluid"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP), shader -> { FLUID_SHADER = shader; });
        }

        @SubscribeEvent
        public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(FOX_EARS_LAYER, FoxEarsModel::createBodyLayer);
            event.registerLayerDefinition(FOX_TAIL_LAYER, FoxTailModel::createBodyLayer);
            event.registerLayerDefinition(RABBIT_EARS_LAYER, RabbitEarsModel::createBodyLayer);
            event.registerLayerDefinition(NANACHI_TAIL_LAYER, NanachiTailModel::createBodyLayer);

            event.registerLayerDefinition(EMPTY_ARMOR_LAYER, EmptyArmorModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void onRegisterLayers(EntityRenderersEvent.AddLayers event) {
            FOX_EARS_MODEL = new FoxEarsModel(event.getEntityModels().bakeLayer(FOX_EARS_LAYER));
            FOX_TAIL_MODEL = new FoxTailModel(event.getEntityModels().bakeLayer(FOX_TAIL_LAYER));
            RABBIT_EARS_MODEL = new RabbitEarsModel(event.getEntityModels().bakeLayer(RABBIT_EARS_LAYER));
            NANACHI_TAIL_MODEL = new NanachiTailModel(event.getEntityModels().bakeLayer(NANACHI_TAIL_LAYER));

            EMPTY_ARMOR_MODEL = new EmptyArmorModel(event.getEntityModels().bakeLayer(EMPTY_ARMOR_LAYER));
        }
    }

    public static PlayerSkin MAXBOGOMOL_SKIN = new FoxPlayerSkin(FluffyFur.MOD_ID + ":maxbogomol")
            .setSkinTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "maxbogomol/skin"))
            .setSkinBlinkTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "maxbogomol/skin_blink"))
            .setEarsTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "maxbogomol/ears"))
            .setTailTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "maxbogomol/tail"))
            .setSlim(true);
    public static PlayerSkin ONIXTHECAT_SKIN = new PlayerSkin(FluffyFur.MOD_ID + ":onixthecat")
            .setSkinTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "onixthecat/skin"))
            .setSkinBlinkTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "onixthecat/skin_blink"))
            .setSlim(true);
    public static PlayerSkin NANACHI_SKIN = new NanachiPlayerSkin(FluffyFur.MOD_ID + ":nanachi")
            .setSkinTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "nanachi/skin"))
            .setSkinBlinkTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "nanachi/skin_blink"))
            .setEarsTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "nanachi/ears"))
            .setTailTexture(PlayerSkin.getSkinLocation(FluffyFur.MOD_ID, "nanachi/tail"))
            .setSlim(true);

    public static void setupPlayerSkins() {
        PlayerSkinHandler.register(MAXBOGOMOL_SKIN);
        PlayerSkinHandler.register(ONIXTHECAT_SKIN);
        PlayerSkinHandler.register(NANACHI_SKIN);
    }

    public static void makeBow(Item item) {
        ItemProperties.register(item, new ResourceLocation("pull"), (stack, level, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
            }
        });

        ItemProperties.register(item, new ResourceLocation("pulling"), (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
    }
}
