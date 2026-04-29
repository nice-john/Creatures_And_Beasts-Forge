package com.cgessinger.creaturesandbeasts;

import com.cgessinger.creaturesandbeasts.config.CNBConfig;
import com.cgessinger.creaturesandbeasts.events.CNBEvents;
import com.cgessinger.creaturesandbeasts.init.*;
import com.cgessinger.creaturesandbeasts.world.gen.ModEntitySpawns;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
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
        // Anvil combine, yeti-hide armor bonus, spear looting, sporeling backpack
        // drop, and sporeling rider positioning all live in mixins (see the
        // mixin/ package + creatures-and-beasts.mixins.json). Only the
        // right-click-to-dismount sporeling logic uses an API-level callback.
        CNBEvents.register();

        // ── Flower-pot plant associations ─────────────────────────────────────
        // No-op on Fabric: the vanilla FlowerPotBlock(Block plant, Properties) ctor
        // self-registers each potted block into the static FlowerPotBlock.POTTED_BY_BLOCK
        // map. Our POTTED_PINK_WATERLILY etc. are registered in CNBBlocks via that ctor,
        // so the (Forge-only) FlowerPotBlock.addPlant() helper isn't needed here.

        // ── Config ────────────────────────────────────────────────────────────
        CNBConfig.load(FabricLoader.getInstance().getConfigDir()
                .resolve("creaturesandbeasts-common.toml"));
    }
}
