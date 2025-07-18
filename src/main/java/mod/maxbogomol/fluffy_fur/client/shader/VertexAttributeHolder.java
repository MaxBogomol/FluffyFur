package mod.maxbogomol.fluffy_fur.client.shader;

import com.mojang.blaze3d.vertex.BufferBuilder;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class VertexAttributeHolder {

    public void accept(BufferBuilder bufferBuilder, RenderBuilder renderBuilder) {

    }

    public void clear() {

    }

    public static class Byte extends VertexAttributeHolder {
        byte standardValue;
        byte value;

        public Byte(byte value) {
            this.standardValue = value;
            this.value = value;
        }

        public static Byte create(byte value) {
            return new Byte(value);
        }

        public void setValue(byte value) {
            this.value = value;
        }

        public void accept(BufferBuilder bufferBuilder, RenderBuilder builder) {
            bufferBuilder.putByte(0, value);
            bufferBuilder.nextElement();
        }

        public void clear() {
            value = standardValue;
        }
    }

    public static class Short extends VertexAttributeHolder {
        short standardValue;
        short value;

        public Short(short value) {
            this.standardValue = value;
            this.value = value;
        }

        public static Short create(short value) {
            return new Short(value);
        }

        public void setValue(short value) {
            this.value = value;
        }

        public void accept(BufferBuilder bufferBuilder, RenderBuilder builder) {
            bufferBuilder.putShort(0, value);
            bufferBuilder.nextElement();
        }

        public void clear() {
            value = standardValue;
        }
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

        public void accept(BufferBuilder bufferBuilder, RenderBuilder builder) {
            bufferBuilder.putFloat(0, value);
            bufferBuilder.nextElement();
        }

        public void clear() {
            value = standardValue;
        }
    }

    public static class Boolean extends VertexAttributeHolder {
        boolean standardValue;
        boolean value;

        public Boolean(boolean value) {
            this.standardValue = value;
            this.value = value;
        }

        public static Boolean create(boolean value) {
            return new Boolean(value);
        }

        public void setValue(boolean value) {
            this.value = value;
        }

        public void accept(BufferBuilder bufferBuilder, RenderBuilder builder) {
            bufferBuilder.putFloat(0, value ? 1f : 0f);
            bufferBuilder.nextElement();
        }

        public void clear() {
            value = standardValue;
        }
    }

    public static class PosUV extends VertexAttributeHolder {
        float standardX, standardY;
        float x, y;

        public PosUV(float x, float y) {
            this.standardX = x;
            this.standardY = y;
            this.x = x;
            this.y = y;
        }

        public static PosUV create(float x, float y) {
            return new PosUV(x, y);
        }

        public void setValue(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public void setValue(Vec2 vec2) {
            this.x = vec2.x;
            this.y = vec2.y;
        }

        public void accept(BufferBuilder bufferBuilder, RenderBuilder renderBuilder) {
            bufferBuilder.putFloat(0, x);
            bufferBuilder.putFloat(4, y);
            bufferBuilder.nextElement();
        }

        public void clear() {
            this.x = standardX;
            this.y = standardY;
        }
    }

    public static class Pos extends VertexAttributeHolder {
        float standardX, standardY, standardZ;
        float x, y, z;

        public Pos(float x, float y, float z) {
            this.standardX = x;
            this.standardY = y;
            this.standardZ = z;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public static Pos create(float x, float y, float z) {
            return new Pos(x, y, z);
        }

        public void setValue(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public void setValue(Vec3 vec3) {
            this.x = (float) vec3.x;
            this.y = (float) vec3.y;
            this.z = (float) vec3.z;
        }

        public void accept(BufferBuilder bufferBuilder, RenderBuilder renderBuilder) {
            bufferBuilder.putFloat(0, x);
            bufferBuilder.putFloat(4, y);
            bufferBuilder.putFloat(8, z);
            bufferBuilder.nextElement();
        }

        public void clear() {
            this.x = standardX;
            this.y = standardY;
            this.z = standardZ;
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

        public void accept(BufferBuilder bufferBuilder, RenderBuilder renderBuilder) {
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
