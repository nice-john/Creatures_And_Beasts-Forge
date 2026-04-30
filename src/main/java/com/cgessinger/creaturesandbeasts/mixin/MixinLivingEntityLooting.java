package com.cgessinger.creaturesandbeasts.mixin;

import com.cgessinger.creaturesandbeasts.entities.ThrownCactemSpearEntity;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Replaces Forge's {@code LootingLevelEvent} for our specific case: when a
 * {@link ThrownCactemSpearEntity} is the killing damage source, the looting level
 * should come from the Looting enchantment on the spear's stack rather than from
 * the wielder's hand (the wielder may be far away and holding something else).
 *
 * <p>Vanilla {@code LivingEntity#dropAllDeathLoot} computes the local int looting
 * level from {@code EnchantmentHelper.getMobLooting(player)} only when the direct
 * entity is a Player. We {@code @ModifyVariable} the freshly-stored value to
 * substitute the spear's enchantment level when applicable.
 *
 * <p>1.21 enchantments are data-driven {@link Holder}s, so we resolve
 * {@link Enchantments#LOOTING} (a {@code ResourceKey<Enchantment>}) against the
 * level's registry access at call time rather than the old static
 * {@code Enchantments.MOB_LOOTING} field reference.
 */
@Mixin(LivingEntity.class)
public class MixinLivingEntityLooting {

    @ModifyVariable(method = "dropAllDeathLoot", at = @At("STORE"), ordinal = 0, argsOnly = false)
    private int CNB_spearLooting(int original, ServerLevel serverLevel, DamageSource damageSource) {
        if (damageSource.getDirectEntity() instanceof ThrownCactemSpearEntity spear) {
            Holder<Enchantment> looting = serverLevel.registryAccess()
                    .lookupOrThrow(Registries.ENCHANTMENT)
                    .getOrThrow(Enchantments.LOOTING);
            return EnchantmentHelper.getItemEnchantmentLevel(looting, spear.getSpear());
        }
        return original;
    }
}
