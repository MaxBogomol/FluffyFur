package mod.maxbogomol.fluffy_fur.common.fluid;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.function.Consumer;

public class CustomFluidType extends FluidType {
    private ResourceLocation stillTexture = new ResourceLocation("block/water_still");
    private ResourceLocation flowingTexture = new ResourceLocation("block/water_flow");
    private ResourceLocation overlayTexture = new ResourceLocation("textures/misc/misc_underwater.png");
    private int tintColor = 0xFFFFFF;
    private Vector3f fogColor = new Vector3f(1, 1, 1);
    private float fogStart = -8.0f;
    private float fogEnd = 24.0f;
    private ParticleOptions splashParticle;
    private ParticleOptions bubleParticle;
    private SoundEvent swimSound = SoundEvents.GENERIC_SWIM;
    private SoundEvent splashSound = SoundEvents.GENERIC_SPLASH;

    public CustomFluidType(Properties properties) {
        super(properties);
    }

    public CustomFluidType(ResourceLocation stillTexture, ResourceLocation flowingTexture, ResourceLocation overlayTexture, int tintColor, Vector3f fogColor, Properties properties) {
        super(properties);
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
        this.overlayTexture = overlayTexture;
        this.tintColor = tintColor;
        this.fogColor = fogColor;
    }

    public CustomFluidType setStillTexture(ResourceLocation stillTexture) {
        this.stillTexture = stillTexture;
        return this;
    }

    public CustomFluidType setFlowingTexture(ResourceLocation flowingTexture) {
        this.flowingTexture = flowingTexture;
        return this;
    }

    public CustomFluidType setOverlayTexture(ResourceLocation overlayTexture) {
        this.overlayTexture = overlayTexture;
        return this;
    }

    public CustomFluidType setTintColor(int tintColor) {
        this.tintColor = tintColor;
        return this;
    }

    public CustomFluidType setFogColor(Vector3f fogColor) {
        this.fogColor = fogColor;
        return this;
    }

    public CustomFluidType setFogStart(float fogStart) {
        this.fogStart = fogStart;
        return this;
    }

    public CustomFluidType setFogEnd(float fogEnd) {
        this.fogEnd = fogEnd;
        return this;
    }

    public CustomFluidType setSplashParticle(ParticleOptions splashParticle) {
        this.splashParticle = splashParticle;
        return this;
    }

    public CustomFluidType setBubleParticle(ParticleOptions bubleParticle) {
        this.bubleParticle = bubleParticle;
        return this;
    }

    public CustomFluidType setSwimSound(SoundEvent swimSound) {
        this.swimSound = swimSound;
        return this;
    }

    public CustomFluidType setSplashSound(SoundEvent splashSound) {
        this.splashSound = splashSound;
        return this;
    }

    public ResourceLocation getStillTexture() {
        return stillTexture;
    }

    public ResourceLocation getFlowingTexture() {
        return flowingTexture;
    }

    public int getTintColor() {
        return tintColor;
    }

    public ResourceLocation getOverlayTexture() {
        return overlayTexture;
    }

    public Vector3f getFogColor() {
        return fogColor;
    }

    public float getFogStart() {
        return fogStart;
    }

    public float getFogEnd() {
        return fogEnd;
    }

    public ParticleOptions getSplashParticle() {
        return splashParticle;
    }

    public ParticleOptions getBubleParticle() {
        return bubleParticle;
    }

    public SoundEvent getSwimSound() {
        return swimSound;
    }

    public SoundEvent getSplashSound() {
        return splashSound;
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return stillTexture;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return flowingTexture;
            }

            @Override
            public @Nullable ResourceLocation getOverlayTexture() {
                return overlayTexture;
            }

            @Override
            public int getTintColor() {
                return tintColor;
            }

            @Override
            public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                return fogColor;
            }

            @Override
            public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                RenderSystem.setShaderFogStart(fogStart);
                RenderSystem.setShaderFogEnd(fogEnd);
            }

            @Nullable
            public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                return overlayTexture;
            }
        });
    }
}