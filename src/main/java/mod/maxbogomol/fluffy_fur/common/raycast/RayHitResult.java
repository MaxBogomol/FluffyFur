package mod.maxbogomol.fluffy_fur.common.raycast;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class RayHitResult {
    public Vec3 pos;
    public boolean block;
    public List<Entity> entities;

    public RayHitResult(Vec3 pos, boolean block) {
        this.pos = pos;
        this.block = block;
    }

    public RayHitResult(Vec3 pos, boolean block, List<Entity> entities) {
        this.pos = pos;
        this.block = block;
        this.entities = entities;
    }

    public Vec3 getPos() {
        return pos;
    }

    public boolean hasBlock() {
        return block;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public boolean hasEntities() {
        return !entities.isEmpty();
    }
}
