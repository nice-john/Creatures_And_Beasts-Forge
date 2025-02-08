package com.cgessinger.creaturesandbeasts.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;

public class GlowingFlowerCrownItem extends FlowerCrownItem {
    private final Ingredient repairItems; // Mark final for immutability

    // Updated constructor to accept repairItems
    public GlowingFlowerCrownItem(ArmorMaterial material, Ingredient repairItems, Type type, Properties properties) {
        super(material, repairItems, type, properties); // Pass repairItems to the superclass
        this.repairItems = repairItems; // Initialize repairItems
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true; // Ensure the glowing effect
    }

    @Override
    public boolean isValidRepairItem(ItemStack stackInput, ItemStack repairStack) {
        // Ensure repairItems is not null before testing
        return this.repairItems != null && this.repairItems.test(repairStack);
    }
}

