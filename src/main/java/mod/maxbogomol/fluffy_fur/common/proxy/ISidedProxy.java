package mod.maxbogomol.fluffy_fur.common.proxy;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface ISidedProxy {
    Player getPlayer();
    Level getLevel();
}