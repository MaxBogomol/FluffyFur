package mod.maxbogomol.fluffy_fur;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import mod.maxbogomol.fluffy_fur.client.event.ClientEvents;
import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurMod;
import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurModsHandler;
import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurPanorama;
import mod.maxbogomol.fluffy_fur.client.model.armor.EmptyArmorModel;
import mod.maxbogomol.fluffy_fur.client.model.playerskin.FoxEarsModel;
import mod.maxbogomol.fluffy_fur.client.model.playerskin.FoxTailModel;
import mod.maxbogomol.fluffy_fur.client.model.playerskin.NanachiTailModel;
import mod.maxbogomol.fluffy_fur.client.model.playerskin.RabbitEarsModel;
import mod.maxbogomol.fluffy_fur.client.playerskin.FoxPlayerSkin;
import mod.maxbogomol.fluffy_fur.client.playerskin.NanachiPlayerSkin;
import mod.maxbogomol.fluffy_fur.client.playerskin.PlayerSkin;
import mod.maxbogomol.fluffy_fur.client.playerskin.PlayerSkinHandler;
import mod.maxbogomol.fluffy_fur.client.render.LevelRenderHandler;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.io.IOException;

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
            setupMenu();
        });
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
            event.register(FluffyFurClient.SKIN_MENU_KEY);
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

    public static FluffyFurMod MOD_INSTANCE;
    public static FluffyFurPanorama VANILLA_PANORAMA;
    public static FluffyFurPanorama FLUFFY_PANORAMA;

    public static void setupMenu() {
        MOD_INSTANCE = new FluffyFurMod(FluffyFur.MOD_ID, FluffyFur.NAME, FluffyFur.VERSION).setDev("MaxBogomol").setItem(new ItemStack(Items.PINK_PETALS))
                .setEdition(FluffyFur.VERSION_NUMBER).setNameColor(new Color(254, 200, 207)).setVersionColor(new Color(92, 72, 90))
                .setDescription(Component.translatable("mod_description.fluffy_fur"))
                .addGithubLink("https://github.com/MaxBogomol/FluffyFur")
                .addCurseForgeLink("https://www.curseforge.com/minecraft/mc-mods/fluffy-fur")
                .addModrinthLink("https://modrinth.com/mod/fluffy-fur");
        VANILLA_PANORAMA = new FluffyFurPanorama("minecraft:vanilla", net.minecraft.network.chat.Component.translatable("panorama.minecraft.vanilla")).setItem(new ItemStack(Items.GRASS_BLOCK));
        FLUFFY_PANORAMA = new FluffyFurPanorama(FluffyFur.MOD_ID + ":fluffy_zone", Component.translatable("panorama.fluffy_fur.fluffy_zone"))
                .setMod(MOD_INSTANCE).setItem(new ItemStack(Items.CHERRY_LEAVES))
                .setTexture(new ResourceLocation(FluffyFur.MOD_ID, "textures/gui/title/background/panorama"))
                .setLogo(new ResourceLocation(FluffyFur.MOD_ID, "textures/gui/title/fluffy_fur.png"));

        registerMod(MOD_INSTANCE);
        registerPanorama(VANILLA_PANORAMA);
        registerPanorama(FLUFFY_PANORAMA);
    }

    public static void registerMod(FluffyFurMod mod) {
        FluffyFurModsHandler.registerMod(mod);
    }

    public static void registerPanorama(FluffyFurPanorama panorama) {
        FluffyFurModsHandler.registerPanorama(panorama);
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
