package mod.maxbogomol.fluffy_fur.common.playerskin;

import net.minecraft.world.entity.player.Player;

import java.util.Random;

public class PlayerSkinEffect {
    public String id;

    public static Random random = new Random();

    public PlayerSkinEffect(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void tick(Player player) {

    }
}
