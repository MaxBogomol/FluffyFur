package mod.maxbogomol.fluffy_fur.registry.common;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.common.block.sign.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FluffyFurBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, FluffyFur.MOD_ID);

    public static final RegistryObject<BlockEntityType<CustomSignBlockEntity>> SIGN = BLOCK_ENTITIES.register("sign", () -> BlockEntityType.Builder.of(CustomSignBlockEntity::new, FluffyFurBlocks.getBlocks(CustomStandingSignBlock.class, CustomWallSignBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<CustomHangingSignBlockEntity>> HANGING_SIGN = BLOCK_ENTITIES.register("hanging_sign", () -> BlockEntityType.Builder.of(CustomHangingSignBlockEntity::new, FluffyFurBlocks.getBlocks(CustomHangingSignBlockEntity.class, CustomWallHangingSignBlock.class)).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

    @Mod.EventBusSubscriber(modid = FluffyFur.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            BlockEntityRenderers.register(FluffyFurBlockEntities.SIGN.get(), SignRenderer::new);
            BlockEntityRenderers.register(FluffyFurBlockEntities.HANGING_SIGN.get(), HangingSignRenderer::new);
        }
    }
}
