package com.cgessinger.creaturesandbeasts.items;

import com.cgessinger.creaturesandbeasts.entities.ThrownCactemSpearEntity;
import com.cgessinger.creaturesandbeasts.init.CNBSoundEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * Cactem spear: charge with right-click, release to throw a {@link ThrownCactemSpearEntity}.
 * Honors Multishot (fans 3 spears), Loyalty (return-to-owner; only the primary spear keeps
 * the enchant), Fire Aspect & Knockback (applied on hit by the projectile entity).
 */
public class SpearItem extends Item {

    private static final Identifier ATTACK_DAMAGE_ID =
            Identifier.fromNamespaceAndPath("cnb", "spear_attack_damage");
    private static final Identifier ATTACK_SPEED_ID =
            Identifier.fromNamespaceAndPath("cnb", "spear_attack_speed");

    public SpearItem(Properties properties) {
        super(properties);
    }

    /**
     * Properties for the spear: attribute modifiers, trident-style Tool component (can't break
     * blocks in creative, 2 durability per mined block) and low enchantability.
     */
    public static Properties spearProperties(Properties properties) {
        return properties
                .attributes(buildAttributes())
                .component(DataComponents.TOOL, new Tool(List.of(), 1.0F, 2, false))
                .enchantable(1);
    }

    private static ItemAttributeModifiers buildAttributes() {
        return ItemAttributeModifiers.builder()
                .add(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(ATTACK_DAMAGE_ID, 5.0D, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(ATTACK_SPEED_ID, -2.9D, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .build();
    }

    /** Melee hits cost 1 durability. */
    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity hurtEntity, LivingEntity owner) {
        stack.hurtAndBreak(1, owner, EquipmentSlot.MAINHAND);
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.SPEAR;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return InteractionResult.FAIL;
        }
        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity entity, int useTicks) {
        if (!(entity instanceof Player player)) return false;

        int charged = this.getUseDuration(stack, entity) - useTicks;
        if (charged < 10) return false;
        if (level.isClientSide()) return false;

        // Damage the spear once for the throw
        stack.hurtAndBreak(1, player,
                player.getUsedItemHand() == InteractionHand.MAIN_HAND
                        ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);

        spawnSpears(stack, player, level);

        if (!player.getAbilities().instabuild) {
            player.getInventory().removeItem(stack);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        return true;
    }

    private void spawnSpears(ItemStack stack, Player player, Level level) {
        int multishotLevel = getEnchantmentLevel(level, stack, Enchantments.MULTISHOT);
        int numberOfSpears = multishotLevel == 0 ? 1 : 3;
        float[] pitches = getShotPitches(player.getRandom());

        // Side spears can't have Loyalty (otherwise they'd all return as a swarm)
        ItemStack noLoyaltyStack = stack.copy();
        EnchantmentHelper.updateEnchantments(noLoyaltyStack, mut ->
                mut.removeIf(holder -> holder.is(Enchantments.LOYALTY)));

        for (int i = 0; i < numberOfSpears; i++) {
            if (i == 0) {
                shootProjectile(level, player, stack, pitches[i], 0.0F, true);
            } else if (i == 1) {
                shootProjectile(level, player, noLoyaltyStack, pitches[i], -10.0F, false);
            } else {
                shootProjectile(level, player, noLoyaltyStack, pitches[i], 10.0F, false);
            }
        }
    }

    private void shootProjectile(Level level, Player player, ItemStack stack, float soundVariation, float yawJitter, boolean canPickup) {
        ThrownCactemSpearEntity spear = new ThrownCactemSpearEntity(level, player, stack);
        Vec3 view = player.getViewVector(1.0F);
        float randomPitch = (level.getRandom().nextFloat() - 0.5F) * yawJitter;
        float randomYaw = (level.getRandom().nextFloat() - 0.5F) * yawJitter;
        view = view.xRot(randomPitch).yRot(randomYaw);
        spear.shoot(view.x(), view.y(), view.z(), 1.6F, 1.0F);

        if (player.getAbilities().instabuild) {
            spear.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        } else {
            spear.pickup = canPickup ? AbstractArrow.Pickup.ALLOWED : AbstractArrow.Pickup.DISALLOWED;
        }

        level.addFreshEntity(spear);
        level.playSound(null, spear, CNBSoundEvents.SPEAR_THROW, SoundSource.PLAYERS, 1.0F, soundVariation);
    }

    private static float[] getShotPitches(RandomSource rand) {
        boolean flag = rand.nextBoolean();
        return new float[]{1.0F, getRandomShotPitch(flag, rand), getRandomShotPitch(!flag, rand)};
    }

    private static float getRandomShotPitch(boolean isHigher, RandomSource rand) {
        float f = isHigher ? 0.63F : 0.43F;
        return 1.0F / (rand.nextFloat() * 0.5F + 1.8F) + f;
    }

    /** Look up an enchantment level on a stack via the level's enchantment registry. */
    private static int getEnchantmentLevel(Level level, ItemStack stack, ResourceKey<Enchantment> key) {
        Holder<Enchantment> holder = level.registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .getOrThrow(key);
        return EnchantmentHelper.getItemEnchantmentLevel(holder, stack);
    }
}
