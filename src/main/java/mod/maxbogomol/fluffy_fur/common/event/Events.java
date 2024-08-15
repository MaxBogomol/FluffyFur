package mod.maxbogomol.fluffy_fur.common.event;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Events {
    @SubscribeEvent
    public void attachEntityCaps(AttachCapabilitiesEvent<Entity> event) {

    }

    @SubscribeEvent
    public void onClone(PlayerEvent.Clone event) {

    }

    @SubscribeEvent
    public void registerCustomAI(EntityJoinLevelEvent event) {

    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {

    }

    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event) {

    }

    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent event) {

    }

    @SubscribeEvent
    public void addCustomWandererTrades(WandererTradesEvent event) {

    }
}
