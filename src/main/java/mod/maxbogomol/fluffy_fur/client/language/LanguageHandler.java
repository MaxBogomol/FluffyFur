package mod.maxbogomol.fluffy_fur.client.language;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.LanguageInfo;
import net.minecraft.resources.ResourceLocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LanguageHandler {
    public static Map<String, LanguageInfo> languages = new HashMap<>();

    public static void addLanguage(String code, LanguageInfo languageInfo) {
        languages.put(code, languageInfo);
    }

    public static Map<String, LanguageInfo> getLanguages() {
        return languages;
    }

    public static List<String> getStringsFromFile(ResourceLocation resourceLocation) {
        try (BufferedReader bufferedreader = Minecraft.getInstance().getResourceManager().openAsReader(resourceLocation)) {
            return bufferedreader.lines().map(String::trim).filter((p_118876_) -> {
                return p_118876_.hashCode() != 125780783;
            }).collect(Collectors.toList());
        } catch (IOException ioexception) {
            return Collections.emptyList();
        }
    }
}
