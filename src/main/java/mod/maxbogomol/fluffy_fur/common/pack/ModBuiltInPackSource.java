package mod.maxbogomol.fluffy_fur.common.pack;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public record ModBuiltInPackSource(String packId, PackType packType, Pack.Position packPosition, PackSource packSource, PackResources packResources) implements RepositorySource {
    @Override
    public void loadPacks(@NotNull Consumer<Pack> onLoad) {
        onLoad.accept(Pack.readMetaAndCreate(packId, Component.literal(packId), true, id -> packResources, packType, packPosition, packSource));
    }
}
