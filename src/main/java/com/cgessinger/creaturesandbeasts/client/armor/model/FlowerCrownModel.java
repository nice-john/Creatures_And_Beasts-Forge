package com.cgessinger.creaturesandbeasts.client.armor.model;

import com.cgessinger.creaturesandbeasts.items.FlowerCrownItem;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
public class FlowerCrownModel extends GeoModel<FlowerCrownItem> {
    private static final Identifier FLOWER_CROWN_MODEL = Identifier.fromNamespaceAndPath("cnb", "geo/armor/flower_crown.geo.json");
    private static final Identifier FLOWER_CROWN_TEXTURE = Identifier.fromNamespaceAndPath("cnb", "textures/armor/flower_crown.png");
    private static final Identifier FLOWER_CROWN_ANIMATION = Identifier.fromNamespaceAndPath("cnb", "animations/flower_crown.animation.json");

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return FLOWER_CROWN_MODEL;
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return FLOWER_CROWN_TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(FlowerCrownItem animatable) {
        return FLOWER_CROWN_ANIMATION;
    }
}
