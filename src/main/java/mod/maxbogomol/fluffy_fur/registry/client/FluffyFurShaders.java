package mod.maxbogomol.fluffy_fur.registry.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

public class FluffyFurShaders {
    public static ShaderInstance ADDITIVE, ADDITIVE_TEXTURE, TRANSPARENT_PARTICLE, FLUID;

    public static ShaderInstance getGlowing() { return ADDITIVE; }
    public static ShaderInstance getGlowingSprite() { return ADDITIVE_TEXTURE; }
    public static ShaderInstance getSpriteParticle() { return TRANSPARENT_PARTICLE; }

    @Mod.EventBusSubscriber(modid = FluffyFur.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void shaderRegistry(RegisterShadersEvent event) throws IOException {
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation(FluffyFur.MOD_ID, "additive"), DefaultVertexFormat.POSITION_COLOR), shader -> { ADDITIVE = shader; });
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation(FluffyFur.MOD_ID, "additive_texture"), DefaultVertexFormat.POSITION_TEX_COLOR), shader -> { ADDITIVE_TEXTURE = shader; });
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation(FluffyFur.MOD_ID, "transparent_particle"), DefaultVertexFormat.PARTICLE), shader -> { TRANSPARENT_PARTICLE = shader; });
        }
    }
}
