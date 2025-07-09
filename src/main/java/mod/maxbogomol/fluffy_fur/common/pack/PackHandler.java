package mod.maxbogomol.fluffy_fur.common.pack;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.locating.IModFile;

public class PackHandler {

    public static void addPack(AddPackFindersEvent event, String modId, String id, Component name, String path, PackType packType, Pack.Position packPosition, PackSource packSource) {
        IModFileInfo modFileInfo = ModList.get().getModFileById(modId);
        IModFile modFile = modFileInfo.getFile();
        Pack pack = Pack.readMetaAndCreate(id, name, false, i -> new ModBuiltInPackResources(i, modFile, path), packType, packPosition, packSource);
        if (pack != null) {
            event.addRepositorySource(new ModBuiltInPackSource(id, packType, packPosition, packSource, pack.open()));
        }
    }
}
