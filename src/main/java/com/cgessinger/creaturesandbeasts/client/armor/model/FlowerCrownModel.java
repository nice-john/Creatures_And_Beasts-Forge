package com.cgessinger.creaturesandbeasts.client.armor.model;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.items.FlowerCrownItem;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.model.GeoModel;

@OnlyIn(Dist.CLIENT)
public class FlowerCrownModel extends GeoModel<FlowerCrownItem> {
    private static final ResourceLocation FLOWER_CROWN_MODEL = ResourceLocation.fromNamespaceAndPath("cnb", "geo/armor/flower_crown.geo.json");
    private static final ResourceLocation FLOWER_CROWN_TEXTURE = ResourceLocation.fromNamespaceAndPath("cnb", "textures/armor/flower_crown.png");
    private static final ResourceLocation FLOWER_CROWN_ANIMATION = ResourceLocation.fromNamespaceAndPath("cnb", "animations/flower_crown.json");

    @Override
    public ResourceLocation getModelResource(FlowerCrownItem animatable) {
        return FLOWER_CROWN_MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(FlowerCrownItem animatable) {
        return FLOWER_CROWN_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(FlowerCrownItem animatable) {
        return FLOWER_CROWN_ANIMATION;
    }
}

