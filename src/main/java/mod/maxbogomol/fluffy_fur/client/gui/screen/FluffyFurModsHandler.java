package mod.maxbogomol.fluffy_fur.client.gui.screen;

import mod.maxbogomol.fluffy_fur.FluffyFurClient;
import mod.maxbogomol.fluffy_fur.client.gui.components.FluffyFurLogoRenderer;
import mod.maxbogomol.fluffy_fur.client.gui.components.FluffyFurPanoramaRenderer;
import mod.maxbogomol.fluffy_fur.config.FluffyFurClientConfig;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class FluffyFurModsHandler {
    public static FluffyFurPanoramaRenderer ACTIVE_PANORAMA = new FluffyFurPanoramaRenderer();
    public static FluffyFurLogoRenderer ACTIVE_LOGO = new FluffyFurLogoRenderer();
    public static ResourceLocation ACTIVE_OVERLAY = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");

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
        return new ArrayList<>(panoramas.values());
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

    public static void setActivePanorama(FluffyFurPanorama panorama) {
        ACTIVE_PANORAMA = panorama.getCustomRenderer();
        ACTIVE_LOGO = panorama.getCustomLogoRenderer();
        ACTIVE_OVERLAY = panorama.getOverlayTexture();
    }

    public static void setOpenPanorama(TitleScreen titleScreen, FluffyFurPanorama panorama) {
        setActivePanorama(panorama);
        copyPanoramaRenderer(titleScreen.panorama, ACTIVE_PANORAMA);

        if (FluffyFurClientConfig.VANILLA_PANORAMA_CONFLICT_PREVENTION.get() && panorama.equals(FluffyFurClient.VANILLA_PANORAMA)) {
            ResourceLocation activeTexture = ACTIVE_PANORAMA.cubeMap.images[0];
            ResourceLocation panoramaTexture = titleScreen.panorama.cubeMap.images[0];
            if (!panoramaTexture.equals(activeTexture) && (titleScreen.panorama.equals(ACTIVE_PANORAMA) || !(titleScreen.panorama instanceof FluffyFurPanoramaRenderer))) {
                String textureName = panoramaTexture.toString().substring(0, panoramaTexture.toString().length() - 6);
                ACTIVE_PANORAMA.setTexture(new ResourceLocation(textureName));
            }
            if (!TitleScreen.PANORAMA_OVERLAY.equals(ACTIVE_OVERLAY)) {
                ACTIVE_OVERLAY = TitleScreen.PANORAMA_OVERLAY;
            }
        } else {
            if (panorama.getTexture() != null) {
                ACTIVE_PANORAMA.setTexture(panorama.getTexture());
            }
            if (!TitleScreen.PANORAMA_OVERLAY.equals(ACTIVE_OVERLAY)) {
                TitleScreen.PANORAMA_OVERLAY = ACTIVE_OVERLAY;
            }
            ACTIVE_LOGO.setLogo(panorama.getLogo());
        }

        titleScreen.panorama = ACTIVE_PANORAMA;
        if (FluffyFurClientConfig.PANORAMA_LOGO.get()) {
            titleScreen.logoRenderer = ACTIVE_LOGO;
        }
    }

    public static void copyPanoramaRenderer(PanoramaRenderer from, PanoramaRenderer to) {
        to.spin = from.spin;
        to.bob = from.bob;
        if (to instanceof FluffyFurPanoramaRenderer furTo) {
            if (from instanceof FluffyFurPanoramaRenderer furFrom) {
                furTo.oldSpin = furFrom.oldSpin;
            } else {
                furTo.oldSpin = from.spin;
            }
        }
    }
}
