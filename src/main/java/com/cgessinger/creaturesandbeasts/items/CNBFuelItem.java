package com.cgessinger.creaturesandbeasts.items;

import net.minecraft.world.item.Item;

/**
 * Item that carries a burn time for furnace fuel registration. On Fabric, fuel is registered
 * via {@code FuelRegistry.INSTANCE.add(item, burnTime)} (called from {@link
 * com.cgessinger.creaturesandbeasts.init.CNBItems#registerFuels()}) rather than via an
 * Item override. The burnTime is stored here so the registration call can read it back.
 */
public class CNBFuelItem extends Item {
    private final int burnTime;

    public CNBFuelItem(int burnTime) {
        super(new Item.Properties());
        this.burnTime = burnTime;
    }

    public int getBurnTime() {
        return this.burnTime;
    }
}
