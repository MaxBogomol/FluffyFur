package mod.maxbogomol.fluffy_fur.registry.common;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class FluffyFurSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, FluffyFur.MOD_ID);

    public static final RegistryObject<SoundEvent> MUSIC_DISC_FLUFFY = SOUND_EVENTS.register("fluffy", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FluffyFur.MOD_ID, "fluffy")));

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
