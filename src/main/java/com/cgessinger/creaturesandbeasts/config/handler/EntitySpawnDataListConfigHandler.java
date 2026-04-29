package com.cgessinger.creaturesandbeasts.config.handler;

import com.cgessinger.creaturesandbeasts.config.EntitySpawnData;
import com.electronwill.nightconfig.core.Config;
import com.google.common.collect.ImmutableList;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Utility that converts between {@code List<EntitySpawnData>} and
 * the Night Config {@code List<Config>} representation used in TOML.
 */
public class EntitySpawnDataListConfigHandler {
    public static final EntitySpawnDataListConfigHandler INSTANCE = new EntitySpawnDataListConfigHandler();

    private EntitySpawnDataListConfigHandler() {}

    public List<Config> serialize(List<EntitySpawnData> list) {
        if (list == null) return ImmutableList.of();
        return list.stream()
                .map(EntitySpawnData::toConfig)
                .collect(ImmutableList.toImmutableList());
    }

    @Nullable
    public List<EntitySpawnData> deserialize(@Nullable List<Config> obj) {
        if (obj == null) return null;
        return obj.stream()
                .map(EntitySpawnData::fromConfig)
                .collect(ImmutableList.toImmutableList());
    }
}
