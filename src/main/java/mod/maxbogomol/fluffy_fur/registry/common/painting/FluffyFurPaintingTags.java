package mod.maxbogomol.fluffy_fur.registry.common.painting;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class FluffyFurPaintingTags {
    public static final TagKey<PaintingVariant> DROPPABLE = TagKey.create(Registries.PAINTING_VARIANT, new ResourceLocation(FluffyFur.MOD_ID, "droppable"));
}
