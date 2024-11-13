package mod.maxbogomol.fluffy_fur.client.event;

import java.util.ArrayList;
import java.util.List;

public class SplashHandler {
    public static List<String> splashes = new ArrayList<>();

    public static void addSplash(String splash) {
        splashes.add(splash);
    }

    public static List<String> getSplashes() {
        return splashes;
    }
}
