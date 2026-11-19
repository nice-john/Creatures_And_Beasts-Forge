package com.cgessinger.creaturesandbeasts.items;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterial;

public class GlowingFlowerCrownItem extends FlowerCrownItem {
    public GlowingFlowerCrownItem(ArmorMaterial material, Properties properties) {
        super(material, properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
