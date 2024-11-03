package mod.maxbogomol.fluffy_fur.registry.common.block;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.common.world.SupplierBlockStateProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class FluffyFurBlockStateProviderTypes {
    public static final DeferredRegister<BlockStateProviderType<?>> BLOCK_STATE_PROVIDER_TYPES = DeferredRegister.createOptional(Registries.BLOCK_STATE_PROVIDER_TYPE, FluffyFur.MOD_ID);

    public static final RegistryObject<BlockStateProviderType<?>> BLOCK_STATE_PROVIDER = BLOCK_STATE_PROVIDER_TYPES.register("block_state_provider", () -> new BlockStateProviderType<>(SupplierBlockStateProvider.CODEC));

    public static void register(IEventBus eventBus) {
        BLOCK_STATE_PROVIDER_TYPES.register(eventBus);
    }
}
