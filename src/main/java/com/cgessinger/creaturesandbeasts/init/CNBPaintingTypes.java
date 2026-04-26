package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;

public class CNBPaintingTypes {
    public static final DeferredRegister<PaintingVariant> PAINTINGS =
            DeferredRegister.create(Registries.PAINTING_VARIANT, CreaturesAndBeasts.MOD_ID);

    public static final DeferredHolder<PaintingVariant, PaintingVariant> LILYTAD_PAINTING =
            PAINTINGS.register("lilytad", () -> new PaintingVariant(
                    16, 16,
                    ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "lilytad"),
                    Optional.empty(), Optional.empty()));
}
