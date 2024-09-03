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
    public static ShaderInstance GLOWING, GLOWING_SPRITE, GLOWING_PARTICLE, SPRITE_PARTICLE, FLUID;

    public static ShaderInstance getGlowing() { return GLOWING; }

    public static ShaderInstance getGlowingSprite() { return GLOWING_SPRITE; }

    public static ShaderInstance getGlowingParticle() { return GLOWING_PARTICLE; }
    public static ShaderInstance getSpriteParticle() { return SPRITE_PARTICLE; }
    public static ShaderInstance getFluid() { return FLUID; }

    @Mod.EventBusSubscriber(modid = FluffyFur.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void shaderRegistry(RegisterShadersEvent event) throws IOException {
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation(FluffyFur.MOD_ID, "glowing"), DefaultVertexFormat.POSITION_COLOR), shader -> { GLOWING = shader; });
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation(FluffyFur.MOD_ID, "glowing_sprite"), DefaultVertexFormat.POSITION_TEX_COLOR), shader -> { GLOWING_SPRITE = shader; });
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation(FluffyFur.MOD_ID, "glowing_particle"), DefaultVertexFormat.PARTICLE), shader -> { GLOWING_PARTICLE = shader; });
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation(FluffyFur.MOD_ID, "sprite_particle"), DefaultVertexFormat.PARTICLE), shader -> { SPRITE_PARTICLE = shader; });
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation(FluffyFur.MOD_ID, "fluid"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP), shader -> { FLUID = shader; });
        }
    }
}
