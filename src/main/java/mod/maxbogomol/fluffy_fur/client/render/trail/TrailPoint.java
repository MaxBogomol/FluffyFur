package mod.maxbogomol.fluffy_fur.client.render.trail;

import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;

public class TrailPoint {
    public Vec3 position;
    public int timeActive;

    public TrailPoint(Vec3 position, int timeActive) {
        this.position = position;
        this.timeActive = timeActive;
    }

    public TrailPoint(Vec3 position) {
        this(position, 0);
    }

    public Vector4f getMatrixPosition() {
        Vec3 position = getPosition();
        return new Vector4f((float) position.x, (float) position.y, (float) position.z, 1.0f);
    }

    public Vec3 getPosition() {
        return position;
    }

    public int getTimeActive() {
        return timeActive;
    }

    public TrailPoint lerp(TrailPoint trailPoint, float delta) {
        Vec3 position = getPosition();
        return new TrailPoint(position.lerp(trailPoint.position, delta), timeActive);
    }

    public void tick() {
        timeActive++;
    }

    public void setPosition(Vec3 position) {
        this.position = position;
    }

    public void setTimeActive(int timeActive) {
        this.timeActive = timeActive;
    }
}
