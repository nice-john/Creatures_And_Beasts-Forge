package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.world.biome.modifiers.AddCostedSpawnsBiomeModifier;
import net.minecraft.resources.ResourceLocation;

/**
 * On Fabric, biome spawn modifications are driven by the
 * {@code data/cnb/forge_biome_modifier/} datapack entries at runtime, interpreted
 * by {@link AddCostedSpawnsBiomeModifier} via the Fabric BiomeModifications API.
 *
 * Calling {@link #register()} hooks the Fabric BiomeModifications callbacks so the
 * JSON-driven spawn data is applied.
 */
public class CNBBiomeModifiers {

    public static void register() {
        AddCostedSpawnsBiomeModifier.registerFabricModifications();
        CreaturesAndBeasts.LOGGER.debug("Registered CNB biome modifiers");
    }
}
