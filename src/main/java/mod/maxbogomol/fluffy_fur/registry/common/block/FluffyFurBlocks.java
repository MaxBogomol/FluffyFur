package mod.maxbogomol.fluffy_fur.registry.common.block;

import com.google.common.collect.ImmutableMap;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.common.block.plush.PlushBlock;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Arrays;

public class FluffyFurBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FluffyFur.MOD_ID);

    public static final RegistryObject<Block> POTTED_PINK_PETALS = BLOCKS.register("potted_pink_petals", () -> new FlowerPotBlock(Blocks.PINK_PETALS, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));

    public static final RegistryObject<Block> MAXBOGOMOL_PLUSH = BLOCKS.register("maxbogomol_plush", () -> new PlushBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).mapColor(MapColor.TERRACOTTA_PINK).noOcclusion()));
    public static final RegistryObject<Block> ONIXTHECAT_PLUSH = BLOCKS.register("onixthecat_plush", () -> new PlushBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).mapColor(MapColor.TERRACOTTA_WHITE).noOcclusion()));
    public static final RegistryObject<Block> SAMMYSEMICOLON_PLUSH = BLOCKS.register("sammysemicolon_plush", () -> new PlushBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).mapColor(MapColor.TERRACOTTA_WHITE).noOcclusion()));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
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
    }

    public static void axeStrippables(Block block, Block result) {
        AxeItem.STRIPPABLES = new ImmutableMap.Builder<Block, Block>().putAll(AxeItem.STRIPPABLES).put(block, result).build();
    }

    public static void fireBlock(Block block, int encouragement, int flammability) {
        fireblock.setFlammable(block, encouragement, flammability);
    }
}
