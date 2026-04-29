package com.cgessinger.creaturesandbeasts.items;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.world.item.Item;

public class CNBFuelItem extends Item {
    private final int burnTime;

    public CNBFuelItem(int burnTime) {
        super(new Item.Properties());
        this.burnTime = burnTime;
        // Register fuel value via Fabric API (safe to call during item construction)
        FuelRegistry.INSTANCE.add(this, burnTime);
    }

    /** Exposed so callers can inspect the registered value if needed. */
    public int getBurnTime() {
        return this.burnTime;
    }
}
