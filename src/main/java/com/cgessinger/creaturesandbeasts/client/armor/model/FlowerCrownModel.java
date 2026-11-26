package com.cgessinger.creaturesandbeasts.client.armor.model;

import com.cgessinger.creaturesandbeasts.client.model.CnbGeoModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.items.FlowerCrownItem;
import net.minecraft.resources.Identifier;


@Environment(EnvType.CLIENT)
public class FlowerCrownModel extends CnbGeoModel<FlowerCrownItem> {
    private static final Identifier FLOWER_CROWN_MODEL = Identifier.fromNamespaceAndPath("cnb", "geo/armor/flower_crown");
    private static final Identifier FLOWER_CROWN_TEXTURE = Identifier.fromNamespaceAndPath("cnb", "textures/armor/flower_crown.png");
    private static final Identifier FLOWER_CROWN_ANIMATION = Identifier.fromNamespaceAndPath("cnb", "flower_crown");

    @Override
    protected Identifier resolveModel(FlowerCrownItem animatable) {
        return FLOWER_CROWN_MODEL;
    }

    @Override
    protected Identifier resolveTexture(FlowerCrownItem animatable) {
        return FLOWER_CROWN_TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(FlowerCrownItem animatable) {
        return FLOWER_CROWN_ANIMATION;
    }
}

