package mod.maxbogomol.fluffy_fur.client.sound;

import java.util.ArrayList;
import java.util.List;

public class MusicHandler {
    public static List<MusicModifier> modifiers = new ArrayList<>();

    public static void register(MusicModifier modifier) {
        modifiers.add(modifier);
    }

    public static List<MusicModifier> getModifiers() {
        return modifiers;
    }
}
