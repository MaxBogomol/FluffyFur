package mod.maxbogomol.fluffy_fur.client.gui.screen;

import mod.maxbogomol.fluffy_fur.FluffyFurClient;
import mod.maxbogomol.fluffy_fur.config.FluffyFurClientConfig;
import mod.maxbogomol.fluffy_fur.client.gui.components.CustomLogoRenderer;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class FluffyFurModsHandler {
    public static Map<String, FluffyFurMod> mods = new HashMap<>();
    public static Map<String, FluffyFurPanorama> panoramas = new HashMap<>();

    public static void registerMod(FluffyFurMod mod) {
        mods.put(mod.getId(), mod);
    }

    public static FluffyFurMod getMod(String id) {
        return mods.get(id);
    }

    public static List<FluffyFurMod> getMods() {
        return mods.values().stream().toList();
    }

    public static void registerPanorama(FluffyFurPanorama panorama) {
        panoramas.put(panorama.getId(), panorama);
    }

    public static FluffyFurPanorama getPanorama(String id) {
        return panoramas.get(id);
    }

    public static List<FluffyFurPanorama> getPanoramas() {
        return panoramas.values().stream().toList();
    }

    public static List<FluffyFurMod> getSortedMods() {
        List<FluffyFurMod> sorted = new ArrayList<>();

        List<String> main = new ArrayList<>();
        List<String> sort = new ArrayList<>();

        for (String id : mods.keySet()) {
            if (!id.equals(FluffyFurClient.MOD_INSTANCE.getId())) {
                if (getMod(id).getDev().equals("MaxBogomol")) {
                    main.add(id);
                } else {
                    sort.add(id);
                }
            }
        }

        Collections.sort(main);
        Collections.sort(sort);

        sorted.add(FluffyFurClient.MOD_INSTANCE);
        for (String id : main) {
            sorted.add(getMod(id));
        }
        for (String id : sort) {
            sorted.add(getMod(id));
        }

        return sorted;
    }

    public static List<FluffyFurPanorama> getSortedPanoramas() {
        List<FluffyFurPanorama> sorted = new ArrayList<>();
        List<FluffyFurPanorama> panoramas = getPanoramas();
        List<FluffyFurMod> sortedMods = getSortedMods();
        List<FluffyFurPanorama> added = new ArrayList<>();

        for (FluffyFurMod mod : sortedMods) {
            List<FluffyFurPanorama> modPanoramas = new ArrayList<>();
            List<Integer> sorts = new ArrayList<>();
            for (FluffyFurPanorama panorama : panoramas) {
                if (panorama.getMod() == mod) {
                    sorts.add(panorama.getSort());
                    modPanoramas.add(panorama);
                    added.add(panorama);
                }
            }
            Collections.sort(sorts);
            for (int sort : sorts) {
                for (FluffyFurPanorama panorama : modPanoramas) {
                    if (panorama.getSort() == sort) {
                        sorted.add(panorama);
                    }
                }
            }
        }
        sorted.add(0, FluffyFurClient.VANILLA_PANORAMA);
        for (FluffyFurPanorama panorama : panoramas) {
            if (!added.contains(panorama)) {
                if (panorama != FluffyFurClient.VANILLA_PANORAMA) {
                    sorted.add(panorama);
                }
            }
        }

        return sorted;
    }

    public static FluffyFurPanorama getPanorama() {
        return getPanorama(FluffyFurClientConfig.PANORAMA.get());
    }

    public static void setPanorama(FluffyFurPanorama panorama) {
        FluffyFurClientConfig.PANORAMA.set(panorama.getId());
    }

    public static void setOpenPanorama(TitleScreen titleScreen, FluffyFurPanorama panorama) {
        float spin = titleScreen.panorama.spin;
        float bob = titleScreen.panorama.bob;
        ResourceLocation base = new ResourceLocation("textures/gui/title/background/panorama");
        if (panorama.getTexture() != null) {
            base = panorama.getTexture();
        }
        TitleScreen.CUBE_MAP = new CubeMap(base);
        titleScreen.panorama = new PanoramaRenderer(TitleScreen.CUBE_MAP);
        if (panorama.getLogo() != null) {
            titleScreen.logoRenderer = new CustomLogoRenderer(panorama.getLogo(), titleScreen.logoRenderer.keepLogoThroughFade);
        } else {
            if (titleScreen.logoRenderer instanceof CustomLogoRenderer) {
                titleScreen.logoRenderer = new LogoRenderer(titleScreen.logoRenderer.keepLogoThroughFade);
            }
        }
        titleScreen.panorama.spin = spin;
        titleScreen.panorama.bob = bob;
    }
}
