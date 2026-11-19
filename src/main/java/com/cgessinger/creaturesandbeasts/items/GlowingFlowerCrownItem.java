package com.cgessinger.creaturesandbeasts.items;

import net.minecraft.world.item.ItemStack;

public class GlowingFlowerCrownItem extends FlowerCrownItem {

    public GlowingFlowerCrownItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
