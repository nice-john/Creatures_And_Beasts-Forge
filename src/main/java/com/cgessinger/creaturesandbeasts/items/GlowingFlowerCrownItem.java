package com.cgessinger.creaturesandbeasts.items;

import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class GlowingFlowerCrownItem extends FlowerCrownItem {

    public GlowingFlowerCrownItem(ArmorMaterial material, Ingredient repairItems,
                                   Type type, Properties properties) {
        super(material, repairItems, type, properties);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) { return false; }

    /** Makes the item appear with an enchantment glint in inventory. */
    @Override
    public boolean isFoil(ItemStack stack) { return true; }
}
