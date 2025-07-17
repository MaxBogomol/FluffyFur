package mod.maxbogomol.fluffy_fur.client.shader;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;

import java.util.HashMap;
import java.util.Map;

public class VertexAttributeHandler {
    public static Map<VertexFormatElement, VertexAttributeHolder> attributes = new HashMap<>();

    public static void register(VertexFormatElement vertexFormatElement, VertexAttributeHolder holder) {
        attributes.put(vertexFormatElement, holder);
    }

    public static Map<VertexFormatElement, VertexAttributeHolder> getAttributes() {
        return attributes;
    }

    public static VertexAttributeHolder getAttribute(VertexFormatElement vertexFormatElement) {
        return attributes.get(vertexFormatElement);
    }

    public static void setAttribute(VertexFormatElement vertexFormatElement, VertexAttributeHolder holder) {
        attributes.put(vertexFormatElement, holder);
    }

    public static void clearAll() {
        for (VertexAttributeHolder holder : getAttributes().values()) {
            holder.clear();
        }
    }

    public static void clear(VertexFormatElement vertexFormatElement) {
        VertexAttributeHolder holder = getAttribute(vertexFormatElement);
        if (holder != null) holder.clear();
    }

    public static void clear(VertexFormat vertexFormat) {
        ImmutableList<VertexFormatElement> elements = vertexFormat.getElements();
        for (VertexFormatElement element : elements) {
            VertexAttributeHolder holder = getAttribute(element);
            if (holder != null) holder.clear();
        }
    }
}
