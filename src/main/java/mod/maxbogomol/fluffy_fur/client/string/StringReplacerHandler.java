package mod.maxbogomol.fluffy_fur.client.string;

import java.util.ArrayList;
import java.util.List;

public class StringReplacerHandler {
    public static List<StringReplacerInstance> INSTANCES = new ArrayList<>();

    public static void register(StringReplacerInstance instance) {
        INSTANCES.add(instance);
    }

    public static List<StringReplacerInstance> getInstances() {
        return INSTANCES;
    }

    public static String replaceString(String string) {
        for (StringReplacerInstance instance : getInstances()) {
            if (instance.canReplaceString(string)) {
                string = instance.replaceString(string);
            }
        }
        return string;
    }
}
