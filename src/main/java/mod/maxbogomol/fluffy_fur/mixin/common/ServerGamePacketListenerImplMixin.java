package mod.maxbogomol.fluffy_fur.mixin.common;

import mod.maxbogomol.fluffy_fur.config.FluffyFurServerConfig;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerImplMixin {

    @Final
    @Shadow
    private MinecraftServer server;

    @Shadow
    public ServerPlayer player;

    @Inject(method = "handleClientCommand", at = @At("RETURN"))
    private void fluffy_fur$use(ServerboundClientCommandPacket packet, CallbackInfo ci) {
        if (FluffyFurServerConfig.HARDCORE_SPECTATORS_GENERATE_CHUNKS.get()) {
            ServerboundClientCommandPacket.Action serverboundclientcommandpacket$action = packet.getAction();
            if (this.server.isHardcore() && (!this.server.isDedicatedServer() || FluffyFurServerConfig.HARDCORE_SPECTATORS_GENERATE_CHUNKS_DEDICATED.get())) {
                if (serverboundclientcommandpacket$action == ServerboundClientCommandPacket.Action.PERFORM_RESPAWN) {
                    this.player.level().getGameRules().getRule(GameRules.RULE_SPECTATORSGENERATECHUNKS).set(true, this.server);
                }
            }
        }
    }
}
