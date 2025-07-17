package mod.maxbogomol.fluffy_fur.client.shader;

import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.world.phys.Vec2;

public class VertexAttributeHolder {

    public void accept(BufferBuilder bufferBuilder) {

    }

    public void clear() {

    }

    public static class Float extends VertexAttributeHolder {
        float standardValue;
        float value;

        public Float(float value) {
            this.standardValue = value;
            this.value = value;
        }

        public static Float create(float value) {
            return new Float(value);
        }

        public void setValue(float value) {
            this.value = value;
        }

        public void accept(BufferBuilder bufferBuilder) {
            bufferBuilder.putFloat(0, value);
            bufferBuilder.nextElement();
        }

        public void clear() {
            value = standardValue;
        }
    }

    public static class Coord extends VertexAttributeHolder {
        float standardX;
        float standardY;
        float x;
        float y;

        public Coord(float x, float y) {
            this.standardX = x;
            this.standardY = y;
            this.x = x;
            this.y = y;
        }

        public static Coord create(float x, float y) {
            return new Coord(x, y);
        }

        public void setValue(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public void setValue(Vec2 vec2) {
            this.x = vec2.x;
            this.y = vec2.y;
        }

        public void accept(BufferBuilder bufferBuilder) {
            bufferBuilder.putFloat(0, x);
            bufferBuilder.putFloat(4, y);
            bufferBuilder.nextElement();
        }

        public void clear() {
            this.x = standardX;
            this.y = standardY;
        }
    }

    public static class UV extends VertexAttributeHolder {
        float standardU0, standardV0, standardU1, standardV1;
        float u0, v0, u1, v1;

        public UV(float u0, float v0, float u1, float v1) {
            this.standardU0 = u0;
            this.standardV0 = v0;
            this.standardU1 = u1;
            this.standardV1 = v1;
            this.u0 = u0;
            this.v0 = v0;
            this.u1 = u1;
            this.v1 = v1;
        }

        public static UV create(float u0, float v0, float u1, float v1) {
            return new UV(u0, v0, u1, v1);
        }

        public void setValue(float u0, float v0, float u1, float v1) {
            this.u0 = u0;
            this.v0 = v0;
            this.u1 = u1;
            this.v1 = v1;
        }

        public void accept(BufferBuilder bufferBuilder) {
            bufferBuilder.putFloat(0, u0);
            bufferBuilder.putFloat(4, v0);
            bufferBuilder.putFloat(8, u1);
            bufferBuilder.putFloat(12, v1);
            bufferBuilder.nextElement();
        }

        public void clear() {
            this.u0 = standardU0;
            this.v0 = standardV0;
            this.u1 = standardU1;
            this.v1 = standardV1;
        }
    }
}
