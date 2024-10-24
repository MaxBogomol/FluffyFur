package mod.maxbogomol.fluffy_fur.common.event;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.common.capability.IPlayerSkin;
import mod.maxbogomol.fluffy_fur.common.capability.PlayerSkinProvider;
import mod.maxbogomol.fluffy_fur.common.network.FluffyFurPacketHandler;
import mod.maxbogomol.fluffy_fur.common.network.playerskin.PlayerSkinUpdatePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class Events {

    public List<Player> playerSkinUpdate = new ArrayList<>();

    @SubscribeEvent
    public void attachEntityCaps(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) event.addCapability(new ResourceLocation(FluffyFur.MOD_ID, "player_skin"), new PlayerSkinProvider());
    }

    @SubscribeEvent
    public void onClone(PlayerEvent.Clone event) {
        Capability<IPlayerSkin> KNOWLEDGE = IPlayerSkin.INSTANCE;
        event.getOriginal().reviveCaps();
        event.getEntity().getCapability(KNOWLEDGE).ifPresent((k) -> event.getOriginal().getCapability(KNOWLEDGE).ifPresent((o) ->
                ((INBTSerializable<CompoundTag>) k).deserializeNBT(((INBTSerializable<CompoundTag>) o).serializeNBT())));
        if (!event.getEntity().level().isClientSide) {
            FluffyFurPacketHandler.sendTo((ServerPlayer) event.getEntity(), new PlayerSkinUpdatePacket(event.getEntity()));
        }
    }

    @SubscribeEvent
    public void onJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player player && !event.getLevel().isClientSide()) {
            FluffyFurPacketHandler.sendTo((ServerPlayer) event.getEntity(), new PlayerSkinUpdatePacket(player));
            playerSkinUpdate.add(player);
        }
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        if (!event.player.level().isClientSide) {
            for (Player player : playerSkinUpdate) {
                for (ServerPlayer serverPlayer : player.getServer().getPlayerList().getPlayers()) {
                    FluffyFurPacketHandler.sendTo(serverPlayer, new PlayerSkinUpdatePacket(player));
                    if (player != serverPlayer) {
                        FluffyFurPacketHandler.sendTo(player, new PlayerSkinUpdatePacket(serverPlayer));
                    }
                }
            }
            playerSkinUpdate.clear();
        }
    }
}
