package mod.maxbogomol.fluffy_fur.client.shader.postprocess;

import com.mojang.blaze3d.preprocessor.GlslPreprocessor;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FluffyFurGlslPreprocessor extends GlslPreprocessor {

    public static final FluffyFurGlslPreprocessor PREPROCESSOR = new FluffyFurGlslPreprocessor();

    @Nullable
    @Override
    public String applyImport(boolean useFullPath, String directory) {
        ResourceLocation resourcelocation = new ResourceLocation(directory);
        ResourceLocation resourcelocation1 = new ResourceLocation(resourcelocation.getNamespace(), "shaders/include/" + resourcelocation.getPath());
        try {
            Resource resource1 = Minecraft.getInstance().getResourceManager().getResource(resourcelocation1).get();
            return IOUtils.toString(resource1.open(), StandardCharsets.UTF_8);
        } catch (IOException ioexception) {
            FluffyFur.LOGGER.error("Could not open GLSL import {}: {}", directory, ioexception.getMessage());
            return "#error " + ioexception.getMessage();
        }
    }
}
