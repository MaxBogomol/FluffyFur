package mod.maxbogomol.fluffy_fur.common.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkinHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

public class ItemSkinArgumentType implements ArgumentType<ItemSkin> {
    private static final DynamicCommandExceptionType UNKNOWN = new DynamicCommandExceptionType((obj) -> Component.translatable("commands.fluffy_fur.item_skin.unknown", obj));

    public static ItemSkin getSkin(final CommandContext<?> context, final String name) {
        return context.getArgument(name, ItemSkin.class);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
        for (ItemSkin itemSkin : ItemSkinHandler.getSkins()) {
            if (itemSkin.getId().startsWith(builder.getRemainingLowerCase())) builder.suggest(itemSkin.getId());
        }
        return builder.buildFuture();
    }

    @Override
    public ItemSkin parse(StringReader reader) throws CommandSyntaxException {
        ResourceLocation resourceLocation = ResourceLocation.read(reader);
        ItemSkin itemSkin = ItemSkinHandler.getSkin(resourceLocation.toString());
        if (itemSkin == null) throw UNKNOWN.create(resourceLocation.toString());
        return itemSkin;
    }

    public static ItemSkinArgumentType skins() {
        return new ItemSkinArgumentType();
    }
}