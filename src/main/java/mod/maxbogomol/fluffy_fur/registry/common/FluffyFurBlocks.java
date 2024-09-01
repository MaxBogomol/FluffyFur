package mod.maxbogomol.fluffy_fur.registry.common;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.Arrays;

public class FluffyFurBlocks {
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
