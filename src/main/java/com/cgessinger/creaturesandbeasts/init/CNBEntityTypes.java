package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.CactemEntity;
import com.cgessinger.creaturesandbeasts.entities.CindershellEntity;
import com.cgessinger.creaturesandbeasts.entities.EndWhaleEntity;
import com.cgessinger.creaturesandbeasts.entities.LilytadEntity;
import com.cgessinger.creaturesandbeasts.entities.LittleGrebeEntity;
import com.cgessinger.creaturesandbeasts.entities.LizardEggEntity;
import com.cgessinger.creaturesandbeasts.entities.LizardEntity;
import com.cgessinger.creaturesandbeasts.entities.MinipadEntity;
import com.cgessinger.creaturesandbeasts.entities.SporelingEntity;
import com.cgessinger.creaturesandbeasts.entities.ThrownCactemSpearEntity;
import com.cgessinger.creaturesandbeasts.entities.YetiEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CNBEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, CreaturesAndBeasts.MOD_ID);

    /* CREATURES */
    public static final DeferredHolder<EntityType<?>, EntityType<LittleGrebeEntity>> LITTLE_GREBE =
            ENTITY_TYPES.register("little_grebe", () -> EntityType.Builder.of(LittleGrebeEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.6f).build(name("little_grebe")));
    public static final DeferredHolder<EntityType<?>, EntityType<LizardEntity>> LIZARD =
            ENTITY_TYPES.register("lizard", () -> EntityType.Builder.of(LizardEntity::new, MobCategory.CREATURE)
                    .sized(0.52f, 0.3f).build(name("lizard")));
    public static final DeferredHolder<EntityType<?>, EntityType<LilytadEntity>> LILYTAD =
            ENTITY_TYPES.register("lilytad", () -> EntityType.Builder.of(LilytadEntity::new, MobCategory.CREATURE)
                    .sized(0.7f, 1.02f).build(name("lilytad")));
    public static final DeferredHolder<EntityType<?>, EntityType<SporelingEntity>> SPORELING =
            ENTITY_TYPES.register("sporeling", () -> EntityType.Builder.of(SporelingEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 0.85f).build(name("sporeling")));
    public static final DeferredHolder<EntityType<?>, EntityType<MinipadEntity>> MINIPAD =
            ENTITY_TYPES.register("minipad", () -> EntityType.Builder.of(MinipadEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 0.7f).build(name("minipad")));
    // canSpawnFarFromPlayer() on the builder: default is false, which means
    // NaturalSpawner.canSpawnMobAt rejects spawn candidates >128 blocks from
    // the nearest player. End whales are large flying entities meant to soar —
    // confining spawns to a 128-block disk and watching them fly off cuts
    // encounter rate dramatically.
    public static final DeferredHolder<EntityType<?>, EntityType<EndWhaleEntity>> END_WHALE =
            ENTITY_TYPES.register("end_whale", () -> EntityType.Builder.of(EndWhaleEntity::new, MobCategory.CREATURE)
                    .sized(3.0f, 1.5f).canSpawnFarFromPlayer().build(name("end_whale")));
    public static final DeferredHolder<EntityType<?>, EntityType<CactemEntity>> CACTEM =
            ENTITY_TYPES.register("cactem", () -> EntityType.Builder.of(CactemEntity::new, MobCategory.CREATURE)
                    .sized(0.75F, 1.0F).build(name("cactem")));
    public static final DeferredHolder<EntityType<?>, EntityType<YetiEntity>> YETI =
            ENTITY_TYPES.register("yeti", () -> EntityType.Builder.of(YetiEntity::new, MobCategory.CREATURE)
                    .sized(1.55f, 2.05f).build(name("yeti")));
    public static final DeferredHolder<EntityType<?>, EntityType<CindershellEntity>> CINDERSHELL =
            ENTITY_TYPES.register("cindershell", () -> EntityType.Builder.of(CindershellEntity::new, MobCategory.CREATURE)
                    .sized(1.25f, 1.45f).fireImmune().build(name("cindershell")));

    /* PROJECTILES */
    public static final DeferredHolder<EntityType<?>, EntityType<LizardEggEntity>> LIZARD_EGG =
            ENTITY_TYPES.register("lizard_egg", () -> EntityType.Builder.<LizardEggEntity>of(LizardEggEntity::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f).clientTrackingRange(4).updateInterval(10).build(name("lizard_egg")));
    public static final DeferredHolder<EntityType<?>, EntityType<ThrownCactemSpearEntity>> THROWN_CACTEM_SPEAR =
            ENTITY_TYPES.register("thrown_cactem_spear", () -> EntityType.Builder.<ThrownCactemSpearEntity>of(ThrownCactemSpearEntity::new, MobCategory.MISC)
                    .sized(0.4F, 0.4F).clientTrackingRange(4).updateInterval(10).build(name("cactem_spear")));

    private static String name(String n) {
        return CreaturesAndBeasts.MOD_ID + ":" + n;
    }
}
