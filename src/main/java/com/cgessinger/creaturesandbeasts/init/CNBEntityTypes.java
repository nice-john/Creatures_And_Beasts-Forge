package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class CNBEntityTypes {

    /* CREATURES */
    public static final EntityType<LittleGrebeEntity> LITTLE_GREBE = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(CreaturesAndBeasts.MOD_ID, "little_grebe"),
            FabricEntityTypeBuilder.create(MobCategory.CREATURE, LittleGrebeEntity::new)
                    .dimensions(EntityDimensions.scalable(0.5f, 0.6f)).build());

    public static final EntityType<LizardEntity> LIZARD = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(CreaturesAndBeasts.MOD_ID, "lizard"),
            FabricEntityTypeBuilder.create(MobCategory.CREATURE, LizardEntity::new)
                    .dimensions(EntityDimensions.scalable(0.52f, 0.3f)).build());

    public static final EntityType<LilytadEntity> LILYTAD = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(CreaturesAndBeasts.MOD_ID, "lilytad"),
            FabricEntityTypeBuilder.create(MobCategory.CREATURE, LilytadEntity::new)
                    .dimensions(EntityDimensions.scalable(0.7f, 1.02f)).build());

    public static final EntityType<SporelingEntity> SPORELING = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(CreaturesAndBeasts.MOD_ID, "sporeling"),
            FabricEntityTypeBuilder.create(MobCategory.CREATURE, SporelingEntity::new)
                    .dimensions(EntityDimensions.scalable(0.6f, 0.85f)).build());

    public static final EntityType<MinipadEntity> MINIPAD = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(CreaturesAndBeasts.MOD_ID, "minipad"),
            FabricEntityTypeBuilder.create(MobCategory.CREATURE, MinipadEntity::new)
                    .dimensions(EntityDimensions.scalable(0.6f, 0.7f)).build());

    // spawnableFarFromPlayer(): NaturalSpawner.canSpawnMobAt otherwise rejects
    // candidates >128 blocks from the nearest player. End whales are large flying
    // entities meant to soar — capping spawn attempts to a 128-block disk and
    // watching them fly off cuts encounter rate dramatically.
    public static final EntityType<EndWhaleEntity> END_WHALE = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(CreaturesAndBeasts.MOD_ID, "end_whale"),
            FabricEntityTypeBuilder.create(MobCategory.CREATURE, EndWhaleEntity::new)
                    .spawnableFarFromPlayer()
                    .dimensions(EntityDimensions.scalable(3.0f, 1.5f)).build());

    public static final EntityType<CactemEntity> CACTEM = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(CreaturesAndBeasts.MOD_ID, "cactem"),
            FabricEntityTypeBuilder.create(MobCategory.CREATURE, CactemEntity::new)
                    .dimensions(EntityDimensions.scalable(0.75f, 1.0f)).build());

    public static final EntityType<YetiEntity> YETI = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(CreaturesAndBeasts.MOD_ID, "yeti"),
            FabricEntityTypeBuilder.create(MobCategory.CREATURE, YetiEntity::new)
                    .dimensions(EntityDimensions.scalable(1.55f, 2.05f)).build());

    public static final EntityType<CindershellEntity> CINDERSHELL = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(CreaturesAndBeasts.MOD_ID, "cindershell"),
            FabricEntityTypeBuilder.create(MobCategory.CREATURE, CindershellEntity::new)
                    .fireImmune()
                    .dimensions(EntityDimensions.scalable(1.25f, 1.45f)).build());

    /* PROJECTILES */
    public static final EntityType<LizardEggEntity> LIZARD_EGG = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(CreaturesAndBeasts.MOD_ID, "lizard_egg"),
            FabricEntityTypeBuilder.<LizardEggEntity>create(MobCategory.MISC, LizardEggEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                    .trackRangeChunks(4).trackedUpdateRate(10).build());

    public static final EntityType<ThrownCactemSpearEntity> THROWN_CACTEM_SPEAR = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            new ResourceLocation(CreaturesAndBeasts.MOD_ID, "thrown_cactem_spear"),
            FabricEntityTypeBuilder.<ThrownCactemSpearEntity>create(MobCategory.MISC, ThrownCactemSpearEntity::new)
                    .dimensions(EntityDimensions.fixed(0.4f, 0.4f))
                    .trackRangeChunks(4).trackedUpdateRate(10).build());

    public static void register() {
        // Calling this method triggers static initialisation of all fields above.
        CreaturesAndBeasts.LOGGER.debug("Registered CNB entity types");
    }

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(CINDERSHELL,       CindershellEntity.createAttributes().build());
        FabricDefaultAttributeRegistry.register(SPORELING,         SporelingEntity.createAttributes().build());
        FabricDefaultAttributeRegistry.register(LITTLE_GREBE,      LittleGrebeEntity.createAttributes().build());
        FabricDefaultAttributeRegistry.register(LILYTAD,           LilytadEntity.createAttributes().build());
        FabricDefaultAttributeRegistry.register(LIZARD,            LizardEntity.createAttributes().build());
        FabricDefaultAttributeRegistry.register(YETI,              YetiEntity.createAttributes().build());
        FabricDefaultAttributeRegistry.register(MINIPAD,           MinipadEntity.createAttributes().build());
        FabricDefaultAttributeRegistry.register(END_WHALE,         EndWhaleEntity.createAttributes().build());
        FabricDefaultAttributeRegistry.register(CACTEM,            CactemEntity.createAttributes().build());
    }
}
