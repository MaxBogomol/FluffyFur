package mod.maxbogomol.fluffy_fur.registry.common;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.common.mobeffect.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FluffyFurMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, FluffyFur.MOD_ID);

    public static final RegistryObject<MobEffect> ATTACK_SPEED = MOB_EFFECTS.register("attack_speed", AttackSpeedMobEffect::new);
    public static final RegistryObject<MobEffect> ATTACK_SLOWDOWN = MOB_EFFECTS.register("attack_slowdown", AttackSlowdownMobEffect::new);
    public static final RegistryObject<MobEffect> ATTACK_KNOCKBACK_STRENGTH = MOB_EFFECTS.register("attack_knockback_strength", AttackKnockbackStrengthMobEffect::new);
    public static final RegistryObject<MobEffect> ATTACK_KNOCKBACK_WEAKNESS = MOB_EFFECTS.register("attack_knockback_weakness", AttackKnockbackWeaknessMobEffect::new);
    public static final RegistryObject<MobEffect> ARMOR_STRENGTH = MOB_EFFECTS.register("armor_strength", ArmorStrengthMobEffect::new);
    public static final RegistryObject<MobEffect> ARMOR_WEAKNESS = MOB_EFFECTS.register("armor_weakness", ArmorWeaknessMobEffect::new);
    public static final RegistryObject<MobEffect> ARMOR_TOUGHNESS_STRENGTH = MOB_EFFECTS.register("armor_toughness_strength", ArmorToughnessStrengthMobEffect::new);
    public static final RegistryObject<MobEffect> ARMOR_TOUGHNESS_WEAKNESS = MOB_EFFECTS.register("armor_toughness_weakness", ArmorToughnessWeaknessMobEffect::new);
    public static final RegistryObject<MobEffect> KNOCKBACK_RESISTANCE = MOB_EFFECTS.register("knockback_resistance", KnockbackResistanceMobEffect::new);
    public static final RegistryObject<MobEffect> KNOCKBACK_SENSIBILITY = MOB_EFFECTS.register("knockback_sensibility", KnockbackSensibilityMobEffect::new);
    public static final RegistryObject<MobEffect> ENTITY_REACH_EXPAND = MOB_EFFECTS.register("entity_reach_expand", EntityReachExpandMobEffect::new);
    public static final RegistryObject<MobEffect> ENTITY_REACH_SHRINK = MOB_EFFECTS.register("entity_reach_shrink", EntityReachShrinkMobEffect::new);
    public static final RegistryObject<MobEffect> BLOCK_REACH_EXPAND = MOB_EFFECTS.register("block_reach_expand", BlockReachExpandMobEffect::new);
    public static final RegistryObject<MobEffect> BLOCK_REACH_SHRINK = MOB_EFFECTS.register("block_reach_shrink", BlockReachShrinkMobEffect::new);
    public static final RegistryObject<MobEffect> SWIM_SPEED = MOB_EFFECTS.register("swim_speed", SwimSpeedMobEffect::new);
    public static final RegistryObject<MobEffect> SWIM_SLOWNESS = MOB_EFFECTS.register("swim_slowness", SwimSlownessMobEffect::new);

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
