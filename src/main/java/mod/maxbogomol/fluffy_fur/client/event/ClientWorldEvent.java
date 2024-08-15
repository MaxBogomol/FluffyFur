package mod.maxbogomol.fluffy_fur.client.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;

import java.util.Random;

public class ClientWorldEvent {

    private static Random random = new Random();

    @OnlyIn(Dist.CLIENT)
    public static void onTick(TickEvent.LevelTickEvent event) {

    }

    @OnlyIn(Dist.CLIENT)
    public static void onRender(RenderLevelStageEvent event) {

    }
}
