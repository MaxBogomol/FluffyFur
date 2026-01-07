package mod.maxbogomol.fluffy_fur.registry.common;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FluffyFurAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, FluffyFur.MOD_ID);

    public static final RegistryObject<Attribute> SIZE_SCALE = ATTRIBUTES.register("size_scale", () -> new RangedAttribute("attribute.name.fluffy_fur.size_scale", 1, 0.01f, 5f).setSyncable(true));

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }

    @Mod.EventBusSubscriber(modid = FluffyFur.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void registerAttributes(EntityAttributeModificationEvent event) {
            event.add(EntityType.PLAYER, SIZE_SCALE.get());
        }
    }
}
