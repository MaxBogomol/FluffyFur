package mod.maxbogomol.fluffy_fur.common.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;

public class FluffyFurCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("fluffy_fur")
                .requires(s -> s.hasPermission(2))
                .then(Commands.literal("skin")
                        .then(Commands.literal("item")
                            .then(Commands.literal("set")
                                    .then(Commands.argument("player", EntityArgument.players())
                                            .then(Commands.argument("skin", ItemSkinArgumentType.skins())
                                                .executes(ctx -> setItemSkin(ctx, EntityArgument.getPlayers(ctx, "player"), ItemSkinArgumentType.getSkin(ctx, "skin")))
                                            )
                                    )
                            )
                            .then(Commands.literal("remove")
                                    .then(Commands.argument("player", EntityArgument.players())
                                            .executes(ctx -> removeItemSkin(ctx, EntityArgument.getPlayers(ctx, "player")))
                                    )
                            )
                        )
                )
        );
    }

    private static int setItemSkin(CommandContext<CommandSourceStack> command, Collection<ServerPlayer> targetPlayers, ItemSkin itemSkin) throws CommandSyntaxException {
        int players = 0;

        for (ServerPlayer player : targetPlayers) {
            ItemStack stack = player.getMainHandItem();
            if (!stack.isEmpty()) {
                if (itemSkin.canApplyOnItem(stack)) {
                    itemSkin.applyOnItem(stack);
                    players++;
                }
            }
        }

        if (players == 0) {
            command.getSource().sendFailure(Component.translatable("commands.fluffy_fur.skin.item.set.failed", Component.translatable(itemSkin.getTranslatedName())));
        } else if (players == 1) {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.fluffy_fur.skin.item.set.single", Component.translatable(itemSkin.getTranslatedName()), targetPlayers.iterator().next().getMainHandItem().getDisplayName());
            }, true);
        } else {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.fluffy_fur.skin.item.set.multiple", Component.translatable(itemSkin.getTranslatedName()), targetPlayers.size());
            }, true);
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int removeItemSkin(CommandContext<CommandSourceStack> command, Collection<ServerPlayer> targetPlayers) throws CommandSyntaxException {
        int players = 0;

        for (ServerPlayer player : targetPlayers) {
            ItemStack stack = player.getMainHandItem();
            if (!stack.isEmpty()) {
                ItemSkin itemSkin = ItemSkin.getSkinFromItem(stack);
                if (itemSkin != null) {
                    CompoundTag nbt = stack.getOrCreateTag();
                    nbt.remove("skin");
                    players++;
                }
            }
        }

        if (players == 0) {
            command.getSource().sendFailure(Component.translatable("commands.fluffy_fur.skin.item.remove.failed"));
        } else if (players == 1) {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.fluffy_fur.skin.item.remove.single", targetPlayers.iterator().next().getMainHandItem().getDisplayName());
            }, true);
        } else {
            command.getSource().sendSuccess(() -> {
                return Component.translatable("commands.fluffy_fur.skin.item.remove.multiple", targetPlayers.size());
            }, true);
        }
        return Command.SINGLE_SUCCESS;
    }
}