package com.cgessinger.creaturesandbeasts.util;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.apache.commons.lang3.tuple.Pair;

import org.jetbrains.annotations.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class LizardType {
    private static final Map<Identifier, LizardType> LIZARD_TYPES = new LinkedHashMap<>();

    private Identifier id;
    private Pair<Identifier, Identifier> textures;
    private Supplier<Item> spawnItem;

    public LizardType(@Nullable Item spawnItem, Identifier id, Identifier texture, Identifier sadTexture) {
        this(() -> spawnItem, id, texture, sadTexture);
    }

    public LizardType(@Nullable Supplier<Item> spawnItem, Identifier id, Identifier texture, Identifier sadTexture) {
        this(spawnItem, id, Pair.of(texture, sadTexture));
    }

    public LizardType(@Nullable Supplier<Item> spawnItem, Identifier id, Pair<Identifier, Identifier> textures) {
        this.id = id;
        this.textures = textures;
        this.spawnItem = spawnItem;
    }

    @Nullable
    public Item getSpawnItem() {
        final Item item = this.spawnItem.get();
        if (item == null || item.equals(Items.AIR)) {
            return null;
        } else {
            return item;
        }
    }

    public void setSpawnItem(@Nullable Item spawnItem) {
        this.spawnItem = () -> spawnItem;
    }


    public Identifier getId() {
        return id;
    }

    public void setId(Identifier id) {
        this.id = id;
    }

    public Identifier getTextureLocation() {
        return this.textures.getLeft();
    }

    public void setTextureLocation(Identifier textureLocation) {
        this.textures = Pair.of(textureLocation, this.textures.getRight());
    }

    public Identifier getSadTextureLocation() {
        return this.textures.getRight();
    }

    public void setSadTextureLocation(Identifier textureLocation) {
        this.textures = Pair.of(this.textures.getLeft(), textureLocation);
    }

    public static LizardType registerLizardType(LizardType lizardType) {
        Identifier id = lizardType.getId();
        if (LIZARD_TYPES.containsKey(id)) {
            throw new IllegalStateException(String.format("%s already exists in the LizardType registry.", id.toString()));
        }
        LIZARD_TYPES.put(id, lizardType);
        return lizardType;
    }

    @Nullable
    public static LizardType getById(@Nullable String id) {
        if (id == null) {
            return null;
        } else {
            return getById(Identifier.tryParse(id));
        }
    }

    @Nullable
    public static LizardType getById(@Nullable Identifier id) {
        return LIZARD_TYPES.get(id);
    }

    public boolean equals(Object obj) {
        if (obj instanceof LizardType) {
            final LizardType type = (LizardType) obj;
            return type.getId().equals(this.getId()) &&
                    type.getTextureLocation().equals(this.getTextureLocation());
        } else {
            return false;
        }
    }
}
