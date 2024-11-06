package mod.maxbogomol.fluffy_fur.registry.common.damage;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public class FluffyFurDamageTypeTags {
    public static final TagKey<DamageType> MAGIC = TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("forge", "is_magic"));
}
