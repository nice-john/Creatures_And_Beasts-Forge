package com.cgessinger.creaturesandbeasts.util;

import net.minecraft.resources.Identifier;
import org.apache.commons.lang3.tuple.Pair;

import org.jetbrains.annotations.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

public class SporelingType {
    private static final Map<Identifier, SporelingType> SPORELING_TYPES = new LinkedHashMap<>();

    private Identifier id;
    private Pair<Identifier, Identifier> modelAndTexture;
    private SporelingHostility hostility;

    public SporelingType(Identifier id, Identifier model, Identifier texture, SporelingHostility hostility) {
        this(id, Pair.of(model, texture), hostility);
    }

    public SporelingType(Identifier id, Pair<Identifier, Identifier> modelAndTexture, SporelingHostility hostility) {
        this.id = id;
        this.modelAndTexture = modelAndTexture;
        this.hostility = hostility;
    }

    public Identifier getId() {
        return id;
    }

    public void setId(Identifier id) {
        this.id = id;
    }

    public Identifier getTextureLocation() {
        return this.modelAndTexture.getRight();
    }

    public void setTextureLocation(Identifier textureLocation) {
        this.modelAndTexture = Pair.of(this.modelAndTexture.getLeft(), textureLocation);
    }

    public Identifier getModelLocation() {
        return this.modelAndTexture.getLeft();
    }

    public void setModelLocation(Identifier modelLocation) {
        this.modelAndTexture = Pair.of(modelLocation, this.modelAndTexture.getRight());
    }

    public SporelingHostility getHostility() {
        return hostility;
    }

    public void setHostility(SporelingHostility hostility) {
        this.hostility = hostility;
    }

    public static SporelingType registerSporelingType(SporelingType sporelingType) {
        Identifier id = sporelingType.getId();
        if (SPORELING_TYPES.containsKey(id)) {
            throw new IllegalStateException(String.format("%s already exists in the SporelingType registry.", id.toString()));
        }
        SPORELING_TYPES.put(id, sporelingType);
        return sporelingType;
    }

    @Nullable
    public static SporelingType getById(@Nullable String id) {
        if (id == null) {
            return null;
        } else {
            return getById(Identifier.tryParse(id));
        }
    }

    @Nullable
    public static SporelingType getById(@Nullable Identifier id) {
        return SPORELING_TYPES.get(id);
    }

    public boolean equals(Object obj) {
        if (obj instanceof SporelingType) {
            final SporelingType type = (SporelingType) obj;
            return type.getId().equals(this.getId()) &&
                    type.getModelLocation().equals(this.getModelLocation()) &&
                    type.getTextureLocation().equals(this.getTextureLocation());
        } else {
            return false;
        }
    }

    public enum SporelingHostility {
        HOSTILE,
        NEUTRAL,
        FRIENDLY;
    }
}
