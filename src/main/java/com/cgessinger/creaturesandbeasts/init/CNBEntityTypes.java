package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class CNBEntityTypes {

    /* CREATURES */
    public static final EntityType<LittleGrebeEntity> LITTLE_GREBE = register("little_grebe",
            EntityType.Builder.of(LittleGrebeEntity::new, MobCategory.CREATURE).sized(0.5f, 0.6f));

    public static final EntityType<LizardEntity> LIZARD = register("lizard",
            EntityType.Builder.of(LizardEntity::new, MobCategory.CREATURE).sized(0.52f, 0.3f));

    public static final EntityType<LilytadEntity> LILYTAD = register("lilytad",
            EntityType.Builder.of(LilytadEntity::new, MobCategory.CREATURE).sized(0.7f, 1.02f));

    public static final EntityType<SporelingEntity> SPORELING = register("sporeling",
            EntityType.Builder.of(SporelingEntity::new, MobCategory.CREATURE).sized(0.6f, 0.85f));

    public static final EntityType<MinipadEntity> MINIPAD = register("minipad",
            EntityType.Builder.of(MinipadEntity::new, MobCategory.CREATURE).sized(0.6f, 0.7f));

    // canSpawnFarFromPlayer(): NaturalSpawner.canSpawnMobAt otherwise rejects
    // candidates >128 blocks from the nearest player. End whales are large flying
    // entities meant to soar — capping spawn attempts to a 128-block disk and
    // watching them fly off cuts encounter rate dramatically.
    public static final EntityType<EndWhaleEntity> END_WHALE = register("end_whale",
            EntityType.Builder.of(EndWhaleEntity::new, MobCategory.CREATURE).canSpawnFarFromPlayer().sized(3.0f, 1.5f));

    public static final EntityType<CactemEntity> CACTEM = register("cactem",
            EntityType.Builder.of(CactemEntity::new, MobCategory.CREATURE).sized(0.75f, 1.0f));

    public static final EntityType<YetiEntity> YETI = register("yeti",
            EntityType.Builder.of(YetiEntity::new, MobCategory.CREATURE).sized(1.55f, 2.05f));

    public static final EntityType<CindershellEntity> CINDERSHELL = register("cindershell",
            EntityType.Builder.of(CindershellEntity::new, MobCategory.CREATURE).fireImmune().sized(1.25f, 1.45f));

    /* PROJECTILES */
    public static final EntityType<LizardEggEntity> LIZARD_EGG = register("lizard_egg",
            EntityType.Builder.<LizardEggEntity>of(LizardEggEntity::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f).clientTrackingRange(4).updateInterval(10));

    public static final EntityType<ThrownCactemSpearEntity> THROWN_CACTEM_SPEAR = register("thrown_cactem_spear",
            EntityType.Builder.<ThrownCactemSpearEntity>of(ThrownCactemSpearEntity::new, MobCategory.MISC)
                    .sized(0.4f, 0.4f).clientTrackingRange(4).updateInterval(10));

    private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE,
                Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, name));
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    public static void register() {
        // Calling this method triggers static initialisation of all fields above.
        CreaturesAndBeasts.LOGGER.debug("Registered CNB entity types");
    }

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(CINDERSHELL,       CindershellEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(SPORELING,         SporelingEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(LITTLE_GREBE,      LittleGrebeEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(LILYTAD,           LilytadEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(LIZARD,            LizardEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(YETI,              YetiEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(MINIPAD,           MinipadEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(END_WHALE,         EndWhaleEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(CACTEM,            CactemEntity.createAttributes());
    }
}
