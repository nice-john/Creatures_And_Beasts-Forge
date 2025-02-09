package com.cgessinger.creaturesandbeasts.client.armor.model;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.items.FlowerCrownItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.model.GeoModel;

@OnlyIn(Dist.CLIENT)
public class FlowerCrownModel extends GeoModel<FlowerCrownItem> {
    private static final ResourceLocation FLOWER_CROWN_MODEL = new ResourceLocation("cnb", "geo/armor/flower_crown.geo.json");
    private static final ResourceLocation FLOWER_CROWN_TEXTURE = new ResourceLocation("cnb", "textures/armor/flower_crown.png");
    private static final ResourceLocation FLOWER_CROWN_ANIMATION = new ResourceLocation("cnb", "animations/flower_crown.json");

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

