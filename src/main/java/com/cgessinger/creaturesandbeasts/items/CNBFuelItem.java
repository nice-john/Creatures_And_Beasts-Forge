package com.cgessinger.creaturesandbeasts.items;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.CookingFuel;
import net.minecraft.world.level.storage.loot.providers.number.floats.ResolvableFloat;
import net.minecraft.world.level.storage.loot.providers.number.ints.ResolvableInt;

/**
 * Furnace fuel item. 26.x removed Fabric's {@code FuelRegistry}; burn time is now the
 * {@link DataComponents#COOKING_FUEL} item component, attached via {@link #fuelProperties(int)}.
 */
public class CNBFuelItem extends Item {

    public CNBFuelItem(Properties properties) {
        super(properties);
    }

    public static Properties fuelProperties(int burnTime) {
        return new Properties().component(DataComponents.COOKING_FUEL,
                new CookingFuel(new ResolvableInt.Constant(burnTime), new ResolvableFloat.Constant(1.0F)));
    }
}
