package mod.maxbogomol.fluffy_fur.client.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import mod.maxbogomol.fluffy_fur.client.render.LevelRenderHandler;
import mod.maxbogomol.fluffy_fur.integration.client.ShadersIntegration;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurShaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import org.lwjgl.opengl.GL11;

public class SpriteParticleRenderType implements ParticleRenderType {

    public static final SpriteParticleRenderType INSTANCE = new SpriteParticleRenderType();

    @Override
    public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShader(FluffyFurShaders::getSpriteParticle);
        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
        if (ShadersIntegration.isShadersEnabled()) LevelRenderHandler.MATRIX4F = RenderSystem.getModelViewMatrix();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
    }

    @Override
    public void end(Tesselator tesselator) {
        tesselator.end();
        RenderSystem.enableDepthTest();
        Minecraft.getInstance().textureManager.getTexture(TextureAtlas.LOCATION_PARTICLES).restoreLastBlurMipmap();
        RenderSystem.depthMask(true);
    }
}