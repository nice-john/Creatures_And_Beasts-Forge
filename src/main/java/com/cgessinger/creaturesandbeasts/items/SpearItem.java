package com.cgessinger.creaturesandbeasts.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.ItemStack;

// TODO[1.21.1 port]: re-implement spear-throw, multishot, loyalty, attribute modifiers, enchantment whitelist.
// 1.21 enchantments are data-driven (no DamageEnchantment / KnockbackEnchantment classes), AttributeModifier
// uses ResourceLocation keys, BASE_ATTACK_DAMAGE_UUID is gone. Vanishable interface removed.
public class SpearItem extends Item {
    public SpearItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public int getUseDuration(ItemStack stack, net.minecraft.world.entity.LivingEntity entity) {
        return 72000;
    }
}
