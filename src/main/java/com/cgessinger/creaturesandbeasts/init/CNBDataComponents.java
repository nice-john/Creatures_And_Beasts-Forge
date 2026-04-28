package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Custom DataComponentType registry. Replaces the 1.20.x ItemStack-NBT capability path that
 * NeoForge dropped — anything we want to stash on an ItemStack and have survive copies / network
 * sync / save-load goes here as a typed component.
 */
public class CNBDataComponents {

    public static final DeferredRegister.DataComponents COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, CreaturesAndBeasts.MOD_ID);

    /**
     * Remaining ticks of "imbued" status on a Cinder Sword. Counts down each tick in
     * inventoryTick; when it hits zero the sword degrades to the next-lower tier (which itself
     * gets a fresh 400-tick imbue) until imbueLevel 0 is reached.
     */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> IMBUED_TICKS =
            COMPONENTS.registerComponentType("imbued_ticks", builder -> builder
                    .persistent(Codec.INT)
                    .networkSynchronized(ByteBufCodecs.VAR_INT));

    /**
     * Number of yeti-hide layers reinforcing an armor piece. Set on an ItemStack when the player
     * combines armor + Yeti Hide on an anvil. Each layer adds a small armor bonus via a patched
     * {@code ItemAttributeModifiers} component on the stack. Capped at {@code CNBConfig.hideAmount}.
     * Zero / unset means "no reinforcement" — we never write 0; we just leave the component absent.
     */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> HIDE_LAYERS =
            COMPONENTS.registerComponentType("hide_layers", builder -> builder
                    .persistent(Codec.INT)
                    .networkSynchronized(ByteBufCodecs.VAR_INT));
}
