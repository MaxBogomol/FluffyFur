package mod.maxbogomol.fluffy_fur.common.playerskin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class PlayerSkinCape {
    public String id;

    public ResourceLocation texture;

    public PlayerSkinCape(String id) {
        this.id = id;
    }

    public PlayerSkinCape setTexture(ResourceLocation texture) {
        this.texture = texture;
        return this;
    }

    public ResourceLocation getSkin(Player player) {
        return texture;
    }

    public String getId() {
        return id;
    }

    public static ResourceLocation getCapeLocation(String mod, String texture) {
        return new ResourceLocation(mod, "textures/entity/cape/" + texture + ".png");
    }
}
