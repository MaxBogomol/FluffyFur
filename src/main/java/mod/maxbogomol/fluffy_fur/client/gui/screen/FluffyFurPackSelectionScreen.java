package mod.maxbogomol.fluffy_fur.client.gui.screen;

import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.mojang.blaze3d.platform.NativeImage;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.packs.PackSelectionModel;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.mutable.MutableBoolean;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FluffyFurPackSelectionScreen extends Screen {
    public static final Component DRAG_AND_DROP = Component.translatable("pack.dropInfo").withStyle(ChatFormatting.GRAY);
    public static final Component DIRECTORY_BUTTON_TOOLTIP = Component.translatable("pack.folderInfo");
    public static final Component TITLE = Component.translatable("dataPack.title");
    public static final ResourceLocation DEFAULT_ICON = new ResourceLocation("textures/misc/unknown_pack.png");

    public Screen lastScreen;
    public Component titleName;
    public PackSelectionModel model;
    public Path packDir;
    public Map<String, ResourceLocation> packIcons = Maps.newHashMap();
    @Nullable
    public Watcher watcher;
    public long ticksToReload;
    List<PackSelectionModel.Entry> allEntries = new ArrayList<>();
    List<PackSelectionModel.Entry> selectedEntries = new ArrayList<>();
    List<PackSelectionModel.Entry> unselectedEntries = new ArrayList<>();

    public FluffyFurPackSelectionScreen(Screen lastScreen) {
        super(Component.empty());
        this.lastScreen = lastScreen;
        this.titleName = TITLE;

        if (lastScreen instanceof PackSelectionScreen packSelectionScreen) {
            model = packSelectionScreen.model;
            packDir = packSelectionScreen.packDir;
            titleName = lastScreen.getTitle();
            watcher = Watcher.create(packDir);
        }
    }

    public FluffyFurPackSelectionScreen setTitleName(Component titleName) {
        this.titleName = titleName;
        return this;
    }

    public FluffyFurPackSelectionScreen setPackSelectionModel(PackRepository packRepository, Consumer<PackRepository> output) {
        this.model = new PackSelectionModel(this::populateLists, this::getPackIcon, packRepository, output);
        return this;
    }

    public FluffyFurPackSelectionScreen setPackSelectionModel(PackSelectionModel model) {
        this.model = model;
        return this;
    }

    public FluffyFurPackSelectionScreen setPath(Path packDir) {
        this.packDir = packDir;
        this.watcher = Watcher.create(packDir);
        return this;
    }

    @Override
    public void init() {
        addRenderableWidget(Button.builder(Component.translatable("pack.openFolder"), (button) -> {
            Util.getPlatform().openUri(packDir.toUri());
        }).bounds(width / 2 - 204, height - 24, 200, 20).tooltip(Tooltip.create(DIRECTORY_BUTTON_TOOLTIP)).build());

        addRenderableWidget(Button.builder(CommonComponents.GUI_BACK, (button) -> {
            onClose();
        }).bounds(width / 2 + 4, height - 24, 98, 20).build());

        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (button) -> {
            model.commit();
            onClose();
        }).bounds(width / 2 + 106, height - 24, 98, 20).build());

        reload();
    }

    @Override
    public void tick() {
        if (watcher != null) {
            try {
                if (watcher.pollForChanges()) {
                    ticksToReload = 20L;
                }
            } catch (IOException ioexception) {
                FluffyFur.LOGGER.warn("Failed to poll for directory {} changes, stopping", packDir);
                closeWatcher();
            }
        }

        if (ticksToReload > 0L && --ticksToReload == 0L) {
            reload();
        }
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        renderBackground(gui);

        super.render(gui, mouseX, mouseY, partialTicks);

        gui.drawCenteredString(font, titleName, width / 2, 4, 16777215);
        gui.drawCenteredString(font, DRAG_AND_DROP, width / 2, 16, 16777215);
        drawPackList(gui, width / 2 - 204, 30, mouseX, mouseY, partialTicks);
    }

    public void drawPackList(GuiGraphics gui, int x, int y, int mouseX, int mouseY, float partialTicks) {
        int offset = 0;

        for (PackSelectionModel.Entry entry : allEntries) {
            gui.blit(entry.getIconTexture(), x + 2, y + 2 + offset, 0, 0, 32, 32, 32, 32);
            gui.drawString(font, entry.getTitle(), x + 36, y + 3 + offset, 16777215);
            Component description = Component.empty().append(entry.getExtendedDescription()).withStyle(Style.EMPTY.withColor(8421504));
            int ii = offset + 36;
            offset += (font.lineHeight + 1);
            for(FormattedCharSequence formattedCharSequence : font.split(description, 368)) {
                gui.drawString(font, formattedCharSequence, x + 36, y + 3 + offset, 8421504);
                offset += (font.lineHeight + 1);
            }
            if (offset < ii) {
                offset = ii;
            } else {
                offset += 4;
            }
        }
    }

    @Override
    public void onFilesDrop(List<Path> packs) {
        String s = packs.stream().map(Path::getFileName).map(Path::toString).collect(Collectors.joining(", "));
        minecraft.setScreen(new ConfirmScreen((confirm) -> {
            if (confirm) {
                copyPacks(minecraft, packs, packDir);
                reload();
            }

            minecraft.setScreen(this);
        }, Component.translatable("pack.dropConfirm"), Component.literal(s)));
    }

    public void populateLists() {
        selectedEntries.clear();
        unselectedEntries.clear();
        allEntries.clear();
        selectedEntries.addAll(model.getSelected().toList());
        unselectedEntries.addAll(model.getUnselected().toList());
        allEntries.addAll(selectedEntries);
        allEntries.addAll(unselectedEntries);
    }

    public void reload() {
        model.findNewPacks();
        populateLists();
        ticksToReload = 0L;
        packIcons.clear();
    }

    public static void copyPacks(Minecraft minecraft, List<Path> packs, Path outDir) {
        MutableBoolean mutableBoolean = new MutableBoolean();
        packs.forEach((pack) -> {
            try (Stream<Path> stream = Files.walk(pack)) {
                stream.forEach((path) -> {
                    try {
                        Util.copyBetweenDirs(pack.getParent(), outDir, path);
                    } catch (IOException ioexception1) {
                        FluffyFur.LOGGER.warn("Failed to copy datapack file  from {} to {}", path, outDir, ioexception1);
                        mutableBoolean.setTrue();
                    }

                });
            } catch (IOException ioexception) {
                FluffyFur.LOGGER.warn("Failed to copy datapack file from {} to {}", pack, outDir);
                mutableBoolean.setTrue();
            }

        });
        if (mutableBoolean.isTrue()) {
            SystemToast.onPackCopyFailure(minecraft, outDir.toString());
        }
    }

    public ResourceLocation loadPackIcon(TextureManager textureManager, Pack pack) {
        try (PackResources packResources = pack.open()) {
            IoSupplier<InputStream> ioSupplier = packResources.getRootResource("pack.png");
            if (ioSupplier == null) {
                return DEFAULT_ICON;
            } else {
                String s = pack.getId();
                ResourceLocation resourceLocation = new ResourceLocation("minecraft", "pack/" + Util.sanitizeName(s, ResourceLocation::validPathChar) + "/" + Hashing.sha1().hashUnencodedChars(s) + "/icon");

                try (InputStream inputStream = ioSupplier.get()) {
                    NativeImage nativeImage = NativeImage.read(inputStream);
                    textureManager.register(resourceLocation, new DynamicTexture(nativeImage));
                    return resourceLocation;
                }
            }
        } catch (Exception exception) {
            FluffyFur.LOGGER.warn("Failed to load icon from pack {}", pack.getId(), exception);
            return DEFAULT_ICON;
        }
    }

    public ResourceLocation getPackIcon(Pack pack) {
        return this.packIcons.computeIfAbsent(pack.getId(), (location) -> {
            return this.loadPackIcon(minecraft.getTextureManager(), pack);
        });
    }

    private void closeWatcher() {
        if (this.watcher != null) {
            try {
                this.watcher.close();
                this.watcher = null;
            } catch (Exception exception) {}
        }

    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(lastScreen);
        closeWatcher();
    }

    @OnlyIn(Dist.CLIENT)
    public static class Watcher implements AutoCloseable {
        private final WatchService watcher;
        private final Path packPath;

        public Watcher(Path packPath) throws IOException {
            this.packPath = packPath;
            this.watcher = packPath.getFileSystem().newWatchService();

            try {
                this.watchDir(packPath);

                try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(packPath)) {
                    for(Path path : directoryStream) {
                        if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
                            this.watchDir(path);
                        }
                    }
                }
            } catch (Exception exception) {
                this.watcher.close();
                throw exception;
            }
        }

        @Nullable
        public static Watcher create(Path packPath) {
            try {
                return new Watcher(packPath);
            } catch (IOException ioexception) {
                FluffyFur.LOGGER.warn("Failed to initialize pack directory {} monitoring", packPath, ioexception);
                return null;
            }
        }

        private void watchDir(Path path) throws IOException {
            path.register(this.watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
        }

        public boolean pollForChanges() throws IOException {
            boolean flag = false;

            WatchKey watchkey;
            while((watchkey = this.watcher.poll()) != null) {
                for(WatchEvent<?> watchevent : watchkey.pollEvents()) {
                    flag = true;
                    if (watchkey.watchable() == this.packPath && watchevent.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                        Path path = this.packPath.resolve((Path)watchevent.context());
                        if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
                            this.watchDir(path);
                        }
                    }
                }

                watchkey.reset();
            }

            return flag;
        }

        public void close() throws IOException {
            this.watcher.close();
        }
    }
}
