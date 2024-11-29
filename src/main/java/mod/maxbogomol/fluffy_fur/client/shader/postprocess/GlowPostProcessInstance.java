package mod.maxbogomol.fluffy_fur.client.shader.postprocess;

import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import org.joml.Vector3f;

import java.util.function.BiConsumer;

public class GlowPostProcessInstance extends PostProcessInstance {
    public Vector3f center;
    public Vector3f color;
    public float radius = 10;
    public float intensity = 1;
    public float fade = 1;
    public float startTick = 0;
    public float tickTime = 0;
    public float fadeTime = 20;
    public boolean isFade = true;

    public GlowPostProcessInstance(Vector3f center, Vector3f color) {
        this.center = center;
        this.color = color;
        this.startTick = ClientTickHandler.getTotal();
    }

    public GlowPostProcessInstance setRadius(float radius) {
        this.radius = radius;
        return this;
    }

    public GlowPostProcessInstance setIntensity(float intensity) {
        this.intensity = intensity;
        return this;
    }

    public GlowPostProcessInstance setFade(float fade) {
        this.fade = fade;
        return this;
    }

    public GlowPostProcessInstance setStartTime(float time) {
        this.startTick = time;
        return this;
    }

    public GlowPostProcessInstance setTime(float time) {
        this.tickTime = time;
        return this;
    }

    public GlowPostProcessInstance setFadeTime(float fade) {
        this.fadeTime = fade;
        return this;
    }

    public GlowPostProcessInstance setIsFade(boolean fade) {
        this.isFade = fade;
        return this;
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        if (isFade) {
            tickTime = ClientTickHandler.getTotal() - startTick;
            fade = 1f - (tickTime / fadeTime);
            if (tickTime > fadeTime) {
                fade = 0;
                remove();
            }
        }
    }

    @Override
    public void writeDataToBuffer(BiConsumer<Integer, Float> writer) {
        writer.accept(0, center.x());
        writer.accept(1, center.y());
        writer.accept(2, center.z());
        writer.accept(3, color.x());
        writer.accept(4, color.y());
        writer.accept(5, color.z());
        writer.accept(6, radius);
        writer.accept(7, intensity);
        writer.accept(8, fade);
        writer.accept(9, tickTime);
        writer.accept(10, fadeTime);
    }
}
