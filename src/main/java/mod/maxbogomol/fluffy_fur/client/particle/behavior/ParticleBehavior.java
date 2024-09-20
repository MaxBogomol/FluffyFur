package mod.maxbogomol.fluffy_fur.client.particle.behavior;

import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.maxbogomol.fluffy_fur.client.particle.GenericParticle;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.component.ParticleBehaviorComponent;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import net.minecraft.client.Camera;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Random;

public class ParticleBehavior {

    public static final Random random = new Random();

    public SpinParticleData xSpinData;
    public SpinParticleData ySpinData;
    public SpinParticleData zSpinData;

    public float xOffset;
    public float yOffset;
    public float zOffset;

    public boolean firstSide;
    public boolean secondSide;
    public boolean camera;
    public boolean xRotCam;
    public boolean yRotCam;

    public ParticleBehavior(SpinParticleData xSpinData, SpinParticleData ySpinData, SpinParticleData zSpinData, float xOffset, float yOffset, float zOffset, boolean firstSide, boolean secondSide, boolean camera, boolean xRotCam, boolean yRotCam) {
        this.xSpinData = xSpinData;
        this.ySpinData = ySpinData;
        this.zSpinData = zSpinData;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
        this.firstSide = firstSide;
        this.secondSide = secondSide;
        this.camera = camera;
        this.xRotCam = xRotCam;
        this.yRotCam = yRotCam;
    }

    public ParticleBehavior copy() {
        return new ParticleBehavior(xSpinData, ySpinData, zSpinData, xOffset, yOffset, zOffset, firstSide, secondSide, camera, xRotCam, yRotCam);
    }

    public static ParticleBehaviorBuilder create() {
        return new ParticleBehaviorBuilder(0, 0, 0);
    }

    public static ParticleBehaviorBuilder create(float xOffset, float yOffset, float zOffset) {
        return new ParticleBehaviorBuilder((float) Math.toRadians(xOffset), (float) Math.toRadians(yOffset), (float) Math.toRadians(zOffset));
    }

    public ParticleBehaviorComponent getComponent() {
        return new ParticleBehaviorComponent();
    }

    public void init(GenericParticle particle) {
        particle.behaviorComponent = getComponent();
        ParticleBehaviorComponent component = particle.behaviorComponent;

        component.xRoll = xSpinData.spinOffset + xSpinData.startingValue;
        component.randomXSpin = (GenericParticle.pickRandomValue(0, xSpinData.rsp1, xSpinData.rsp2));
        if (random.nextBoolean()) component.randomXSpin = -component.randomXSpin;
        component.xRoll = component.xRoll + GenericParticle.pickRandomRollValue(0, xSpinData.rso1, xSpinData.rso2);

        component.yRoll = ySpinData.spinOffset + ySpinData.startingValue;
        component.randomYSpin = (GenericParticle.pickRandomValue(0, ySpinData.rsp1, ySpinData.rsp2));
        if (random.nextBoolean()) component.randomYSpin = -component.randomYSpin;
        component.yRoll = component.yRoll + GenericParticle.pickRandomRollValue(0, ySpinData.rso1, ySpinData.rso2);

        component.zRoll = zSpinData.spinOffset + zSpinData.startingValue;
        component.randomZSpin = (GenericParticle.pickRandomValue(0, zSpinData.rsp1, zSpinData.rsp2));
        if (random.nextBoolean()) component.randomZSpin = -component.randomZSpin;
        component.zRoll = component.zRoll + GenericParticle.pickRandomRollValue(0, zSpinData.rso1, zSpinData.rso2);

        component.srx = GenericParticle.pickRandomValue(xSpinData.startingValue, xSpinData.rs1, xSpinData.rs2);
        component.mrx = GenericParticle.pickRandomValue(xSpinData.middleValue, xSpinData.rm1, xSpinData.rm2);
        component.erx = GenericParticle.pickRandomValue(xSpinData.endingValue, xSpinData.re1, xSpinData.re2);

        component.sry = GenericParticle.pickRandomValue(ySpinData.startingValue, xSpinData.rs1, xSpinData.rs2);
        component.mry = GenericParticle.pickRandomValue(ySpinData.middleValue, xSpinData.rm1, xSpinData.rm2);
        component.ery = GenericParticle.pickRandomValue(ySpinData.endingValue, xSpinData.re1, xSpinData.re2);

        component.srz = GenericParticle.pickRandomValue(zSpinData.startingValue, xSpinData.rs1, xSpinData.rs2);
        component.mrz = GenericParticle.pickRandomValue(zSpinData.middleValue, xSpinData.rm1, xSpinData.rm2);
        component.erz = GenericParticle.pickRandomValue(zSpinData.endingValue, xSpinData.re1, xSpinData.re2);
    }

    public void updateTraits(GenericParticle particle) {
        ParticleBehaviorComponent component = particle.behaviorComponent;

        component.xORoll = component.xRoll;
        component.xRoll = component.xRoll + xSpinData.getValue(particle.age, particle.lifetime, component.srx, component.mrx, component.erx) + component.randomXSpin;

        component.yORoll = component.yRoll;
        component.yRoll = component.yRoll + ySpinData.getValue(particle.age, particle.lifetime, component.sry, component.mry, component.ery) + component.randomYSpin;

        component.zORoll = component.zRoll;
        component.zRoll = component.zRoll + zSpinData.getValue(particle.age, particle.lifetime, component.srz, component.mrz, component.erz) + component.randomZSpin;
    }

    public void updateRenderTraits(GenericParticle particle, float partialTicks) {

    }

    public void render(GenericParticle particle, VertexConsumer vertexConsumer, Camera renderInfo, float partialTicks) {
        if (particle.shouldRenderTraits) updateRenderTraits(particle, partialTicks);

        Vec3 pos = getPosition(particle, renderInfo, partialTicks);
        Quaternionf quaternionf = getRotate(particle, renderInfo, partialTicks);

        Vector3f[] avector3f = getQuad(particle, renderInfo, partialTicks);
        float f3 = particle.getQuadSize(partialTicks);

        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i];
            vector3f.rotate(quaternionf);
            vector3f.mul(f3);
            vector3f.add((float) pos.x(), (float) pos.y(), (float) pos.z());
        }

        float u0 = particle.getU0();
        float u1 = particle.getU1();
        float v0 = particle.getV0();
        float v1 = particle.getV1();
        int light = particle.getLightColor(partialTicks);

        if (firstSide) {
            vertexConsumer.vertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z()).uv(u1, v1).color(particle.rCol, particle.gCol, particle.bCol, particle.alpha).uv2(light).endVertex();
            vertexConsumer.vertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z()).uv(u1, v0).color(particle.rCol, particle.gCol, particle.bCol, particle.alpha).uv2(light).endVertex();
            vertexConsumer.vertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z()).uv(u0, v0).color(particle.rCol, particle.gCol, particle.bCol, particle.alpha).uv2(light).endVertex();
            vertexConsumer.vertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z()).uv(u0, v1).color(particle.rCol, particle.gCol, particle.bCol, particle.alpha).uv2(light).endVertex();
        }

        if (secondSide) {
            vertexConsumer.vertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z()).uv(u1, v1).color(particle.rCol, particle.gCol, particle.bCol, particle.alpha).uv2(light).endVertex();
            vertexConsumer.vertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z()).uv(u1, v0).color(particle.rCol, particle.gCol, particle.bCol, particle.alpha).uv2(light).endVertex();
            vertexConsumer.vertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z()).uv(u0, v0).color(particle.rCol, particle.gCol, particle.bCol, particle.alpha).uv2(light).endVertex();
            vertexConsumer.vertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z()).uv(u0, v1).color(particle.rCol, particle.gCol, particle.bCol, particle.alpha).uv2(light).endVertex();
        }
    }

    public Vec3 getPosition(GenericParticle particle, Camera renderInfo, float partialTicks) {
        Vec3 vec3 = renderInfo.getPosition();
        float x = (float) (Mth.lerp(partialTicks, particle.xo, particle.x) - vec3.x());
        float y = (float) (Mth.lerp(partialTicks, particle.yo, particle.y) - vec3.y());
        float z = (float) (Mth.lerp(partialTicks, particle.zo, particle.z) - vec3.z());
        return new Vec3(x, y, z);
    }

    public Quaternionf getRotate(GenericParticle particle, Camera renderInfo, float partialTicks) {
        Quaternionf quaternionf = new Quaternionf();
        if (camera) {
            float x = xRotCam ? renderInfo.getXRot() : 0;
            float y = yRotCam ? renderInfo.getYRot() : 0;
            quaternionf.rotationYXZ(-y * ((float)Math.PI / 180F), x * ((float)Math.PI / 180F), 0.0F);
        }

        ParticleBehaviorComponent component = particle.behaviorComponent;
        quaternionf.rotateX(Mth.lerp(partialTicks, component.xORoll, component.xRoll) + xOffset);
        quaternionf.rotateY(Mth.lerp(partialTicks, component.yORoll, component.yRoll) + yOffset);
        quaternionf.rotateZ(Mth.lerp(partialTicks, component.zORoll, component.zRoll) + zOffset);

        if (particle.roll != 0.0F) {
            quaternionf.rotateY(Mth.lerp(partialTicks, particle.oRoll, particle.roll));
        }

        return quaternionf;
    }

    public Vector3f[] getQuad(GenericParticle particle, Camera renderInfo, float partialTicks) {
        return new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
    }
}
