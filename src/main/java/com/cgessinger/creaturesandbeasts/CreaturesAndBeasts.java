package com.cgessinger.creaturesandbeasts;

import com.cgessinger.creaturesandbeasts.config.CNBConfig;
import com.cgessinger.creaturesandbeasts.init.*;
import com.cgessinger.creaturesandbeasts.world.gen.ModEntitySpawns;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreaturesAndBeasts implements ModInitializer {
    public static final String MOD_ID = "cnb";
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        // ── Registries ───────────────────────────────────────────────────────
        CNBSoundEvents.register();
        CNBParticleTypes.register();
        CNBBlocks.register();
        CNBItems.register();
        CNBEntityTypes.register();
        CNBContainerTypes.register();
        CNBPaintingTypes.register();
        CNBCreativeTabs.register();

        // ── Type systems ─────────────────────────────────────────────────────
        CNBSporelingTypes.registerAll();
        CNBLizardTypes.registerAll();
        CNBLilytadTypes.registerAll();
        CNBMinipadTypes.registerAll();

        // ── Entity attributes & spawn placements ─────────────────────────────
        CNBEntityTypes.registerAttributes();
        ModEntitySpawns.entitySpawnPlacementRegistry();

        // ── World / biome / loot modifications ───────────────────────────────
        CNBBiomeModifiers.register();
        CNBLootModifiers.register();

        // ── Game events ───────────────────────────────────────────────────────
        CNBEvents.register();

        // ── Flower-pot plant associations (vanilla method via access widener) ─
        FlowerPotBlock vanillaFlowerPot = (FlowerPotBlock) Blocks.FLOWER_POT;
        vanillaFlowerPot.addPlant(
                BuiltInRegistries.BLOCK.getKey(CNBBlocks.PINK_WATERLILY_BLOCK),
                () -> CNBBlocks.POTTED_PINK_WATERLILY);
        vanillaFlowerPot.addPlant(
                BuiltInRegistries.BLOCK.getKey(CNBBlocks.LIGHT_PINK_WATERLILY_BLOCK),
                () -> CNBBlocks.POTTED_LIGHT_PINK_WATERLILY);
        vanillaFlowerPot.addPlant(
                BuiltInRegistries.BLOCK.getKey(CNBBlocks.YELLOW_WATERLILY_BLOCK),
                () -> CNBBlocks.POTTED_YELLOW_WATERLILY);

        // ── Config ────────────────────────────────────────────────────────────
        CNBConfig.load(FabricLoader.getInstance().getConfigDir()
                .resolve("creaturesandbeasts-common.toml"));
    }
}
