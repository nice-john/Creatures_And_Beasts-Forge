package com.cgessinger.creaturesandbeasts.util;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class LilytadType {
    private static final Map<Identifier, LilytadType> LILYTAD_TYPES = new LinkedHashMap<>();

    private Identifier id;
    private Identifier texture;
    private Supplier<Item> shearItem;

    public LilytadType(@Nullable Item shearItem, Identifier id, Identifier texture) {
        this(() -> shearItem, id, texture);
    }

    public LilytadType(@Nullable Supplier<Item> shearItem, Identifier id, Identifier texture) {
        this.id = id;
        this.texture = texture;
        this.shearItem = shearItem;
    }

    @CheckForNull
    public Item getShearItem() {
        final Item item = this.shearItem.get();
        if (item == null || item.equals(Items.AIR)) {
            return null;
        } else {
            return item;
        }
    }

    public void setShearItem(@Nullable Item shearItem) {
        this.shearItem = () -> shearItem;
    }


    public Identifier getId() {
        return id;
    }

    public void setId(Identifier id) {
        this.id = id;
    }

    public Identifier getTextureLocation() {
        return this.texture;
    }

    public void setTextureLocation(Identifier textureLocation) {
        this.texture = textureLocation;
    }

    public static LilytadType registerLilytadType(LilytadType minipadType) {
        Identifier id = minipadType.getId();
        if (LILYTAD_TYPES.containsKey(id)) {
            throw new IllegalStateException(String.format("%s already exists in the LilytadType registry.", id.toString()));
        }
        LILYTAD_TYPES.put(id, minipadType);
        return minipadType;
    }

    @Nullable
    public static LilytadType getById(@Nullable String id) {
        if (id == null) {
            return null;
        } else {
            return getById(Identifier.tryParse(id));
        }
    }

    @Nullable
    public static LilytadType getById(@Nullable Identifier id) {
        return LILYTAD_TYPES.get(id);
    }

    public boolean equals(Object obj) {
        if (obj instanceof LilytadType) {
            final LilytadType type = (LilytadType) obj;
            return type.getId().equals(this.getId()) &&
                    type.getTextureLocation().equals(this.getTextureLocation());
        } else {
            return false;
        }
    }
}
