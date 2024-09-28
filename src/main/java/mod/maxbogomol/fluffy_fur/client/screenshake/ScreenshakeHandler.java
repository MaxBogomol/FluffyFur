package mod.maxbogomol.fluffy_fur.client.screenshake;

import mod.maxbogomol.fluffy_fur.config.FluffyFurClientConfig;
import net.minecraft.client.Camera;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

import java.util.ArrayList;

public class ScreenshakeHandler {

    public static final ArrayList<ScreenshakeInstance> INSTANCES = new ArrayList<>();
    public static float intensity;
    public static float yawOffset;
    public static float pitchOffset;

    public static void cameraTick(Camera camera, RandomSource random) {
        if (intensity >= 0) {
            yawOffset = randomizeOffset(random);
            pitchOffset = randomizeOffset(random);
            camera.setRotation(camera.getYRot() + yawOffset, camera.getXRot() + pitchOffset);
        }
    }

    public static void clientTick(Camera camera, RandomSource random) {
        double sum = Math.min(INSTANCES.stream().mapToDouble(i1 -> i1.updateIntensity(camera, random)).sum(), FluffyFurClientConfig.SCREENSHAKE_INTENSITY.get());

        intensity = (float) Math.pow(sum, 3);
        INSTANCES.removeIf(i -> i.progress >= i.duration);
    }

    public static void addScreenshake(ScreenshakeInstance instance) {
        INSTANCES.add(instance);
    }

    public static float randomizeOffset(RandomSource random) {
        return Mth.nextFloat(random, -intensity * 2, intensity * 2);
    }
}
