package com.cgessinger.creaturesandbeasts.world.biome.modifiers;

import com.cgessinger.creaturesandbeasts.init.CNBEntityTypes;
import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;

/**
 * Registers all mob spawn entries via the Fabric Biome Modifications API.
 * Called once from {@link com.cgessinger.creaturesandbeasts.init.CNBBiomeModifiers#register()}.
 *
 * <p>Forge tags (e.g. {@code #forge:is_desert}) have no direct equivalent on Fabric.
 * We use vanilla {@link BiomeTags} where possible and explicit biome-key checks elsewhere.
 */
public final class AddCostedSpawnsBiomeModifier {

    private AddCostedSpawnsBiomeModifier() {}

    public static void registerFabricModifications() {

        // ── CACTEM ──────────────────────────────────────────────────────
        BiomeModifications.addSpawn(
                ctx -> ctx.hasTag(BiomeTags.IS_BADLANDS),
                MobCategory.CREATURE, CNBEntityTypes.CACTEM, 10, 6, 13);
        BiomeModifications.addSpawn(
                AddCostedSpawnsBiomeModifier::isDesert,
                MobCategory.CREATURE, CNBEntityTypes.CACTEM, 10, 6, 13);

        // ── CINDERSHELL ─────────────────────────────────────────────────
        BiomeModifications.addSpawn(
                ctx -> ctx.hasTag(BiomeTags.IS_NETHER),
                MobCategory.CREATURE, CNBEntityTypes.CINDERSHELL, 40, 2, 8);

        // ── END WHALE ───────────────────────────────────────────────────
        BiomeModifications.addSpawn(
                ctx -> ctx.hasTag(BiomeTags.IS_END),
                MobCategory.CREATURE, CNBEntityTypes.END_WHALE, 10, 1, 1);
        // Spawn cost: 400 charge, 1.0 energy budget
        BiomeModifications.addProperties(
                ctx -> ctx.hasTag(BiomeTags.IS_END),
                (selectionContext, ctx) ->
                        ctx.getSpawnProperties().addMobCharge(CNBEntityTypes.END_WHALE, 400.0D, 1.0D));

        // ── LILYTAD ─────────────────────────────────────────────────────
        BiomeModifications.addSpawn(
                AddCostedSpawnsBiomeModifier::isSwamp,
                MobCategory.CREATURE, CNBEntityTypes.LILYTAD, 68, 1, 1);

        // ── LITTLE GREBE ────────────────────────────────────────────────
        BiomeModifications.addSpawn(
                ctx -> ctx.hasTag(BiomeTags.IS_RIVER),
                MobCategory.CREATURE, CNBEntityTypes.LITTLE_GREBE, 53, 2, 3);

        // ── LIZARD ──────────────────────────────────────────────────────
        BiomeModifications.addSpawn(
                ctx -> ctx.hasTag(BiomeTags.IS_BADLANDS),
                MobCategory.CREATURE, CNBEntityTypes.LIZARD, 23, 1, 4);
        BiomeModifications.addSpawn(
                AddCostedSpawnsBiomeModifier::isDesert,
                MobCategory.CREATURE, CNBEntityTypes.LIZARD, 23, 1, 4);
        BiomeModifications.addSpawn(
                ctx -> ctx.hasTag(BiomeTags.IS_JUNGLE),
                MobCategory.CREATURE, CNBEntityTypes.LIZARD, 150, 1, 4);
        BiomeModifications.addSpawn(
                AddCostedSpawnsBiomeModifier::isMushroom,
                MobCategory.CREATURE, CNBEntityTypes.LIZARD, 15, 1, 4);

        // ── MINIPAD ─────────────────────────────────────────────────────
        BiomeModifications.addSpawn(
                AddCostedSpawnsBiomeModifier::isSwamp,
                MobCategory.CREATURE, CNBEntityTypes.MINIPAD, 30, 3, 6);

        // ── SPORELING (overworld) ────────────────────────────────────────
        BiomeModifications.addSpawn(
                AddCostedSpawnsBiomeModifier::isMushroom,
                MobCategory.CREATURE, CNBEntityTypes.SPORELING, 30, 3, 5);
        BiomeModifications.addSpawn(
                AddCostedSpawnsBiomeModifier::isSwamp,
                MobCategory.CREATURE, CNBEntityTypes.SPORELING, 38, 3, 5);
        BiomeModifications.addSpawn(
                AddCostedSpawnsBiomeModifier::isLushCaves,
                MobCategory.CREATURE, CNBEntityTypes.SPORELING, 90, 3, 5);
        BiomeModifications.addSpawn(
                AddCostedSpawnsBiomeModifier::isDarkForest,
                MobCategory.CREATURE, CNBEntityTypes.SPORELING, 105, 3, 5);
        // ── SPORELING (nether) ───────────────────────────────────────────
        BiomeModifications.addSpawn(
                AddCostedSpawnsBiomeModifier::isNetherWastes,
                MobCategory.MONSTER, CNBEntityTypes.SPORELING, 90, 2, 4);
        BiomeModifications.addSpawn(
                AddCostedSpawnsBiomeModifier::isWarpedForest,
                MobCategory.MONSTER, CNBEntityTypes.SPORELING, 3, 2, 4);
        BiomeModifications.addSpawn(
                AddCostedSpawnsBiomeModifier::isCrimsonForest,
                MobCategory.MONSTER, CNBEntityTypes.SPORELING, 180, 2, 4);

        // ── YETI ────────────────────────────────────────────────────────
        // Forge: #forge:is_snowy AND #forge:is_mountain
        // Vanilla: IS_MOUNTAIN tag + frozen/snowy path check
        BiomeModifications.addSpawn(
                AddCostedSpawnsBiomeModifier::isSnowyMountain,
                MobCategory.CREATURE, CNBEntityTypes.YETI, 7, 2, 3);
    }

    // ── Biome predicate helpers ─────────────────────────────────────────

    /** {@code minecraft:desert} — vanilla has no desert biome tag. */
    private static boolean isDesert(BiomeSelectionContext ctx) {
        return matchesMinecraft(ctx, "desert");
    }

    /** {@code minecraft:swamp} and {@code minecraft:mangrove_swamp}. */
    private static boolean isSwamp(BiomeSelectionContext ctx) {
        return matchesMinecraft(ctx, "swamp") || matchesMinecraft(ctx, "mangrove_swamp");
    }

    /** {@code minecraft:mushroom_fields}. */
    private static boolean isMushroom(BiomeSelectionContext ctx) {
        return matchesMinecraft(ctx, "mushroom_fields");
    }

    private static boolean isLushCaves(BiomeSelectionContext ctx) {
        return matchesMinecraft(ctx, "lush_caves");
    }

    private static boolean isDarkForest(BiomeSelectionContext ctx) {
        return matchesMinecraft(ctx, "dark_forest");
    }

    private static boolean isNetherWastes(BiomeSelectionContext ctx) {
        return matchesMinecraft(ctx, "nether_wastes");
    }

    private static boolean isWarpedForest(BiomeSelectionContext ctx) {
        return matchesMinecraft(ctx, "warped_forest");
    }

    private static boolean isCrimsonForest(BiomeSelectionContext ctx) {
        return matchesMinecraft(ctx, "crimson_forest");
    }

    /**
     * Biomes that are both mountainous ({@link BiomeTags#IS_MOUNTAIN}) and
     * snowy/frozen — a reasonable approximation of Forge's
     * {@code #forge:is_snowy AND #forge:is_mountain}.
     */
    private static boolean isSnowyMountain(BiomeSelectionContext ctx) {
        if (!ctx.hasTag(BiomeTags.IS_MOUNTAIN)) return false;
        ResourceLocation loc = ctx.getBiomeKey().location();
        if (!loc.getNamespace().equals("minecraft")) return false;
        String path = loc.getPath();
        // Mountain biomes that are snowy or frozen in 1.20.1
        return path.equals("frozen_peaks")
                || path.equals("jagged_peaks")
                || path.equals("snowy_slopes")
                || path.equals("grove");
    }

    /** Returns true when the biome key is {@code minecraft:<path>}. */
    private static boolean matchesMinecraft(BiomeSelectionContext ctx, String path) {
        ResourceLocation loc = ctx.getBiomeKey().location();
        return loc.getNamespace().equals("minecraft") && loc.getPath().equals(path);
    }
}
