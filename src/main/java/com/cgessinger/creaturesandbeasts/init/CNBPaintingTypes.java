package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;

/**
 * Painting variants in 1.21 are <b>datapack-only</b> — they live in
 * {@code data/&lt;ns&gt;/painting_variant/&lt;name&gt;.json} as DynamicOps-loaded
 * registry entries, not in {@link net.minecraft.core.registries.BuiltInRegistries}.
 * The lilytad painting JSON is at
 * {@code src/main/resources/data/cnb/painting_variant/lilytad.json}; this class
 * only exists as a stub so the main initializer can call {@link #register()}.
 */
public class CNBPaintingTypes {

    public static void register() {
        // No-op. Painting variants are loaded from data/cnb/painting_variant/ JSON.
        CreaturesAndBeasts.LOGGER.debug("Registered CNB painting variants (data-driven)");
    }
}
