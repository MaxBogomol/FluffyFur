package mod.maxbogomol.fluffy_fur;

import com.mojang.serialization.Codec;
import mod.maxbogomol.fluffy_fur.client.config.ClientConfig;
import mod.maxbogomol.fluffy_fur.client.event.ClientEvents;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.event.ClientWorldEvent;
import mod.maxbogomol.fluffy_fur.client.gui.TooltipEventHandler;
import mod.maxbogomol.fluffy_fur.client.particle.*;
import mod.maxbogomol.fluffy_fur.client.render.WorldRenderHandler;
import mod.maxbogomol.fluffy_fur.common.block.sign.*;
import mod.maxbogomol.fluffy_fur.common.config.Config;
import mod.maxbogomol.fluffy_fur.common.config.ServerConfig;
import mod.maxbogomol.fluffy_fur.common.event.Events;
import mod.maxbogomol.fluffy_fur.common.item.TestShrimpItem;
import mod.maxbogomol.fluffy_fur.common.loot.AddItemListLootModifier;
import mod.maxbogomol.fluffy_fur.common.loot.AddItemLootModifier;
import mod.maxbogomol.fluffy_fur.common.network.PacketHandler;
import mod.maxbogomol.fluffy_fur.common.proxy.ClientProxy;
import mod.maxbogomol.fluffy_fur.common.proxy.ISidedProxy;
import mod.maxbogomol.fluffy_fur.common.proxy.ServerProxy;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Arrays;

@Mod("fluffy_fur")
public class FluffyFur {
    public static final String MOD_ID = "fluffy_fur";
    public static final int VERSION_NUMBER = 1;

    public static final ISidedProxy proxy = DistExecutor.unsafeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MOD_ID);

    public static final DeferredRegister<BannerPattern> BANNER_PATTERNS = DeferredRegister.create(Registries.BANNER_PATTERN, MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, MOD_ID);
    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> ARG_TYPES = DeferredRegister.create(ForgeRegistries.COMMAND_ARGUMENT_TYPES, MOD_ID);
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER_TYPES = DeferredRegister.createOptional(Registries.TRUNK_PLACER_TYPE, MOD_ID);
    public static final DeferredRegister<BlockStateProviderType<?>> BLOCK_STATE_PROVIDER_TYPE = DeferredRegister.createOptional(Registries.BLOCK_STATE_PROVIDER_TYPE, MOD_ID);

    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MOD_ID);

    //ITEMS
    public static final RegistryObject<Item> TEST_SHRIMP = ITEMS.register("test_shrimp", () -> new TestShrimpItem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> THING = ITEMS.register("thing", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));

    //BLOCK_ENTITIES
    public static final RegistryObject<BlockEntityType<CustomSignBlockEntity>> SIGN_BLOCK_ENTITY = BLOCK_ENTITIES.register("sign", () -> BlockEntityType.Builder.of(CustomSignBlockEntity::new, getBlocks(CustomStandingSignBlock.class, CustomWallSignBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<CustomHangingSignBlockEntity>> HANGING_SIGN_BLOCK_ENTITY = BLOCK_ENTITIES.register("hanging_sign", () -> BlockEntityType.Builder.of(CustomHangingSignBlockEntity::new, getBlocks(CustomHangingSignBlockEntity.class, CustomWallHangingSignBlock.class)).build(null));

    //PARTICLES
    public static RegistryObject<GenericParticleType> WISP_PARTICLE = PARTICLES.register("wisp", GenericParticleType::new);
    public static RegistryObject<GenericParticleType> TINY_WISP_PARTICLE = PARTICLES.register("tiny_wisp", GenericParticleType::new);
    public static RegistryObject<GenericParticleType> SPARKLE_PARTICLE = PARTICLES.register("sparkle", GenericParticleType::new);
    public static RegistryObject<GenericParticleType> STAR_PARTICLE = PARTICLES.register("star", GenericParticleType::new);
    public static RegistryObject<GenericParticleType> TINY_STAR_PARTICLE = PARTICLES.register("tiny_star", GenericParticleType::new);
    public static RegistryObject<GenericParticleType> SQUARE_PARTICLE = PARTICLES.register("square", GenericParticleType::new);
    public static RegistryObject<GenericParticleType> DOT_PARTICLE = PARTICLES.register("dot", GenericParticleType::new);
    public static RegistryObject<GenericParticleType> CIRCLE_PARTICLE = PARTICLES.register("circle", GenericParticleType::new);
    public static RegistryObject<GenericParticleType> TINY_CIRCLE_PARTICLE = PARTICLES.register("tiny_circle", GenericParticleType::new);
    public static RegistryObject<GenericParticleType> HEART_PARTICLE = PARTICLES.register("heart", GenericParticleType::new);
    public static RegistryObject<GenericParticleType> SMOKE_PARTICLE = PARTICLES.register("smoke", GenericParticleType::new);
    public static RegistryObject<GenericParticleType> CUBE_PARTICLE = PARTICLES.register("cube", GenericParticleType::new);
    public static RegistryObject<GenericParticleType> TRAIL_PARTICLE = PARTICLES.register("trail", GenericParticleType::new);

    //public static final RegistryObject<BlockStateProviderType<?>> AN_STATEPROVIDER = BLOCK_STATE_PROVIDER_TYPE.register("an_stateprovider", () -> new BlockStateProviderType<>(SupplierBlockStateProvider.CODEC));

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM = LOOT_MODIFIERS.register("add_item", AddItemLootModifier.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM_LIST = LOOT_MODIFIERS.register("add_item_list", AddItemListLootModifier.CODEC);

    public FluffyFur() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ITEMS.register(eventBus);
        BLOCK_ENTITIES.register(eventBus);
        PARTICLES.register(eventBus);

        BANNER_PATTERNS.register(eventBus);
        SOUND_EVENTS.register(eventBus);
        ARG_TYPES.register(eventBus);
        TRUNK_PLACER_TYPES.register(eventBus);
        BLOCK_STATE_PROVIDER_TYPE.register(eventBus);
        LOOT_MODIFIERS.register(eventBus);

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);

        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            forgeBus.addListener(ClientTickHandler::clientTickEnd);
            forgeBus.addListener(WorldRenderHandler::onRenderWorldLast);
            forgeBus.addListener(ClientWorldEvent::onTick);
            forgeBus.addListener(ClientWorldEvent::onRender);
            //forgeBus.addListener(HUDEventHandler::onDrawScreenPost);
            forgeBus.addListener(TooltipEventHandler::onPostTooltipEvent);
            //forgeBus.addListener(KeyBindHandler::onInput);
            //forgeBus.addListener(KeyBindHandler::onKey);
            //forgeBus.addListener(KeyBindHandler::onMouseKey);
            MinecraftForge.EVENT_BUS.register(new ClientEvents());
            return new Object();
        });

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new Events());
    }

    private void setup(final FMLCommonSetupEvent event) {
        PacketHandler.init();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        try {
            Class.forName("net.optifine.Config");
            FluffyFurClient.optifinePresent = true;
        } catch (ClassNotFoundException e) {
            FluffyFurClient.optifinePresent = false;
        }
    }

    public static Block[] getBlocks(Class<?>... blockClasses) {
        IForgeRegistry<Block> blocks = ForgeRegistries.BLOCKS;
        ArrayList<Block> matchingBlocks = new ArrayList<>();
        for (Block block : blocks) {
            if (Arrays.stream(blockClasses).anyMatch(b -> b.isInstance(block))) {
                matchingBlocks.add(block);
            }
        }
        return matchingBlocks.toArray(new Block[0]);
    }

    public static Block[] getBlocksExact(Class<?> clazz) {
        IForgeRegistry<Block> blocks = ForgeRegistries.BLOCKS;
        ArrayList<Block> matchingBlocks = new ArrayList<>();
        for (Block block : blocks) {
            if (clazz.equals(block.getClass())) {
                matchingBlocks.add(block);
            }
        }
        return matchingBlocks.toArray(new Block[0]);
    }
}