package mod.maxbogomol.fluffy_fur;

import mod.maxbogomol.fluffy_fur.client.event.FluffyFurClientEvents;
import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurMod;
import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurModsHandler;
import mod.maxbogomol.fluffy_fur.client.gui.screen.FluffyFurPanorama;
import mod.maxbogomol.fluffy_fur.client.language.LanguageHandler;
import mod.maxbogomol.fluffy_fur.client.render.LevelRenderHandler;
import mod.maxbogomol.fluffy_fur.client.shader.postprocess.PostProcessHandler;
import mod.maxbogomol.fluffy_fur.client.splash.SplashHandler;
import mod.maxbogomol.fluffy_fur.integration.client.ShadersIntegration;
import mod.maxbogomol.fluffy_fur.integration.client.fusion.FluffyFurFusion;
import mod.maxbogomol.fluffy_fur.registry.common.item.FluffyFurCreativeTabs;
import net.minecraft.client.resources.language.LanguageInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.awt.*;
import java.util.List;

public class FluffyFurClient {

    public static boolean optifinePresent = false;
    public static boolean piracyPresent = false;

    public static class ClientOnly {
        public static void clientInit() {
            IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
            IEventBus forgeBus = MinecraftForge.EVENT_BUS;
            forgeBus.register(new FluffyFurClientEvents());

            forgeBus.addListener(EventPriority.LOWEST, LevelRenderHandler::onLevelRender);
            forgeBus.addListener(EventPriority.LOWEST, PostProcessHandler::onLevelRender);

            forgeBus.addListener(EventPriority.HIGHEST, LevelRenderHandler::onRenderFog);

            eventBus.addListener(FluffyFurCreativeTabs::addCreativeTabContent);

            FluffyFurFusion.init(eventBus);
            FluffyFurFusion.setup();
        }
    }

    public static void clientSetup(final FMLClientSetupEvent event) {
        try {
            Class.forName("net.optifine.Config");
            optifinePresent = true;
        } catch (ClassNotFoundException e) {
            optifinePresent = false;
        }
        piracyPresent = ModList.get().isLoaded("tlskincape");

        if (optifinePresent) FluffyFur.LOGGER.error("OptiFine detected!!!");
        if (piracyPresent) FluffyFur.LOGGER.error("Piracy detected!!!");

        setupMenu();
        setupSplashes();
        setupLanguages();

        ShadersIntegration.init();
    }

    public static FluffyFurMod MOD_INSTANCE;
    public static FluffyFurPanorama VANILLA_PANORAMA;
    public static FluffyFurPanorama FLUFFY_PANORAMA;

    public static void setupMenu() {
        MOD_INSTANCE = new FluffyFurMod(FluffyFur.MOD_ID, FluffyFur.NAME, FluffyFur.VERSION).setDev("MaxBogomol").setItem(new ItemStack(Items.PINK_PETALS))
                .setEdition(FluffyFur.VERSION_NUMBER).setNameColor(new Color(254, 200, 207)).setVersionColor(new Color(92, 72, 90))
                .setDescription(Component.translatable("mod_description.fluffy_fur"))
                .addFluffyVillageLink("https://fluffy-village.dev/pages/eng/creations/fluffy_fur.html")
                .addGithubLink("https://github.com/MaxBogomol/FluffyFur")
                .addCurseForgeLink("https://www.curseforge.com/minecraft/mc-mods/fluffy-fur")
                .addModrinthLink("https://modrinth.com/mod/fluffy-fur")
                .addDiscordLink("https://discord.gg/cKf55qNugw");
        VANILLA_PANORAMA = new FluffyFurPanorama("minecraft:vanilla", Component.translatable("panorama.minecraft.vanilla")).setItem(new ItemStack(Items.GRASS_BLOCK));
        FLUFFY_PANORAMA = new FluffyFurPanorama(FluffyFur.MOD_ID + ":fluffy_zone", Component.translatable("panorama.fluffy_fur.fluffy_zone"))
                .setMod(MOD_INSTANCE).setItem(new ItemStack(Items.CHERRY_LEAVES))
                .setTexture(new ResourceLocation(FluffyFur.MOD_ID, "textures/gui/menu/panorama/fluffy_zone/panorama"))
                .setLogo(new ResourceLocation(FluffyFur.MOD_ID, "textures/gui/menu/title/fluffy_fur.png"));

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

    public static void setupSplashes() {
        List<String> strings = LanguageHandler.getStringsFromFile(new ResourceLocation(FluffyFur.MOD_ID, "texts/splashes.txt"));
        for (String string : strings) {
            SplashHandler.addSplash(string);
        }
    }

    public static void setupLanguages() {
        List<String> strings = LanguageHandler.getStringsFromFile(new ResourceLocation(FluffyFur.MOD_ID, "texts/languages.txt"));
        LanguageHandler.addLanguage("be_tar", new LanguageInfo(strings.get(1), strings.get(0), false));
        LanguageHandler.addLanguage("be_tl", new LanguageInfo(strings.get(3), strings.get(2), false));
        LanguageHandler.addLanguage("lol_ud", new LanguageInfo(strings.get(5), strings.get(4), false));
        LanguageHandler.addLanguage("meow", new LanguageInfo(strings.get(7), strings.get(6), false));
    }
}
