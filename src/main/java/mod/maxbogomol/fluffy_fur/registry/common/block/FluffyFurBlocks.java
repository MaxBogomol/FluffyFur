package mod.maxbogomol.fluffy_fur.registry.common.block;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.common.block.plush.PlushBlock;
import mod.maxbogomol.fluffy_fur.common.fire.FireBlockHandler;
import mod.maxbogomol.fluffy_fur.common.fire.FireBlockModifier;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

public class FluffyFurBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FluffyFur.MOD_ID);

    public static final RegistryObject<Block> POTTED_PINK_PETALS = BLOCKS.register("potted_pink_petals", () -> new FlowerPotBlock(Blocks.PINK_PETALS, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));

    public static final RegistryObject<Block> MAXBOGOMOL_PLUSH = BLOCKS.register("maxbogomol_plush", () -> new PlushBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).mapColor(MapColor.TERRACOTTA_PINK).noOcclusion()));
    public static final RegistryObject<Block> ONIXTHECAT_PLUSH = BLOCKS.register("onixthecat_plush", () -> new PlushBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).mapColor(MapColor.TERRACOTTA_WHITE).noOcclusion()));
    public static final RegistryObject<Block> UNOLOGICALSAMSAR_PLUSH = BLOCKS.register("unlogicalsamsar_plush", () -> new PlushBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).mapColor(MapColor.TERRACOTTA_BROWN).noOcclusion()));
    public static final RegistryObject<Block> FOXAIRPLANE_PLUSH = BLOCKS.register("foxairplane_plush", () -> new PlushBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).mapColor(MapColor.TERRACOTTA_RED).noOcclusion()));
    public static final RegistryObject<Block> SAMMYSEMICOLON_PLUSH = BLOCKS.register("sammysemicolon_plush", () -> new PlushBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).mapColor(MapColor.TERRACOTTA_GRAY).noOcclusion()));
    public static final RegistryObject<Block> BOYKISSER_PLUSH = BLOCKS.register("boykisser_plush", () -> new PlushBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).mapColor(MapColor.TERRACOTTA_WHITE).noOcclusion()));
    public static final RegistryObject<Block> NANACHI_PLUSH = BLOCKS.register("nanachi_plush", () -> new PlushBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).mapColor(MapColor.TERRACOTTA_ORANGE).noOcclusion()));
    public static final RegistryObject<Block> SPECKLE_PLUSH = BLOCKS.register("speckle_plush", () -> new PlushBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).mapColor(MapColor.TERRACOTTA_WHITE).noOcclusion()));

    public static final RegistryObject<Block> YONKABLOCK = BLOCKS.register("yonkablock", () -> new Block(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL)));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    @Mod.EventBusSubscriber(modid = FluffyFur.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void registerEntities(FMLCommonSetupEvent event) {
            PlushBlock.foxSoundPlush.add(MAXBOGOMOL_PLUSH.get());
            PlushBlock.foxSoundPlush.add(UNOLOGICALSAMSAR_PLUSH.get());
            PlushBlock.foxSoundPlush.add(FOXAIRPLANE_PLUSH.get());
            PlushBlock.foxSoundPlush.add(NANACHI_PLUSH.get());

            PlushBlock.goatSoundPlush.add(ONIXTHECAT_PLUSH.get());

            PlushBlock.catSoundPlush.add(SAMMYSEMICOLON_PLUSH.get());
            PlushBlock.catSoundPlush.add(BOYKISSER_PLUSH.get());
            PlushBlock.catSoundPlush.add(SPECKLE_PLUSH.get());
        }
    }

    public static FireBlock fireblock;

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

    public static void setFireBlock() {
        fireblock = (FireBlock) Blocks.FIRE;
        FireBlockHandler.register(new FireBlockModifier());
    }

    public static void axeStrippables(Block block, Block result) {
        AxeItem.STRIPPABLES = new ImmutableMap.Builder<Block, Block>().putAll(AxeItem.STRIPPABLES).put(block, result).build();
    }

    public static void fireBlock(Block block, int encouragement, int flammability) {
        fireblock.setFlammable(block, encouragement, flammability);
    }

    public static void weatheringCopper(Block block, Block result) {
        try {
            Field delegateField = WeatheringCopper.NEXT_BY_BLOCK.getClass().getDeclaredField("delegate");
            delegateField.setAccessible(true);
            @SuppressWarnings("unchecked")
            Supplier<BiMap<Block, Block>> originalWeatheringMapDelegate = (Supplier<BiMap<Block, Block>>) delegateField.get(WeatheringCopper.NEXT_BY_BLOCK);
            com.google.common.base.Supplier<BiMap<Block, Block>> weatheringMapDelegate = () -> {
                ImmutableBiMap.Builder<Block, Block> builder = ImmutableBiMap.builder();
                builder.putAll(originalWeatheringMapDelegate.get());
                builder.put(block, result);
                return builder.build();
            };

            delegateField.set(WeatheringCopper.NEXT_BY_BLOCK, weatheringMapDelegate);
        } catch (Exception e) {
            FluffyFur.LOGGER.error("Failed weathering copper", e);
        }
    }

    public static void waxedCopper(Block block, Block result) {
        Supplier<BiMap<Block, Block>> originalWaxableMapSupplier = HoneycombItem.WAXABLES;
        HoneycombItem.WAXABLES = Suppliers.memoize(() -> {
            ImmutableBiMap.Builder<Block, Block> builder = ImmutableBiMap.builder();
            builder.putAll(originalWaxableMapSupplier.get());
            builder.put(block, result);
            return builder.build();
        });
    }
}
