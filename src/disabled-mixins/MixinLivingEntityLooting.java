package com.cgessinger.creaturesandbeasts.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Stub — disabled in {@code creatures-and-beasts.mixins.json} for parity with the
 * NeoForge 1.21.1 branch (see {@code CNBEvents}'s matching TODO).
 *
 * <p><b>Why disabled:</b> on 1.20.1, this mixin replaced Forge's
 * {@code LootingLevelEvent} by {@code @ModifyVariable}-ing the local
 * {@code int looting} that {@code LivingEntity#dropAllDeathLoot} stored from
 * {@code EnchantmentHelper.getMobLooting(player)}. In 1.21.1 vanilla:
 * <ul>
 *   <li>{@code dropAllDeathLoot} no longer keeps a local {@code int looting} —
 *       looting is now applied through the loot-table parameter chain
 *       ({@code LootContextParams.LAST_DAMAGE_PLAYER}/{@code ATTACKING_ENTITY})
 *       and resolved by the {@code MOB_LOOTING} enchantment effect on the
 *       attacker's held item.</li>
 *   <li>{@code EnchantmentHelper.getMobLooting} was removed.</li>
 * </ul>
 * Since the player throwing a spear is not "holding" the in-flight spear when
 * the mob dies, the spear's Looting enchantment is no longer auto-honored —
 * but adding it back requires the new enchantment-effect framework, not a
 * variable hijack. Tracked as a TODO in {@code CNBEvents} on both branches.
 */
@Mixin(LivingEntity.class)
public class MixinLivingEntityLooting {
    // Intentionally empty — see class doc.
}
