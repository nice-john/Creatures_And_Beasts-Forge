package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;

/**
 * Custom DataComponentType registry. Anything we want to stash on an ItemStack and have survive
 * copies / network sync / save-load goes here as a typed component.
 */
public class CNBDataComponents {

    /**
     * Remaining ticks of "imbued" status on a Cinder Sword. Counts down each tick in
     * inventoryTick; when it hits zero the sword degrades to the next-lower tier (which itself
     * gets a fresh 400-tick imbue) until imbueLevel 0 is reached.
     */
    public static final DataComponentType<Integer> IMBUED_TICKS = register("imbued_ticks",
            DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .networkSynchronized(ByteBufCodecs.VAR_INT)
                    .build());

    /**
     * Number of yeti-hide layers reinforcing an armor piece. Set on an ItemStack when the player
     * combines armor + Yeti Hide on an anvil. Each layer adds a small armor bonus via a patched
     * {@code ItemAttributeModifiers} component on the stack. Capped at {@code CNBConfig.hideAmount}.
     * Zero / unset means "no reinforcement" — we never write 0; we just leave the component absent.
     */
    public static final DataComponentType<Integer> HIDE_LAYERS = register("hide_layers",
            DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .networkSynchronized(ByteBufCodecs.VAR_INT)
                    .build());

    private static <T> DataComponentType<T> register(String name, DataComponentType<T> type) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE,
                ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, name), type);
    }

    public static void register() {
        // Static field initialisation triggers all registrations.
        CreaturesAndBeasts.LOGGER.debug("Registered CNB data components");
    }
}
