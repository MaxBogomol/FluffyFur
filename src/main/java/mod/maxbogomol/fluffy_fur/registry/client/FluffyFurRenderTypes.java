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

    public static final RenderStateShard.LightmapStateShard LIGHTMAP = new RenderStateShard.LightmapStateShard(true);
    public static final RenderStateShard.LightmapStateShard NO_LIGHTMAP = new RenderStateShard.LightmapStateShard(false);
    public static final RenderStateShard.OverlayStateShard OVERLAY = new RenderStateShard.OverlayStateShard(true);
    public static final RenderStateShard.OverlayStateShard NO_OVERLAY = new RenderStateShard.OverlayStateShard(false);
    public static final RenderStateShard.CullStateShard CULL = new RenderStateShard.CullStateShard(true);
    public static final RenderStateShard.CullStateShard NO_CULL = new RenderStateShard.CullStateShard(false);
    public static final RenderStateShard.WriteMaskStateShard COLOR_WRITE = new RenderStateShard.WriteMaskStateShard(true, false);
    public static final RenderStateShard.WriteMaskStateShard DEPTH_WRITE = new RenderStateShard.WriteMaskStateShard(false, true);
    public static final RenderStateShard.WriteMaskStateShard COLOR_DEPTH_WRITE = new RenderStateShard.WriteMaskStateShard(true, true);
    public static final RenderStateShard.TextureStateShard BLOCK_SHEET = new RenderStateShard.TextureStateShard(TextureAtlas.LOCATION_BLOCKS, false, false);
    public static final RenderStateShard.TextureStateShard BLOCK_SHEET_MIPPED = new RenderStateShard.TextureStateShard(TextureAtlas.LOCATION_BLOCKS, false, true);
    public static final RenderStateShard.TextureStateShard PARTICLE_SHEET = new RenderStateShard.TextureStateShard(TextureAtlas.LOCATION_PARTICLES, false, false);
    public static final RenderStateShard.TextureStateShard PARTICLE_SHEET_MIPPED = new RenderStateShard.TextureStateShard(TextureAtlas.LOCATION_PARTICLES, false, true);

    public static final RenderStateShard.ShaderStateShard GLOWING_SPRITE_SHADER = new RenderStateShard.ShaderStateShard(FluffyFurShaders::getGlowingSprite);

    public static final RenderType ADDITIVE_TEXTURE = RenderType.create(
            FluffyFur.MOD_ID + ":additive_texture",
            DefaultVertexFormat.POSITION_TEX_COLOR,
            VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setWriteMaskState(COLOR_WRITE).setLightmapState(NO_LIGHTMAP).setTransparencyState(NORMAL_TRANSPARENCY)
                    .setTextureState(BLOCK_SHEET).setShaderState(GLOWING_SPRITE_SHADER).createCompositeState(false));

    public static final RenderType GLOWING = RenderType.create(
            FluffyFur.MOD_ID + ":additive",
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setWriteMaskState(COLOR_WRITE).setLightmapState(NO_LIGHTMAP).setTransparencyState(ADDITIVE_TRANSPARENCY)
                    .setShaderState(new RenderStateShard.ShaderStateShard(FluffyFurShaders::getGlowing)).createCompositeState(false));

    public static RenderType GLOWING_PARTICLE = RenderType.create(
            FluffyFur.MOD_ID + ":additive_particle",
            DefaultVertexFormat.PARTICLE,
            VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setWriteMaskState(COLOR_WRITE).setLightmapState(NO_LIGHTMAP).setTransparencyState(ADDITIVE_TRANSPARENCY)
                    .setTextureState(PARTICLE_SHEET).setShaderState(new RenderStateShard.ShaderStateShard(FluffyFurShaders::getGlowingSprite)).createCompositeState(false));

    public static RenderType GLOWING_TERRAIN_PARTICLE = RenderType.create(
            FluffyFur.MOD_ID + ":glowing_terrain_particle",
            DefaultVertexFormat.PARTICLE,
            VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setWriteMaskState(COLOR_WRITE).setLightmapState(NO_LIGHTMAP).setTransparencyState(ADDITIVE_TRANSPARENCY)
                    .setTextureState(BLOCK_SHEET).setShaderState(new RenderStateShard.ShaderStateShard(FluffyFurShaders::getGlowingSprite)).createCompositeState(false));

    public static RenderType DELAYED_PARTICLE = RenderType.create(
            FluffyFur.MOD_ID + ":transparent_particle",
            DefaultVertexFormat.PARTICLE,
            VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setWriteMaskState(COLOR_WRITE).setLightmapState(LIGHTMAP).setTransparencyState(NORMAL_TRANSPARENCY)
                    .setTextureState(PARTICLE_SHEET).setShaderState(new RenderStateShard.ShaderStateShard(FluffyFurShaders::getSpriteParticle)).createCompositeState(false));

    public static RenderType DELAYED_TERRAIN_PARTICLE = RenderType.create(
            FluffyFur.MOD_ID + ":delayed_terrain_particle",
            DefaultVertexFormat.PARTICLE,
            VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setWriteMaskState(COLOR_WRITE).setLightmapState(LIGHTMAP).setTransparencyState(NORMAL_TRANSPARENCY)
                    .setTextureState(BLOCK_SHEET).setShaderState(new RenderStateShard.ShaderStateShard(FluffyFurShaders::getSpriteParticle)).createCompositeState(false));

    public static final RenderType TRANSPARENT_TEXTURE = RenderType.create(
            FluffyFur.MOD_ID + ":transparent_texture",
            DefaultVertexFormat.PARTICLE,
            VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setWriteMaskState(COLOR_WRITE).setLightmapState(LIGHTMAP).setTransparencyState(NORMAL_TRANSPARENCY)
                    .setTextureState(BLOCK_SHEET).setShaderState(new RenderStateShard.ShaderStateShard(FluffyFurShaders::getSpriteParticle)).createCompositeState(false));

    @Mod.EventBusSubscriber(modid = FluffyFur.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void registerRenderTypes(FMLClientSetupEvent event) {
            renderTypes.add(DELAYED_PARTICLE);
            renderTypes.add(DELAYED_TERRAIN_PARTICLE);
            renderTypes.add(GLOWING_PARTICLE);
            renderTypes.add(GLOWING_TERRAIN_PARTICLE);
            renderTypes.add(ADDITIVE_TEXTURE);
            renderTypes.add(GLOWING);
            renderTypes.add(TRANSPARENT_TEXTURE);
        }
    }

    public static MultiBufferSource.BufferSource getDelayedRender() {
        return LevelRenderHandler.getDelayedRender();
    }
}
