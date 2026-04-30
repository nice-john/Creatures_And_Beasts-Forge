package com.cgessinger.creaturesandbeasts.client.armor.model;

import com.cgessinger.creaturesandbeasts.items.SporelingBackpackItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SporelingBackpackModel extends GeoModel<SporelingBackpackItem> {
    private static final ResourceLocation SPORELING_BACKPACK_MODEL = ResourceLocation.fromNamespaceAndPath("cnb", "geo/armor/sporeling_backpack.geo.json");
    private static final ResourceLocation SPORELING_BACKPACK_TEXTURE = ResourceLocation.fromNamespaceAndPath("cnb", "textures/armor/sporeling_backpack.png");
    private static final ResourceLocation SPORELING_BACKPACK_ANIMATION = ResourceLocation.fromNamespaceAndPath("cnb", "animations/sporeling_backpack.json");

    @Override
    public ResourceLocation getModelResource(SporelingBackpackItem animatable) {
        return SPORELING_BACKPACK_MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(SporelingBackpackItem animatable) {
        return SPORELING_BACKPACK_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(SporelingBackpackItem animatable) {
        return SPORELING_BACKPACK_ANIMATION;
    }
}
