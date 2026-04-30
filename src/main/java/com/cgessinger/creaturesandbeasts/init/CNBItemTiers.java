package com.cgessinger.creaturesandbeasts.init;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

/**
 * 1.21 Tier interface added {@link Tier#getIncorrectBlocksForDrops()} (replaces the old
 * harvestLevel int) and dropped {@code getLevel()}. Cinder mirrors vanilla diamond-tier
 * mining so it uses the diamond "incorrect" tag (anything diamond can't mine).
 */
public enum CNBItemTiers implements Tier {
    CINDER(800, 8.0F, 1.0F, 10, () -> Ingredient.of(CNBItems.CINDERSHELL_SHELL_SHARD));

    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairMaterial;

    CNBItemTiers(int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterialIn) {
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterialIn;
    }

    @Override
    public int getUses() {
        return this.maxUses;
    }

    @Override
    public float getSpeed() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }
}
