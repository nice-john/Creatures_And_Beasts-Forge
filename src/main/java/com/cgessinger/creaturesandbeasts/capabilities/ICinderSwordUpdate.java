package com.cgessinger.creaturesandbeasts.capabilities;

/**
 * Simple data interface for tracking how many imbue ticks remain on a Cinder Sword.
 * On Fabric this is backed purely by the item-stack NBT tag ("imbuedTicks").
 */
public interface ICinderSwordUpdate {
    int getImbuedTicks();
    int setImbuedTicks(int ticks);
}
