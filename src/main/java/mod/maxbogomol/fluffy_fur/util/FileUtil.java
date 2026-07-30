package mod.maxbogomol.fluffy_fur.util;

import com.google.gson.*;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import net.minecraft.client.Minecraft;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static boolean exist(Path path) {
        return Files.exists(path);
    }

    public static void createFolder(Path path) {
        if (!exist(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                FluffyFur.LOGGER.error("Failed to create folder: {}", path);
            }
        }
    }

    public static Path getSubFolder(String name) {
        return Minecraft.getInstance().gameDirectory.toPath().resolve(name);
    }

    public static boolean existSubFolder(String name) {
        return exist(getSubFolder(name));
    }

    public static void createSubFolder(String name) {
        createFolder(getSubFolder(name));
    }

    public static JsonObject loadJson(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException | JsonSyntaxException | IllegalStateException e) {
            FluffyFur.LOGGER.error("Failed to load json from file: {}", path);
        }
        return new JsonObject();
    }

    public static void saveJson(Path path, JsonObject jsonObject) {
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            GSON.toJson(jsonObject, writer);
        } catch (IOException e) {
            FluffyFur.LOGGER.error("Failed to save json in file: {}", path);
        }
    }
}
