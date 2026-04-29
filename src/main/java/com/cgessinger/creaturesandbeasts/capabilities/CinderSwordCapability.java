package com.cgessinger.creaturesandbeasts.capabilities;

import net.minecraft.world.item.ItemStack;

/**
 * Fabric replacement for the Forge Capability system.
 * Imbue-tick data is stored directly in the item-stack NBT tag under "imbuedTicks".
 */
public final class CinderSwordCapability {

    private static final String KEY = "imbuedTicks";

    private CinderSwordCapability() {}

    public static int getImbuedTicks(ItemStack stack) {
        return stack.hasTag() ? stack.getOrCreateTag().getInt(KEY) : 0;
    }

    public static void setImbuedTicks(ItemStack stack, int ticks) {
        stack.getOrCreateTag().putInt(KEY, ticks);
    }
}
