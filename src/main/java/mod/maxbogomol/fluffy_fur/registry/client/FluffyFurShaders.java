package mod.maxbogomol.fluffy_fur.registry.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.shader.postprocess.*;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.io.IOException;

public class FluffyFurShaders {
    public static ShaderInstance
            ADDITIVE_TEXTURE, ADDITIVE, TRANSLUCENT_TEXTURE, TRANSLUCENT,
            ADDITIVE_DISTORT, TRANSLUCENT_DISTORT;

    @Mod.EventBusSubscriber(modid = FluffyFur.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void registerShaders(FMLClientSetupEvent event) {
            PostProcessHandler.addInstance(RainFogPostProcess.INSTANCE);
            PostProcessHandler.addInstance(DepthPostProcess.INSTANCE);
            PostProcessHandler.addInstance(GlowPostProcess.INSTANCE);
            PostProcessHandler.addInstance(NormalGlowPostProcess.INSTANCE);
        }

        @SubscribeEvent
        public static void shaderRegistry(RegisterShadersEvent event) throws IOException {
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation(FluffyFur.MOD_ID, "additive_texture"), DefaultVertexFormat.POSITION_TEX_COLOR), shader -> ADDITIVE_TEXTURE = shader);
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation(FluffyFur.MOD_ID, "additive"), DefaultVertexFormat.POSITION_COLOR), shader -> ADDITIVE = shader);
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation(FluffyFur.MOD_ID, "translucent_texture"), DefaultVertexFormat.PARTICLE), shader -> TRANSLUCENT_TEXTURE = shader);
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation(FluffyFur.MOD_ID, "translucent"), DefaultVertexFormat.PARTICLE), shader -> TRANSLUCENT = shader);

            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation(FluffyFur.MOD_ID, "additive_distort"), FluffyFurVertexFormats.ADDITIVE_DISTORT), shader -> ADDITIVE_DISTORT = shader);
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation(FluffyFur.MOD_ID, "translucent_distort"), FluffyFurVertexFormats.TRANSLUCENT_DISTORT), shader -> TRANSLUCENT_DISTORT = shader);
        }
    }

    public static ShaderInstance getAdditiveTexture() {
        return ADDITIVE_TEXTURE;
    }

    public static ShaderInstance getAdditive() {
        return ADDITIVE;
    }

    public static ShaderInstance getTranslucentTexture() {
        return TRANSLUCENT_TEXTURE;
    }

    public static ShaderInstance getTranslucent() {
        return TRANSLUCENT;
    }

    public static ShaderInstance getAdditiveDistort() {
        return ADDITIVE_DISTORT;
    }

    public static ShaderInstance getTranslucentDistort() {
        return TRANSLUCENT_DISTORT;
    }
}