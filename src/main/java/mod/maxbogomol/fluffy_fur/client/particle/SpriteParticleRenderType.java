package mod.maxbogomol.fluffy_fur.client.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import mod.maxbogomol.fluffy_fur.FluffyFurClient;
import mod.maxbogomol.fluffy_fur.client.render.WorldRenderHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import org.lwjgl.opengl.GL11;

public class SpriteParticleRenderType implements ParticleRenderType {

    public static final SpriteParticleRenderType INSTANCE = new SpriteParticleRenderType();

    private static void beginRenderCommon(BufferBuilder bufferBuilder, TextureManager textureManager) {
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShader(FluffyFurClient::getSpriteParticleShader);
        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
        WorldRenderHandler.particleMVMatrix = RenderSystem.getModelViewMatrix();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
    }

    private static void endRenderCommon() {
        Minecraft.getInstance().textureManager.getTexture(TextureAtlas.LOCATION_PARTICLES).restoreLastBlurMipmap();
        RenderSystem.depthMask(true);
    }

    @Override
    public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
        beginRenderCommon(bufferBuilder, textureManager);
    }

    @Override
    public void end(Tesselator tesselator) {
        tesselator.end();
        RenderSystem.enableDepthTest();
        endRenderCommon();
    }
}