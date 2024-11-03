package mod.maxbogomol.fluffy_fur.common.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.registry.common.block.FluffyFurBlockStateProviderTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

public class SupplierBlockStateProvider extends AbstractSupplierBlockStateProvider {

    public SupplierBlockStateProvider(String path) {
        this(new ResourceLocation(FluffyFur.MOD_ID, path));
    }

    public SupplierBlockStateProvider(ResourceLocation path) {
        super(path);
    }

    public static final Codec<SupplierBlockStateProvider> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING.fieldOf("key").forGetter(d -> d.key.getPath()))
            .apply(instance, SupplierBlockStateProvider::new));

    @Override
    protected BlockStateProviderType<?> type() {
        return FluffyFurBlockStateProviderTypes.BLOCK_STATE_PROVIDER.get();
    }
}