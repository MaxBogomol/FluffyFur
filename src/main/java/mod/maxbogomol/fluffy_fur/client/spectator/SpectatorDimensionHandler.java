package mod.maxbogomol.fluffy_fur.client.spectator;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpectatorDimensionHandler {
    public static ArrayList<SpectatorDimensionType> dimensions = new ArrayList<>();
    public static ArrayList<ResourceKey<Level>> levelDimensions = new ArrayList<>();
    public static Map<ResourceKey<Level>, Boolean> levelDimensionSaves = new HashMap<>();

    public static SpectatorDimensionType getDimension(int id) {
        return dimensions.get(id);
    }

    public static void register(SpectatorDimensionType dimension) {
        dimensions.add(dimension);
    }

    public static int size() {
        return dimensions.size();
    }

    public static ArrayList<SpectatorDimensionType> getDimensions() {
        return dimensions;
    }

    public static void sortDimensions() {
        ArrayList<ResourceKey<Level>> all = new ArrayList<>(levelDimensions);
        ArrayList<ResourceKey<Level>> sorted = new ArrayList<>();

        for (SpectatorDimensionType dimensionType : getDimensions()) {
            for (ResourceKey<Level> dimension : levelDimensions) {
                if (dimension.equals(dimensionType.dimension)) {
                    sorted.add(dimension);
                    all.remove(dimension);
                    break;
                }
            }
        }
        sorted.addAll(all);

        levelDimensions = sorted;
    }
}
