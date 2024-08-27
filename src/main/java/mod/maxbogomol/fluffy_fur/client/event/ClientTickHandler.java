package mod.maxbogomol.fluffy_fur.client.event;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;

public class ClientTickHandler {

    public static int ticksInGame = 0;
    public static float partialTicks = 0;

    public static float getTotal() {
        return (float) ticksInGame + partialTicks;
    }

    public static void renderTick(TickEvent.RenderTickEvent event) {
        partialTicks = event.renderTickTime;
    }

    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (!Minecraft.getInstance().isPaused()) {
                ticksInGame++;
                partialTicks = 0;
            }
        }
    }
}
