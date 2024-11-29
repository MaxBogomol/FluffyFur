package mod.maxbogomol.fluffy_fur.client.shader.postprocess;

import net.minecraft.client.renderer.EffectInstance;

import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.GL_R32F;
import static org.lwjgl.opengl.GL31.GL_TEXTURE_BUFFER;
import static org.lwjgl.opengl.GL31.glTexBuffer;

public class ShaderDataBuffer {
    private int tbo = 0;
    private int tex = 0;

    public void generate(long size) {
        destroy();

        tbo = glGenBuffers();
        glBindBuffer(GL_TEXTURE_BUFFER, tbo);
        glBufferData(GL_TEXTURE_BUFFER, size * 4, GL_STATIC_DRAW);

        tex = glGenTextures();
        glBindTexture(GL_TEXTURE_BUFFER, tex);
        glTexBuffer(GL_TEXTURE_BUFFER, GL_R32F, tbo);

        glBindBuffer(GL_TEXTURE_BUFFER, 0);
        glBindTexture(GL_TEXTURE_BUFFER, 0);
    }

    public void destroy() {
        if (tbo != 0)
            glDeleteBuffers(tbo);
        if (tex != 0)
            glDeleteTextures(tex);
        tbo = 0;
        tex = 0;
    }

    public void upload(float[] data) {
        glBindBuffer(GL_TEXTURE_BUFFER, tbo);
        glBufferSubData(GL_TEXTURE_BUFFER, 0, data);
        glBindBuffer(GL_TEXTURE_BUFFER, 0);
    }

    public void apply(EffectInstance effect, String uniform) {
        glBindBuffer(GL_TEXTURE_BUFFER, tbo);
        int unit = effect.samplerMap.size();
        glActiveTexture(GL_TEXTURE0 + unit);
        glBindTexture(GL_TEXTURE_BUFFER, tex);

        effect.safeGetUniform(uniform).set(unit);
        glActiveTexture(GL_TEXTURE0);
    }
}
