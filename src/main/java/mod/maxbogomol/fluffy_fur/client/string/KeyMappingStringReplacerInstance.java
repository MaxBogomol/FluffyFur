package mod.maxbogomol.fluffy_fur.client.string;

import net.minecraft.client.KeyMapping;

public class KeyMappingStringReplacerInstance extends StringReplacerInstance {
    public KeyMapping keyMapping;

    public KeyMappingStringReplacerInstance(String id, KeyMapping keyMapping) {
        super(id);
        this.keyMapping = keyMapping;
    }

    public String getReplaceString() {
        return keyMapping.getTranslatedKeyMessage().getString();
    }
}
