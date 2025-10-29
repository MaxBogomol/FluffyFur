package mod.maxbogomol.fluffy_fur.registry.common.item;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.common.item.PlushItem;
import mod.maxbogomol.fluffy_fur.common.item.SillyTreatItem;
import mod.maxbogomol.fluffy_fur.common.item.TestShrimpItem;
import mod.maxbogomol.fluffy_fur.common.item.ThingItem;
import mod.maxbogomol.fluffy_fur.registry.common.FluffyFurSounds;
import mod.maxbogomol.fluffy_fur.registry.common.block.FluffyFurBlocks;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FluffyFurItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FluffyFur.MOD_ID);

    public static final RegistryObject<Item> MUSIC_DISC_FLUFFY = ITEMS.register("music_disc_fluffy", () -> new RecordItem(15, FluffyFurSounds.MUSIC_DISC_FLUFFY.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 176));

    public static final RegistryObject<Item> TEST_SHRIMP = ITEMS.register("test_shrimp", () -> new TestShrimpItem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> THING = ITEMS.register("thing", () -> new ThingItem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SILLY_TREAT = ITEMS.register("silly_treat", () -> new SillyTreatItem(new Item.Properties().rarity(Rarity.UNCOMMON).food(FluffyFurFoods.SILLY_TREAT)));

    public static final RegistryObject<Item> MAXBOGOMOL_PLUSH = ITEMS.register("maxbogomol_plush", () -> new PlushItem(FluffyFurBlocks.MAXBOGOMOL_PLUSH.get(), new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ONIXTHECAT_PLUSH = ITEMS.register("onixthecat_plush", () -> new PlushItem(FluffyFurBlocks.ONIXTHECAT_PLUSH.get(), new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> UNOLOGICALSAMSAR_PLUSH = ITEMS.register("unlogicalsamsar_plush", () -> new PlushItem(FluffyFurBlocks.UNOLOGICALSAMSAR_PLUSH.get(), new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> FOXAIRPLANE_PLUSH = ITEMS.register("foxairplane_plush", () -> new PlushItem(FluffyFurBlocks.FOXAIRPLANE_PLUSH.get(), new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ONJERLAY_PLUSH = ITEMS.register("onjerlay_plush", () -> new PlushItem(FluffyFurBlocks.ONJERLAY_PLUSH.get(), new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SAMMYSEMICOLON_PLUSH = ITEMS.register("sammysemicolon_plush", () -> new PlushItem(FluffyFurBlocks.SAMMYSEMICOLON_PLUSH.get(), new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> VIOLUNAE_PLUSH = ITEMS.register("violunae_plush", () -> new PlushItem(FluffyFurBlocks.VIOLUNAE_PLUSH.get(), new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> FURRYFOXES_PLUSH = ITEMS.register("furryfoxes_plush", () -> new PlushItem(FluffyFurBlocks.FURRYFOXES_PLUSH.get(), new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BOYKISSER_PLUSH = ITEMS.register("boykisser_plush", () -> new PlushItem(FluffyFurBlocks.BOYKISSER_PLUSH.get(), new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> NANACHI_PLUSH = ITEMS.register("nanachi_plush", () -> new PlushItem(FluffyFurBlocks.NANACHI_PLUSH.get(), new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> NIKO_PLUSH = ITEMS.register("niko_plush", () -> new PlushItem(FluffyFurBlocks.NIKO_PLUSH.get(), new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> PURO_PLUSH = ITEMS.register("puro_plush", () -> new PlushItem(FluffyFurBlocks.PURO_PLUSH.get(), new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> TUNIC_THE_FOX_PLUSH = ITEMS.register("tunic_the_fox_plush", () -> new PlushItem(FluffyFurBlocks.TUNIC_THE_FOX_PLUSH.get(), new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DARK_PRINCE_PLUSH = ITEMS.register("dark_prince_plush", () -> new PlushItem(FluffyFurBlocks.DARK_PRINCE_PLUSH.get(), new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> RALSEI_PLUSH = ITEMS.register("ralsei_plush", () -> new PlushItem(FluffyFurBlocks.RALSEI_PLUSH.get(), new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SPECKLE_PLUSH = ITEMS.register("speckle_plush", () -> new PlushItem(FluffyFurBlocks.SPECKLE_PLUSH.get(), new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SEADRIVE_PLUSH = ITEMS.register("seadrive_plush", () -> new PlushItem(FluffyFurBlocks.SEADRIVE_PLUSH.get(), new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> YONKABLOCK = ITEMS.register("yonkablock", () -> new PlushItem(FluffyFurBlocks.YONKABLOCK.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static void composterItem(float chance, ItemLike item) {
        ComposterBlock.add(chance, item);
    }

    public static void makeBow(Item item) {
        ItemProperties.register(item, new ResourceLocation("pull"), (stack, level, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
            }
        });

        ItemProperties.register(item, new ResourceLocation("pulling"), (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
    }
}
