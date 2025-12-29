package mod.maxbogomol.fluffy_fur;

import mod.maxbogomol.fluffy_fur.common.event.FluffyFurEvents;
import mod.maxbogomol.fluffy_fur.common.furry.Fox;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkinHandler;
import mod.maxbogomol.fluffy_fur.common.network.FluffyFurPacketHandler;
import mod.maxbogomol.fluffy_fur.common.proxy.ClientProxy;
import mod.maxbogomol.fluffy_fur.common.proxy.ISidedProxy;
import mod.maxbogomol.fluffy_fur.common.proxy.ServerProxy;
import mod.maxbogomol.fluffy_fur.config.FluffyFurClientConfig;
import mod.maxbogomol.fluffy_fur.config.FluffyFurConfig;
import mod.maxbogomol.fluffy_fur.config.FluffyFurServerConfig;
import mod.maxbogomol.fluffy_fur.integration.common.curios.FluffyFurCurios;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.common.*;
import mod.maxbogomol.fluffy_fur.registry.common.block.FluffyFurBlockEntities;
import mod.maxbogomol.fluffy_fur.registry.common.block.FluffyFurBlocks;
import mod.maxbogomol.fluffy_fur.registry.common.item.FluffyFurCreativeTabs;
import mod.maxbogomol.fluffy_fur.registry.common.item.FluffyFurItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Mod("fluffy_fur")
public class FluffyFur {
    public static final String MOD_ID = "fluffy_fur";
    public static final String NAME = "Fluffy Fur";
    public static final String VERSION = "0.2.5";
    public static final int VERSION_NUMBER = 16;

    public static final ISidedProxy proxy = DistExecutor.unsafeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    public static final Logger LOGGER = LogManager.getLogger();

    public static final Fox SILLY_FOX = new Fox("The silly Pink Fox that got stuck in your modded Minecraft");

    public static int mcreatorModsCount = 0;
    public static List<String> mcreatorModsList = new ArrayList<>();

    public static boolean devEnvironment = !FMLLoader.isProduction();

    public FluffyFur() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        FluffyFurItems.register(eventBus);
        FluffyFurBlocks.register(eventBus);
        FluffyFurBlockEntities.register(eventBus);
        FluffyFurParticles.register(eventBus);
        FluffyFurLootModifier.register(eventBus);
        FluffyFurSounds.register(eventBus);
        FluffyFurArgumentTypes.register(eventBus);
        FluffyFurMobEffects.register(eventBus);

        FluffyFurCurios.init(eventBus);

        FluffyFurPlayerSkins.register();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, FluffyFurClientConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, FluffyFurConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, FluffyFurServerConfig.SPEC);

        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            FluffyFurClient.ClientOnly.clientInit();
            return new Object();
        });

        eventBus.addListener(this::setup);
        eventBus.addListener(FluffyFurClient::clientSetup);

        FluffyFurCreativeTabs.register(eventBus);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new FluffyFurEvents());
    }

    private void setup(final FMLCommonSetupEvent event) {
        hi();
        FluffyFurBlocks.setFireBlock();
        FluffyFurPacketHandler.init();
        FluffyFurCreativeTabs.init();

        for (ItemSkin skin : ItemSkinHandler.getSkins()) {
            skin.setupSkinEntries();
        }

        FluffyFurCurios.setup();

        for (Package pack: Arrays.stream(Package.getPackages()).toList()) {
            String string = pack.getName();
            int dots = 0;
            for (char c : string.toCharArray()) {
                if (c == '.') dots++;
            }
            if (dots == 2) {
                if (pack.getName().startsWith("net.mcreator")) {
                    LOGGER.error("Bad package detected: " + string);
                    mcreatorModsCount++;
                    int i = string.indexOf(".");
                    string = string.substring(i + 1);
                    i = string.indexOf(".");
                    string = string.substring(i + 1);
                    mcreatorModsList.add(string);
                } else if (pack.getName().contains("procedures")) {
                    LOGGER.error("Bad package detected: " + string);
                    mcreatorModsCount++;
                    int i = string.indexOf(".");
                    string = string.substring(i + 1, string.length() - 1);
                    i = string.indexOf(".");
                    string = string.substring(0, i);
                    mcreatorModsList.add(string);
                }
            }
        }

        if (mcreatorModsCount > 0) {
            LOGGER.error("");
            LOGGER.error("ATTENTION!!!!");
            LOGGER.error("It seems you have MCreator mods installed");
            LOGGER.error("It is highly recommended to delete them");
            LOGGER.error(mcreatorModsList);
        }
        if (devEnvironment) {
            LOGGER.info("Developer environment!");
        }
    }

    public static void hi() {
        List<String> text = new ArrayList<>();
        Random random = new Random();
        text.add("I love shrimps");
        text.add("I wanna cuddle");
        text.add("wow you are cute");
        text.add("UwU");
        text.add(":3");
        text.add("MEOW");
        text.add("prrrr");
        LOGGER.info(text.get(random.nextInt(0, text.size())));

        LOGGER.info(" /\\_/\\");
        LOGGER.info("( o.o )");
        LOGGER.info(" > ^ <");
    }
}