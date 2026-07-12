package mod.maxbogomol.fluffy_fur.client.gui.components;

import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class FluffyFurPanoramaRenderer extends PanoramaRenderer {
    public static CubeMap CUBE_MAP = new CubeMap(new ResourceLocation("textures/gui/title/background/panorama"));

    public float oldSpin = 0;

    public FluffyFurPanoramaRenderer() {
        super(CUBE_MAP);
    }

    public FluffyFurPanoramaRenderer setTexture(ResourceLocation texture) {
        cubeMap = new CubeMap(texture);
        return this;
    }

    @Override
    public void render(float partialTick, float alpha) {
        float deltaSpin = Mth.lerp(ClientTickHandler.partialTicks, oldSpin, spin);
        cubeMap.render(this.minecraft, 10.0F, -wrap(deltaSpin, 360.0F), alpha);
    }

    public void render(float alpha) {
        render(0, alpha);
    }

    public void render() {
        render(0, 1f);
    }

    public void tick() {
        oldSpin = spin;
        float speed = (float) ((double) this.minecraft.options.panoramaSpeed().get());
        spin = spin + speed * 0.1f;
    }

    public static float wrap(float value, float max) {
        return value > max ? value - max : value;
    }
}
