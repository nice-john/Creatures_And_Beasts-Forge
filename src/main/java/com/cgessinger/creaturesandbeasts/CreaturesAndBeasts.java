package com.cgessinger.creaturesandbeasts;

import com.cgessinger.creaturesandbeasts.config.CNBConfig;
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
        // TODO[fabric port]: there is no CNBEvents class on this branch yet. The Forge
        // port had AnvilUpdateEvent (yeti hide combine + heal-spell-book combine),
        // ItemAttributeModifierEvent (yeti hide armor bonus), LootingLevelEvent (spear
        // looting), LivingTickEvent (sporeling backpack drop), PlayerInteractEvent
        // (sporeling dismount). These each need a Fabric-specific replacement (mostly
        // mixins) and aren't ported yet.

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
