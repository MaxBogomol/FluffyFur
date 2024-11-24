package mod.maxbogomol.fluffy_fur.common.fire;

import java.util.ArrayList;
import java.util.List;

public class FireItemHandler {
    public static List<FireItemModifier> modifiers = new ArrayList<>();

    public static void register(FireItemModifier modifier) {
        modifiers.add(modifier);
    }

    public static List<FireItemModifier> getModifiers() {
        return modifiers;
    }
}
