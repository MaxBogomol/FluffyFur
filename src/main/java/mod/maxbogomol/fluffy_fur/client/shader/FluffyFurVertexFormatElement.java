package mod.maxbogomol.fluffy_fur.client.shader;

import com.mojang.blaze3d.vertex.VertexFormatElement;

import java.util.concurrent.atomic.AtomicInteger;

public class FluffyFurVertexFormatElement extends VertexFormatElement {

    public FluffyFurVertexFormatElement(Type type, Usage usage, int count) {
        super(Sequence.nextValue(), type, usage, count);
    }

    public FluffyFurVertexFormatElement(Type type, int count) {
        this(type, Usage.GENERIC, count);
    }

    @Override
    public boolean supportsUsage(int index, Usage usage) {
        return true;
    }

    public static class Sequence {

        private static final AtomicInteger counter = new AtomicInteger();

        public static int nextValue() {
            return counter.getAndIncrement();
        }
    }
}
