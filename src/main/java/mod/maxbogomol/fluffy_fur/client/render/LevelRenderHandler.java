package mod.maxbogomol.fluffy_fur.client.render;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.client.particle.GenericParticle;
import mod.maxbogomol.fluffy_fur.client.particle.ICustomParticleRender;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.ICustomBehaviorParticleRender;
import mod.maxbogomol.fluffy_fur.client.shader.ExtendedShaderInstance;
import mod.maxbogomol.fluffy_fur.integration.client.ShadersIntegration;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL30C;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelRenderHandler {

    public static Matrix4f MATRIX4F = null;
    static MultiBufferSource.BufferSource DELAYED_RENDER = null;
    public static RenderTarget DEPTH_CACHE;
    public static float FOG_START = 0;
    public static List<ICustomParticleRender> particleList = new ArrayList<>();
    public static Map<GenericParticle, ICustomBehaviorParticleRender> behaviorParticleList = new HashMap<>();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLevelRender(RenderLevelStageEvent event) {
        PoseStack stack = event.getPoseStack();
        float partialTicks = event.getPartialTick();
        MultiBufferSource bufferSource = LevelRenderHandler.getDelayedRender();

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
            Vec3 pos = event.getCamera().getPosition();

            stack.pushPose();
            stack.translate(-pos.x, -pos.y, -pos.z);
            for (ICustomParticleRender particle : particleList) {
                particle.render(stack, bufferSource, partialTicks);
            }
            for (GenericParticle particle : behaviorParticleList.keySet()) {
                behaviorParticleList.get(particle).render(particle, stack, bufferSource, partialTicks);
            }
            stack.popPose();
            particleList.clear();
            behaviorParticleList.clear();

            if (!ShadersIntegration.shouldApply()) MATRIX4F = new Matrix4f(RenderSystem.getModelViewMatrix());
            FOG_START = RenderSystem.getShaderFogStart();
        }

        if (!ShadersIntegration.shouldApply()) {
            standardDelayedRender(event);
        } else {
            shadersDelayedRender(event);
        }
    }


    public static void standardDelayedRender(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_WEATHER) {
            copyDepthBuffer(DEPTH_CACHE);
            Matrix4f last = new Matrix4f(RenderSystem.getModelViewMatrix());
            if (MATRIX4F != null) RenderSystem.getModelViewMatrix().set(MATRIX4F);
            for (RenderType renderType : FluffyFurRenderTypes.translucentRenderTypes) endBatch(renderType);
            RenderSystem.getModelViewMatrix().set(last);
            for (RenderType renderType : FluffyFurRenderTypes.translucentParticleRenderTypes) endBatch(renderType);
            if (MATRIX4F != null) RenderSystem.getModelViewMatrix().set(MATRIX4F);
            for (RenderType renderType : FluffyFurRenderTypes.additiveRenderTypes) endBatch(renderType);
            RenderSystem.getModelViewMatrix().set(last);
            for (RenderType renderType : FluffyFurRenderTypes.additiveParticleRenderTypes) endBatch(renderType);
        }
    }

    public static void shadersDelayedRender(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
            copyDepthBuffer(DEPTH_CACHE);
            RenderSystem.setShaderFogStart(FOG_START);
            RenderSystem.getModelViewStack().pushPose();
            RenderSystem.getModelViewStack().setIdentity();
            if (MATRIX4F != null) RenderSystem.getModelViewStack().mulPoseMatrix(MATRIX4F);
            RenderSystem.applyModelViewMatrix();
            for (RenderType renderType : FluffyFurRenderTypes.translucentParticleRenderTypes) endBatch(renderType);
            RenderSystem.getModelViewStack().popPose();
            RenderSystem.applyModelViewMatrix();
            for (RenderType renderType : FluffyFurRenderTypes.translucentRenderTypes) endBatch(renderType);
            RenderSystem.getModelViewStack().pushPose();
            RenderSystem.getModelViewStack().setIdentity();
            if (MATRIX4F != null) RenderSystem.getModelViewStack().mulPoseMatrix(MATRIX4F);
            RenderSystem.applyModelViewMatrix();
            for (RenderType renderType : FluffyFurRenderTypes.additiveParticleRenderTypes) endBatch(renderType);
            RenderSystem.getModelViewStack().popPose();
            RenderSystem.applyModelViewMatrix();
            for (RenderType renderType : FluffyFurRenderTypes.additiveRenderTypes) endBatch(renderType);
            FogRenderer.setupNoFog();
        }
    }

    public static void endBatch(RenderType renderType) {
        ShaderInstance shader = RenderUtil.getShader(renderType);
        if (shader != null) {
            shader.setSampler("SceneDepthBuffer", DEPTH_CACHE.getDepthTextureId());
            shader.setSampler("SceneDiffuseBuffer", Minecraft.getInstance().getMainRenderTarget().getColorTextureId());
            shader.safeGetUniform("InvProjMat").set(new Matrix4f(RenderSystem.getProjectionMatrix()).invert());
        }
        getDelayedRender().endBatch(renderType);
        if (shader instanceof ExtendedShaderInstance extendedShader) {
            extendedShader.setUniformDefaults();
        }
    }

    public static void copyDepthBuffer(RenderTarget tempRenderTarget) {
        setupDepthBuffer();
        enableStencil();
        if (tempRenderTarget == null) return;
        RenderTarget mainRenderTarget = Minecraft.getInstance().getMainRenderTarget();
        tempRenderTarget.copyDepthFrom(mainRenderTarget);
        GlStateManager._glBindFramebuffer(GL30C.GL_DRAW_FRAMEBUFFER, mainRenderTarget.frameBufferId);
    }

    public static void setupDepthBuffer() {
        if (DEPTH_CACHE == null) {
            DEPTH_CACHE = new TextureTarget(Minecraft.getInstance().getMainRenderTarget().width, Minecraft.getInstance().getMainRenderTarget().height, true, Minecraft.ON_OSX);
        }
    }

    public static void enableStencil() {
        if (Minecraft.getInstance().getMainRenderTarget().isStencilEnabled()) {
            DEPTH_CACHE.enableStencil();
        }
    }

    public static void resize(int width, int height) {
        if (DEPTH_CACHE != null) {
            DEPTH_CACHE.resize(width, height, Minecraft.ON_OSX);
        }
    }

    public static MultiBufferSource.BufferSource getDelayedRender() {
        if (DELAYED_RENDER == null) {
            Map<RenderType, BufferBuilder> buffers = new HashMap<>();
            for (RenderType type : FluffyFurRenderTypes.renderTypes) {
                buffers.put(type, new BufferBuilder(isLargeSizeBuffer() ? 2097152 : type.bufferSize()));
            }
            DELAYED_RENDER = MultiBufferSource.immediateWithBuffers(buffers, new BufferBuilder(256));
        }
        return DELAYED_RENDER;
    }

    public static boolean isLargeSizeBuffer() {
        return ModList.get().isLoaded("embeddium") || ModList.get().isLoaded("rubidium") || ModList.get().isLoaded("sodium");
    }
}
