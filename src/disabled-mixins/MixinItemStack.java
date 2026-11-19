package com.cgessinger.creaturesandbeasts.mixin;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Previously copied Forge capability data when an ItemStack was duplicated.
 * On Fabric the CinderSword imbue state is stored as plain NBT, which
 * {@link ItemStack#copy()} already copies automatically — so this mixin is
 * now a no-op placeholder kept in the mixin config for structural consistency.
 */
@Mixin(ItemStack.class)
public class MixinItemStack {
    // No injections required.
}
