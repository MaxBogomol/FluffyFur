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

    public static final RegistryObject<Attribute> SIZE_SCALE = ATTRIBUTES.register("size_scale", () -> new RangedAttribute("attribute.name.fluffy_fur.size_scale", 1, 0.1f, 5f).setSyncable(true));
    public static final RegistryObject<Attribute> JUMP_HEIGHT_AMPLIFIER = ATTRIBUTES.register("jump_height_amplifier", () -> new RangedAttribute("attribute.name.fluffy_fur.jump_height_amplifier", 0, -100f, 100f).setSyncable(true));
    public static final RegistryObject<Attribute> CAMERA_DISTANCE_AMPLIFIER = ATTRIBUTES.register("camera_distance_amplifier", () -> new RangedAttribute("attribute.name.fluffy_fur.camera_distance_amplifier", 0, 0, 100f).setSyncable(true));

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }

    @Mod.EventBusSubscriber(modid = FluffyFur.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void registerAttributes(EntityAttributeModificationEvent event) {
            event.add(EntityType.PLAYER, SIZE_SCALE.get());
            event.add(EntityType.PLAYER, JUMP_HEIGHT_AMPLIFIER.get());
            event.add(EntityType.PLAYER, CAMERA_DISTANCE_AMPLIFIER.get());
        }
    }
}
