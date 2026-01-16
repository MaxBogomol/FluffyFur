package mod.maxbogomol.fluffy_fur.common.network;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.common.network.block.PlushHeartsPacket;
import mod.maxbogomol.fluffy_fur.common.network.item.StopUseItemPacket;
import mod.maxbogomol.fluffy_fur.common.network.item.TestShrimpPacket;
import mod.maxbogomol.fluffy_fur.common.network.playerskin.*;
import mod.maxbogomol.fluffy_fur.common.network.spectator.*;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class FluffyFurPacketHandler extends PacketHandler {
    public static final String PROTOCOL = "10";
    public static final SimpleChannel HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(FluffyFur.MOD_ID,"network"), () -> PROTOCOL, PROTOCOL::equals, PROTOCOL::equals);

    public static void init() {
        int id = 0;
        PlayerSkinUpdatePacket.register(HANDLER, id++);
        PlayerSkinSetPacket.register(HANDLER, id++);
        PlayerSkinSetEffectPacket.register(HANDLER, id++);
        PlayerSkinSetCapePacket.register(HANDLER, id++);
        PlayerSkinChangeEffectPacket.register(HANDLER, id++);
        PlayerSkinChangePacket.register(HANDLER, id++);
        PlushHeartsPacket.register(HANDLER, id++);
        BloodPacket.register(HANDLER, id++);
        TestShrimpPacket.register(HANDLER, id++);
        StopUseItemPacket.register(HANDLER, id++);

        TeleportToSpawnPointPacket.register(HANDLER, id++);
        TeleportToDimensionSpawnPointPacket.register(HANDLER, id++);
        TeleportToDimensionPacket.register(HANDLER, id++);
        SpectatorClientDimensionsPacket.register(HANDLER, id++);
        SpectatorServerDimensionsPacket.register(HANDLER, id++);
    }

    public static SimpleChannel getHandler() {
        return HANDLER;
    }

    public static void sendTo(ServerPlayer playerMP, Object toSend) {
        sendTo(getHandler(), playerMP, toSend);
    }

    public static void sendNonLocal(ServerPlayer playerMP, Object toSend) {
        sendNonLocal(getHandler(), playerMP, toSend);
    }

    public static void sendToTracking(Level level, BlockPos pos, Object msg) {
        sendToTracking(getHandler(), level, pos, msg);
    }

    public static void sendTo(Player entity, Object msg) {
        sendTo(getHandler(), entity, msg);
    }

    public static void sendToServer(Object msg) {
        sendToServer(getHandler(), msg);
    }
}