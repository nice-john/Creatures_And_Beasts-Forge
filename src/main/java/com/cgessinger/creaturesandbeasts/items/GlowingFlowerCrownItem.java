package com.cgessinger.creaturesandbeasts.items;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class GlowingFlowerCrownItem extends FlowerCrownItem {
    private final Ingredient repairItems;

    public GlowingFlowerCrownItem(Holder<ArmorMaterial> material, Ingredient repairItems, Type type, Properties properties) {
        super(material, repairItems, type, properties);
        this.repairItems = repairItems;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isValidRepairItem(ItemStack stackInput, ItemStack repairStack) {
        return this.repairItems != null && this.repairItems.test(repairStack);
    }
}
