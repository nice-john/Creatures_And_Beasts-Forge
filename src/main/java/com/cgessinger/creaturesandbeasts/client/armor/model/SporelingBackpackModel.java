package com.cgessinger.creaturesandbeasts.client.armor.model;

import com.cgessinger.creaturesandbeasts.items.SporelingBackpackItem;
import net.minecraft.resources.Identifier;
import com.geckolib.model.GeoModel;

public class SporelingBackpackModel extends GeoModel<SporelingBackpackItem> {
    private static final Identifier SPORELING_BACKPACK_MODEL = Identifier.fromNamespaceAndPath("cnb", "geo/armor/sporeling_backpack.geo.json");
    private static final Identifier SPORELING_BACKPACK_TEXTURE = Identifier.fromNamespaceAndPath("cnb", "textures/armor/sporeling_backpack.png");
    private static final Identifier SPORELING_BACKPACK_ANIMATION = Identifier.fromNamespaceAndPath("cnb", "animations/sporeling_backpack.animation.json");

    @Override
    public Identifier getModelResource(SporelingBackpackItem animatable) {
        return SPORELING_BACKPACK_MODEL;
    }

    @Override
    public Identifier getTextureResource(SporelingBackpackItem animatable) {
        return SPORELING_BACKPACK_TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(SporelingBackpackItem animatable) {
        return SPORELING_BACKPACK_ANIMATION;
    }
}
