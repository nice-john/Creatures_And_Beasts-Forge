package com.cgessinger.creaturesandbeasts.client.armor.model;

import com.cgessinger.creaturesandbeasts.client.model.CnbGeoModel;
import com.cgessinger.creaturesandbeasts.items.SporelingBackpackItem;
import net.minecraft.resources.Identifier;


public class SporelingBackpackModel extends CnbGeoModel<SporelingBackpackItem> {
    private static final Identifier SPORELING_BACKPACK_MODEL = Identifier.fromNamespaceAndPath("cnb", "geo/armor/sporeling_backpack");
    private static final Identifier SPORELING_BACKPACK_TEXTURE = Identifier.fromNamespaceAndPath("cnb", "textures/armor/sporeling_backpack.png");
    private static final Identifier SPORELING_BACKPACK_ANIMATION = Identifier.fromNamespaceAndPath("cnb", "sporeling_backpack");

    @Override
    protected Identifier resolveModel(SporelingBackpackItem animatable) {
        return SPORELING_BACKPACK_MODEL;
    }

    @Override
    protected Identifier resolveTexture(SporelingBackpackItem animatable) {
        return SPORELING_BACKPACK_TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(SporelingBackpackItem animatable) {
        return SPORELING_BACKPACK_ANIMATION;
    }
}
