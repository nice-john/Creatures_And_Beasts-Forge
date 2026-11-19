package com.cgessinger.creaturesandbeasts.client.armor.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.items.FlowerCrownItem;
import net.minecraft.resources.Identifier;
import com.geckolib.model.GeoModel;

@Environment(EnvType.CLIENT)
public class FlowerCrownModel extends GeoModel<FlowerCrownItem> {
    private static final Identifier FLOWER_CROWN_MODEL = Identifier.fromNamespaceAndPath("cnb", "geo/armor/flower_crown.geo.json");
    private static final Identifier FLOWER_CROWN_TEXTURE = Identifier.fromNamespaceAndPath("cnb", "textures/armor/flower_crown.png");
    private static final Identifier FLOWER_CROWN_ANIMATION = Identifier.fromNamespaceAndPath("cnb", "animations/flower_crown.animation.json");

    @Override
    public Identifier getModelResource(FlowerCrownItem animatable) {
        return FLOWER_CROWN_MODEL;
    }

    @Override
    public Identifier getTextureResource(FlowerCrownItem animatable) {
        return FLOWER_CROWN_TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(FlowerCrownItem animatable) {
        return FLOWER_CROWN_ANIMATION;
    }
}

