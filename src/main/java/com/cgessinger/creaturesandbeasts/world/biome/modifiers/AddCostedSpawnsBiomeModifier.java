package com.cgessinger.creaturesandbeasts.world.biome.modifiers;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.init.CNBEntityTypes;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;

/**
 * Registers all mob spawn entries via the Fabric Biome Modifications API.
 * Called once from {@link com.cgessinger.creaturesandbeasts.init.CNBBiomeModifiers#register()}.
 *
 * <p>Forge tags ({@code #forge:is_desert}, {@code #forge:is_swamp}, etc.) have no direct
 * vanilla equivalent on Fabric, but Fabric's
 * {@link ConventionalBiomeTags} ships {@code c:} namespace convention tags that vanilla
 * biomes are auto-tagged into and modded biomes opt into. Using those keeps modded
 * biome compatibility on par with the Forge build.
 */
public final class AddCostedSpawnsBiomeModifier {

    private AddCostedSpawnsBiomeModifier() {}

    public static void registerFabricModifications() {

        // ── CACTEM ──────────────────────────────────────────────────────
        BiomeModifications.addSpawn(
                ctx -> ctx.hasTag(ConventionalBiomeTags.BADLANDS),
                MobCategory.CREATURE, CNBEntityTypes.CACTEM, 10, 6, 13);
        BiomeModifications.addSpawn(
                ctx -> ctx.hasTag(ConventionalBiomeTags.DESERT),
                MobCategory.CREATURE, CNBEntityTypes.CACTEM, 10, 6, 13);

        // ── CINDERSHELL ─────────────────────────────────────────────────
        // Lower weight (40 -> 8) and pack size (2-8 -> 1-3) per user feedback —
        // packs of six showed up too frequently. Mob-charge throttle (cost 0.7,
        // budget 60.0) caps local density so even when biome cycles fire we don't
        // get cluster bursts. Tuning mirrors vanilla strider.
        BiomeModifications.addSpawn(
                ctx -> ctx.hasTag(ConventionalBiomeTags.IN_NETHER),
                MobCategory.CREATURE, CNBEntityTypes.CINDERSHELL, 8, 1, 3);
        BiomeModifications.create(ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "cindershell_spawn_cost"))
                .add(ModificationPhase.ADDITIONS,
                        ctx -> ctx.hasTag(ConventionalBiomeTags.IN_NETHER),
                        (selector, modContext) ->
                                modContext.getSpawnSettings().setSpawnCost(CNBEntityTypes.CINDERSHELL, 0.7D, 60.0D));

        // ── END WHALE ───────────────────────────────────────────────────
        BiomeModifications.addSpawn(
                ctx -> ctx.hasTag(ConventionalBiomeTags.IN_THE_END),
                MobCategory.CREATURE, CNBEntityTypes.END_WHALE, 10, 1, 1);
        // Mob-charge throttle. Was cost=400 budget=1.0 (ratio 160000), which gave
        // an effective cutoff radius of ~400 blocks - one whale would block every
        // other whale within the player's entire tracked range. User reported 20
        // minutes of end exploration with zero whales.
        // 3.0/6.0 (ratio 1.5) is a soft soft-cap: whales still throttle in dense
        // clusters but exploration encounters work normally. Vanilla strider for
        // reference uses 0.7/60 (ratio 0.008, basically no throttle).
        BiomeModifications.create(ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "end_whale_spawn_cost"))
                .add(ModificationPhase.ADDITIONS,
                        ctx -> ctx.hasTag(ConventionalBiomeTags.IN_THE_END),
                        (selector, modContext) ->
                                modContext.getSpawnSettings().setSpawnCost(CNBEntityTypes.END_WHALE, 3.0D, 6.0D));

        // ── LILYTAD ─────────────────────────────────────────────────────
        BiomeModifications.addSpawn(
                ctx -> ctx.hasTag(ConventionalBiomeTags.SWAMP),
                MobCategory.CREATURE, CNBEntityTypes.LILYTAD, 68, 1, 1);

        // ── LITTLE GREBE ────────────────────────────────────────────────
        BiomeModifications.addSpawn(
                ctx -> ctx.hasTag(ConventionalBiomeTags.RIVER),
                MobCategory.CREATURE, CNBEntityTypes.LITTLE_GREBE, 53, 2, 3);

        // ── LIZARD ──────────────────────────────────────────────────────
        BiomeModifications.addSpawn(
                ctx -> ctx.hasTag(ConventionalBiomeTags.BADLANDS),
                MobCategory.CREATURE, CNBEntityTypes.LIZARD, 23, 1, 4);
        // Desert weight 23 -> 12 + mob-charge throttle. Vanilla deserts have no
        // other CREATURE entries so the lizard was the sole pick at every CREATURE
        // cycle there, and "Oh The Biomes We've Gone" tags many of its biomes
        // c:is_desert which multiplied the effect. Strider-style charge cap
        // (cost 0.7, budget 60.0) holds local density regardless of how many
        // desert biomes the player wanders through.
        BiomeModifications.addSpawn(
                ctx -> ctx.hasTag(ConventionalBiomeTags.DESERT),
                MobCategory.CREATURE, CNBEntityTypes.LIZARD, 12, 1, 4);
        BiomeModifications.create(ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "lizard_desert_spawn_cost"))
                .add(ModificationPhase.ADDITIONS,
                        ctx -> ctx.hasTag(ConventionalBiomeTags.DESERT),
                        (selector, modContext) ->
                                modContext.getSpawnSettings().setSpawnCost(CNBEntityTypes.LIZARD, 0.7D, 60.0D));
        BiomeModifications.addSpawn(
                ctx -> ctx.hasTag(ConventionalBiomeTags.JUNGLE),
                MobCategory.CREATURE, CNBEntityTypes.LIZARD, 150, 1, 4);
        BiomeModifications.addSpawn(
                ctx -> ctx.hasTag(ConventionalBiomeTags.MUSHROOM),
                MobCategory.CREATURE, CNBEntityTypes.LIZARD, 15, 1, 4);

        // ── MINIPAD ─────────────────────────────────────────────────────
        BiomeModifications.addSpawn(
                ctx -> ctx.hasTag(ConventionalBiomeTags.SWAMP),
                MobCategory.CREATURE, CNBEntityTypes.MINIPAD, 30, 3, 6);

        // ── SPORELING (overworld) ────────────────────────────────────────
        BiomeModifications.addSpawn(
                ctx -> ctx.hasTag(ConventionalBiomeTags.MUSHROOM),
                MobCategory.CREATURE, CNBEntityTypes.SPORELING, 30, 3, 5);
        BiomeModifications.addSpawn(
                ctx -> ctx.hasTag(ConventionalBiomeTags.SWAMP),
                MobCategory.CREATURE, CNBEntityTypes.SPORELING, 38, 3, 5);
        // No general convention tag for "lush caves" or "dark forest" — keep specific.
        BiomeModifications.addSpawn(
                AddCostedSpawnsBiomeModifier::isLushCaves,
                MobCategory.CREATURE, CNBEntityTypes.SPORELING, 90, 3, 5);
        BiomeModifications.addSpawn(
                AddCostedSpawnsBiomeModifier::isDarkForest,
                MobCategory.CREATURE, CNBEntityTypes.SPORELING, 105, 3, 5);
        // ── SPORELING (nether) ───────────────────────────────────────────
        // Per-biome weights differ (60/2/120) so we keep the matchers specific
        // rather than collapsing to ConventionalBiomeTags.IN_NETHER.
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
        // Forge: #forge:is_snowy AND #forge:is_mountain (intersection).
        // Fabric: c:snowy AND c:mountain — modded snowy mountain biomes
        // qualify automatically as long as the modder tagged them in.
        BiomeModifications.addSpawn(
                ctx -> ctx.hasTag(ConventionalBiomeTags.SNOWY)
                        && ctx.hasTag(ConventionalBiomeTags.MOUNTAIN),
                MobCategory.CREATURE, CNBEntityTypes.YETI, 7, 2, 3);
    }

    // ── Biome predicate helpers (vanilla-only, for biomes without good c: tags) ──

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

    /** Returns true when the biome key is {@code minecraft:<path>}. */
    private static boolean matchesMinecraft(BiomeSelectionContext ctx, String path) {
        ResourceLocation loc = ctx.getBiomeKey().location();
        return loc.getNamespace().equals("minecraft") && loc.getPath().equals(path);
    }
}
