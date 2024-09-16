package mod.maxbogomol.fluffy_fur.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.client.particle.GenericParticle;
import mod.maxbogomol.fluffy_fur.client.particle.ICustomParticleRender;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.ICustomBehaviorParticleRender;
import mod.maxbogomol.fluffy_fur.integration.client.ShadersIntegration;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.fml.ModList;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelRenderHandler {

    public static Matrix4f MATRIX4F = null;
    static MultiBufferSource.BufferSource DELAYED_RENDER = null;
    public static List<ICustomParticleRender> particleList = new ArrayList<>();
    public static Map<GenericParticle, ICustomBehaviorParticleRender> behaviorParticleList = new HashMap<>();

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
        }

        if (!ShadersIntegration.shouldApply()) {
            standardDelayedRender(event);
        } else {
            shadersDelayedRender(event);
        }
    }

    public static void standardDelayedRender(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_WEATHER) {
            Matrix4f last = new Matrix4f(RenderSystem.getModelViewMatrix());
            if (MATRIX4F != null) RenderSystem.getModelViewMatrix().set(MATRIX4F);
            for (RenderType renderType : FluffyFurRenderTypes.translucentRenderTypes) getDelayedRender().endBatch(renderType);
            RenderSystem.getModelViewMatrix().set(last);
            for (RenderType renderType : FluffyFurRenderTypes.translucentParticleRenderTypes) getDelayedRender().endBatch(renderType);
            if (MATRIX4F != null) RenderSystem.getModelViewMatrix().set(MATRIX4F);
            for (RenderType renderType : FluffyFurRenderTypes.additiveRenderTypes) getDelayedRender().endBatch(renderType);
            RenderSystem.getModelViewMatrix().set(last);
            for (RenderType renderType : FluffyFurRenderTypes.additiveParticleRenderTypes) getDelayedRender().endBatch(renderType);
        }
    }

    public static void shadersDelayedRender(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
            RenderSystem.getModelViewStack().pushPose();
            RenderSystem.getModelViewStack().setIdentity();
            if (MATRIX4F != null) RenderSystem.getModelViewStack().mulPoseMatrix(MATRIX4F);
            RenderSystem.applyModelViewMatrix();
            for (RenderType renderType : FluffyFurRenderTypes.translucentParticleRenderTypes) getDelayedRender().endBatch(renderType);
            RenderSystem.getModelViewStack().popPose();
            RenderSystem.applyModelViewMatrix();
            for (RenderType renderType : FluffyFurRenderTypes.translucentRenderTypes) getDelayedRender().endBatch(renderType);
            RenderSystem.getModelViewStack().pushPose();
            RenderSystem.getModelViewStack().setIdentity();
            if (MATRIX4F != null) RenderSystem.getModelViewStack().mulPoseMatrix(MATRIX4F);
            RenderSystem.applyModelViewMatrix();
            for (RenderType renderType : FluffyFurRenderTypes.additiveParticleRenderTypes) getDelayedRender().endBatch(renderType);
            RenderSystem.getModelViewStack().popPose();
            RenderSystem.applyModelViewMatrix();
            for (RenderType renderType : FluffyFurRenderTypes.additiveRenderTypes) getDelayedRender().endBatch(renderType);
        }
    }

    public static MultiBufferSource.BufferSource getDelayedRender() {
        if (DELAYED_RENDER == null) {
            Map<RenderType, BufferBuilder> buffers = new HashMap<>();
            for (RenderType type : FluffyFurRenderTypes.renderTypes) {
                buffers.put(type, new BufferBuilder(ModList.get().isLoaded("embeddium") || ModList.get().isLoaded("rubidium") ? 2097152 : type.bufferSize()));
            }
            DELAYED_RENDER = MultiBufferSource.immediateWithBuffers(buffers, new BufferBuilder(256));
        }
        return DELAYED_RENDER;
    }
}
