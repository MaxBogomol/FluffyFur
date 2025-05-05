package mod.maxbogomol.fluffy_fur.common.item;

import mod.maxbogomol.fluffy_fur.common.playerskin.PlayerSkinHandler;
import mod.maxbogomol.fluffy_fur.common.network.FluffyFurPacketHandler;
import mod.maxbogomol.fluffy_fur.common.network.playerskin.PlayerSkinChangePacket;
import mod.maxbogomol.fluffy_fur.registry.common.FluffyFurPlayerSkins;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SillyTreatItem extends Item {

    public SillyTreatItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (level.isClientSide()) {
            if (livingEntity instanceof Player player) {
                if (PlayerSkinHandler.getSkin(player) != FluffyFurPlayerSkins.BOYKISSER_SKIN) {
                    PlayerSkinHandler.setSkinPacket(FluffyFurPlayerSkins.BOYKISSER_SKIN);
                } else {
                    PlayerSkinHandler.setSkinPacket(FluffyFurPlayerSkins.EMPTY_SKIN);
                }
                FluffyFurPacketHandler.sendToServer(new PlayerSkinChangePacket(player.position().add(0, player.getBbHeight(), 0)));
            }
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }
}
