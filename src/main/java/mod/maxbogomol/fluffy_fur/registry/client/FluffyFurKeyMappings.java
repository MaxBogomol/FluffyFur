package mod.maxbogomol.fluffy_fur.registry.client;

import com.mojang.blaze3d.platform.InputConstants;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

public class FluffyFurKeyMappings {
    private static final String CATEGORY = "key.category."+ FluffyFur.MOD_ID+".general";
    public static final KeyMapping SKIN_MENU = new KeyMapping("key."+FluffyFur.MOD_ID+".skin_menu", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, CATEGORY);

    @Mod.EventBusSubscriber(modid = FluffyFur.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
            event.register(SKIN_MENU);
        }
    }
}
