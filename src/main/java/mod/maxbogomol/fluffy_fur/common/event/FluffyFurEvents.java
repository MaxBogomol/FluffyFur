package mod.maxbogomol.fluffy_fur.common.event;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.common.capability.IPlayerSkin;
import mod.maxbogomol.fluffy_fur.common.capability.PlayerSkinProvider;
import mod.maxbogomol.fluffy_fur.common.command.FluffyFurCommand;
import mod.maxbogomol.fluffy_fur.common.network.BloodPacket;
import mod.maxbogomol.fluffy_fur.common.network.FluffyFurPacketHandler;
import mod.maxbogomol.fluffy_fur.common.network.playerskin.PlayerSkinUpdatePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class FluffyFurEvents {

    @SubscribeEvent
    public void attachEntityCaps(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) event.addCapability(new ResourceLocation(FluffyFur.MOD_ID, "player_skin"), new PlayerSkinProvider());
    }

    @SubscribeEvent
    public void onClone(PlayerEvent.Clone event) {
        Capability<IPlayerSkin> SKIN = IPlayerSkin.INSTANCE;
        event.getOriginal().reviveCaps();
        event.getEntity().getCapability(SKIN).ifPresent((k) -> event.getOriginal().getCapability(SKIN).ifPresent((o) ->
                ((INBTSerializable<CompoundTag>) k).deserializeNBT(((INBTSerializable<CompoundTag>) o).serializeNBT())));
        if (!event.getEntity().level().isClientSide) {
            FluffyFurPacketHandler.sendTo((ServerPlayer) event.getEntity(), new PlayerSkinUpdatePacket(event.getEntity()));
        }
    }

    @SubscribeEvent
    public void onJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player player && !event.getLevel().isClientSide()) {
            FluffyFurPacketHandler.sendTo((ServerPlayer) event.getEntity(), new PlayerSkinUpdatePacket(player));
        }
    }

    @SubscribeEvent
    public void onPlayerStartTracking(PlayerEvent.StartTracking event) {
        Player player = event.getEntity();
        if (event.getTarget() instanceof Player target) {
            FluffyFurPacketHandler.sendTo(player, new PlayerSkinUpdatePacket(target));
            FluffyFurPacketHandler.sendTo(target, new PlayerSkinUpdatePacket(player));
        }
    }

    @SubscribeEvent
    public void onLivingDamage(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if (!level.isClientSide()) {
            Vec3 pos = entity.position().add(0, entity.getBbHeight() / 2f, 0);
            FluffyFurPacketHandler.sendToTracking(level, BlockPos.containing(pos), new BloodPacket(pos, event.getAmount()));
        }
    }

    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event) {
        FluffyFurCommand.register(event.getDispatcher());
    }
}
