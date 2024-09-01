package mod.maxbogomol.fluffy_fur.common.item;

import mod.maxbogomol.fluffy_fur.common.entity.CustomChestBoatEntity;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Predicate;

public class CustomChestBoatItem extends Item {
    private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
    private final RegistryObject<EntityType<CustomChestBoatEntity>> boat;

    public CustomChestBoatItem(Properties properties, RegistryObject<EntityType<CustomChestBoatEntity>> boat) {
        super(properties);
        this.boat = boat;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        HitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
        if (hitResult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            Vec3 vector3d = player.getViewVector(1.0F);
            List<Entity> list = level.getEntities(player, player.getBoundingBox().expandTowards(vector3d.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);
            if (!list.isEmpty()) {
                Vec3 vector3d1 = player.getEyePosition(1.0F);

                for (Entity entity : list) {
                    AABB AABB = entity.getBoundingBox().inflate(entity.getPickRadius());
                    if (AABB.contains(vector3d1)) {
                        return InteractionResultHolder.pass(itemstack);
                    }
                }
            }

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                CustomChestBoatEntity boatEntity = boat.get().create(level);
                boatEntity.setPos(hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z);
                boatEntity.setYRot(player.getYRot());
                if (!level.noCollision(boatEntity, boatEntity.getBoundingBox().inflate(-0.1D))) {
                    return InteractionResultHolder.fail(itemstack);
                } else {
                    if (!level.isClientSide) {
                        level.addFreshEntity(boatEntity);
                        if (!player.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
                }
            } else {
                return InteractionResultHolder.pass(itemstack);
            }
        }
    }
}