package mod.maxbogomol.fluffy_fur.common.damage;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class DamageHandler {
    public static ResourceKey<DamageType> register(String modId, String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(modId, name));
    }

    public static DamageSource create(Level level, ResourceKey<DamageType> key, @Nullable Entity source, @Nullable Entity attacker) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key), source, attacker);
    }

    public static DamageSource create(Level level, ResourceKey<DamageType> key, @Nullable Entity source) {
        return create(level, key, source, null);
    }

    public static DamageSource create(Level level, ResourceKey<DamageType> key) {
        return create(level, key, null, null);
    }
}
