package mod.maxbogomol.fluffy_fur.common.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class AddItemLootModifier extends LootModifier {
    public static final Supplier<Codec<AddItemLootModifier>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.create((inst) -> codecStart(inst).and(inst.group(ForgeRegistries.ITEMS.getCodec().
            fieldOf("item").forGetter((m) -> m.addedItem),
                    Codec.INT.optionalFieldOf("count", 1).forGetter((m) -> m.count),
                    Codec.FLOAT.optionalFieldOf("chance", 1f).forGetter((m) -> m.chance)))
            .apply(inst, AddItemLootModifier::new)));

    private final Item addedItem;
    private final int count;
    private final float chance;

    protected AddItemLootModifier(LootItemCondition[] conditions, Item addedItemIn, int count, float chance) {
        super(conditions);
        this.addedItem = addedItemIn;
        this.count = count;
        this.chance = chance;
    }

    @Nonnull
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (chance == 1 || context.getRandom().nextFloat() < chance) {
            ItemStack addedStack = new ItemStack(this.addedItem, this.count);
            if (addedStack.getCount() < addedStack.getMaxStackSize()) {
                generatedLoot.add(addedStack);
            } else {
                int i = addedStack.getCount();

                while (i > 0) {
                    ItemStack subStack = addedStack.copy();
                    subStack.setCount(Math.min(addedStack.getMaxStackSize(), i));
                    i -= subStack.getCount();
                    generatedLoot.add(subStack);
                }
            }
        }

        return generatedLoot;
    }

    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
