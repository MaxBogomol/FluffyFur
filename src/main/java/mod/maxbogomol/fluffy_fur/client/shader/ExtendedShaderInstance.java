package mod.maxbogomol.fluffy_fur.client.shader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ChainedJsonException;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.util.GsonHelper;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;
import java.util.function.Consumer;

public class ExtendedShaderInstance extends ShaderInstance {

    public Map<String, Consumer<Uniform>> defaultUniformData;
    public Collection<String> uniformsToCache = new ArrayList<>();

    public ExtendedShaderInstance(ResourceProvider resourceProvider, ResourceLocation location, VertexFormat vertexFormat, String... uniformsToCache) throws IOException {
        super(resourceProvider, location, vertexFormat);
        this.uniformsToCache = new ArrayList<>(List.of(uniformsToCache));
    }

    public ExtendedShaderInstance(ResourceProvider resourceProvider, ResourceLocation location, VertexFormat vertexFormat) throws IOException {
        super(resourceProvider, location, vertexFormat);
    }

    public ExtendedShaderInstance setDefaultUniform(String uniform) {
        this.uniformsToCache.add(uniform);
        return this;
    }

    public void setUniformDefaults() {
        for (Map.Entry<String, Consumer<Uniform>> defaultDataEntry : getDefaultUniformData().entrySet()) {
            final Uniform t = uniformMap.get(defaultDataEntry.getKey());
            defaultDataEntry.getValue().accept(t);
            float f = 0;
        }
    }

    public Map<String, Consumer<Uniform>> getDefaultUniformData() {
        if (defaultUniformData == null) {
            defaultUniformData = new HashMap<>();
        }
        return defaultUniformData;
    }

    @Override
    public void parseUniformNode(JsonElement json) throws ChainedJsonException {
        super.parseUniformNode(json);

        JsonObject jsonobject = GsonHelper.convertToJsonObject(json, "uniform");
        String uniformName = GsonHelper.getAsString(jsonobject, "name");
        if (uniformsToCache.contains(uniformName)) {
            Uniform uniform = uniforms.get(uniforms.size() - 1);

            Consumer<Uniform> consumer;
            if (uniform.getType() <= 3) {
                final IntBuffer buffer = uniform.getIntBuffer();
                buffer.position(0);
                int[] array = new int[uniform.getCount()];
                for (int i = 0; i < uniform.getCount(); i++) {
                    array[i] = buffer.get(i);
                }
                consumer = u -> {
                    buffer.position(0);
                    buffer.put(array);
                };
            } else {
                final FloatBuffer buffer = uniform.getFloatBuffer();
                buffer.position(0);
                float[] array = new float[uniform.getCount()];
                for (int i = 0; i < uniform.getCount(); i++) {
                    array[i] = buffer.get(i);
                }
                consumer = u -> {
                    buffer.position(0);
                    buffer.put(array);
                };
            }

            getDefaultUniformData().put(uniformName, consumer);
        }
    }
}
