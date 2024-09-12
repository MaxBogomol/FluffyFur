package mod.maxbogomol.fluffy_fur.registry.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.render.LevelRenderHandler;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.ArrayList;
import java.util.List;

public class FluffyFurRenderTypes {

    public static List<RenderType> renderTypes = new ArrayList<>();

    public static final RenderStateShard.TransparencyStateShard ADDITIVE_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("additive_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });

    public static final RenderStateShard.TransparencyStateShard NORMAL_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("normal_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });

    public static final RenderStateShard.TransparencyStateShard TRANSLUCENT_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("translucent_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });

    public static final RenderType GLOWING_SPRITE = RenderType.create(
            FluffyFur.MOD_ID + ":glowing_sprite",
            DefaultVertexFormat.POSITION_TEX_COLOR,
            VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setWriteMaskState(new RenderStateShard.WriteMaskStateShard(true, false))
                    .setLightmapState(new RenderStateShard.LightmapStateShard(false))
                    .setTransparencyState(NORMAL_TRANSPARENCY)
                    .setTextureState(new RenderStateShard.TextureStateShard(TextureAtlas.LOCATION_BLOCKS, false, false))
                    .setShaderState(new RenderStateShard.ShaderStateShard(FluffyFurShaders::getGlowingSprite))
                    .createCompositeState(false));

    public static final RenderType GLOWING = RenderType.create(
            FluffyFur.MOD_ID + ":glowing",
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setWriteMaskState(new RenderStateShard.WriteMaskStateShard(true, false))
                    .setLightmapState(new RenderStateShard.LightmapStateShard(false))
                    .setTransparencyState(ADDITIVE_TRANSPARENCY)
                    .setShaderState(new RenderStateShard.ShaderStateShard(FluffyFurShaders::getGlowing))
                    .createCompositeState(false));

    public static RenderType GLOWING_PARTICLE = RenderType.create(
            FluffyFur.MOD_ID + ":glowing_particle",
            DefaultVertexFormat.PARTICLE,
            VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setWriteMaskState(new RenderStateShard.WriteMaskStateShard(true, false))
                    .setLightmapState(new RenderStateShard.LightmapStateShard(false))
                    .setTransparencyState(ADDITIVE_TRANSPARENCY)
                    .setTextureState(new RenderStateShard.TextureStateShard(TextureAtlas.LOCATION_PARTICLES, false, false))
                    .setShaderState(new RenderStateShard.ShaderStateShard(FluffyFurShaders::getGlowingParticle))
                    .createCompositeState(false));

    public static RenderType GLOWING_TERRAIN_PARTICLE = RenderType.create(
            FluffyFur.MOD_ID + ":glowing_terrain_particle",
            DefaultVertexFormat.PARTICLE,
            VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setWriteMaskState(new RenderStateShard.WriteMaskStateShard(true, false))
                    .setLightmapState(new RenderStateShard.LightmapStateShard(false))
                    .setTransparencyState(ADDITIVE_TRANSPARENCY)
                    .setTextureState(new RenderStateShard.TextureStateShard(TextureAtlas.LOCATION_BLOCKS, false, false))
                    .setShaderState(new RenderStateShard.ShaderStateShard(FluffyFurShaders::getGlowingParticle))
                    .createCompositeState(false));

    public static RenderType DELAYED_PARTICLE = RenderType.create(
            FluffyFur.MOD_ID + ":delayed_particle",
            DefaultVertexFormat.PARTICLE,
            VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setWriteMaskState(new RenderStateShard.WriteMaskStateShard(true, false))
                    .setLightmapState(new RenderStateShard.LightmapStateShard(true))
                    .setTransparencyState(NORMAL_TRANSPARENCY)
                    .setTextureState(new RenderStateShard.TextureStateShard(TextureAtlas.LOCATION_PARTICLES, false, false))
                    .setShaderState(new RenderStateShard.ShaderStateShard(FluffyFurShaders::getSpriteParticle))
                    .createCompositeState(false));

    public static RenderType DELAYED_TERRAIN_PARTICLE = RenderType.create(
            FluffyFur.MOD_ID + ":delayed_terrain_particle",
            DefaultVertexFormat.PARTICLE,
            VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setWriteMaskState(new RenderStateShard.WriteMaskStateShard(true, false))
                    .setLightmapState(new RenderStateShard.LightmapStateShard(true))
                    .setTransparencyState(NORMAL_TRANSPARENCY)
                    .setTextureState(new RenderStateShard.TextureStateShard(TextureAtlas.LOCATION_BLOCKS, false, false))
                    .setShaderState(new RenderStateShard.ShaderStateShard(FluffyFurShaders::getSpriteParticle))
                    .createCompositeState(false));

    public static final RenderType FLUID = RenderType.create(
            FluffyFur.MOD_ID + ":fluid",
            DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
            VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setWriteMaskState(new RenderStateShard.WriteMaskStateShard(true, true))
                    .setLightmapState(new RenderStateShard.LightmapStateShard(true))
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setTextureState(new RenderStateShard.TextureStateShard(TextureAtlas.LOCATION_BLOCKS, false, true))
                    .setShaderState(new RenderStateShard.ShaderStateShard(FluffyFurShaders::getFluid))
                    .setCullState(new RenderStateShard.CullStateShard(true))
                    .setOverlayState(new RenderStateShard.OverlayStateShard(true))
                    .createCompositeState(false));

    @Mod.EventBusSubscriber(modid = FluffyFur.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void registerRenderTypes(FMLClientSetupEvent event) {
            renderTypes.add(DELAYED_PARTICLE);
            renderTypes.add(DELAYED_TERRAIN_PARTICLE);
            renderTypes.add(GLOWING_PARTICLE);
            renderTypes.add(GLOWING_TERRAIN_PARTICLE);
            renderTypes.add(GLOWING_SPRITE);
            renderTypes.add(GLOWING);
            renderTypes.add(FLUID);
        }
    }

    public static MultiBufferSource.BufferSource getDelayedRender() {
        return LevelRenderHandler.getDelayedRender();
    }
}
