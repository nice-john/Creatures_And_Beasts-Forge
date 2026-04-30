package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class CNBPaintingTypes {

    public static final PaintingVariant LILYTAD_PAINTING =
            Registry.register(BuiltInRegistries.PAINTING_VARIANT,
                    ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "lilytad"),
                    new PaintingVariant(16, 16));

    public static void register() {
        CreaturesAndBeasts.LOGGER.debug("Registered CNB painting types");
    }
}
