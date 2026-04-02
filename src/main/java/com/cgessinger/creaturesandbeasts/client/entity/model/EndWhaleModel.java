package com.cgessinger.creaturesandbeasts.client.entity.model;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.EndWhaleEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.util.GeckoLibUtil;

public class EndWhaleModel extends GeoModel<EndWhaleEntity> {
    private static final ResourceLocation END_WHALE_MODEL = new ResourceLocation(CreaturesAndBeasts.MOD_ID, "geo/entity/end_whale/end_whale.geo.json");
    private static final ResourceLocation END_WHALE_TEXTURE = new ResourceLocation(CreaturesAndBeasts.MOD_ID, "textures/entity/end_whale/end_whale.png");
    private static final ResourceLocation END_WHALE_SADDLE_TEXTURE = new ResourceLocation(CreaturesAndBeasts.MOD_ID, "textures/entity/end_whale/end_whale_saddle.png");
    private static final ResourceLocation END_WHALE_ANIMATION = new ResourceLocation(CreaturesAndBeasts.MOD_ID, "animations/end_whale.json");

    @Override
    public ResourceLocation getModelResource(EndWhaleEntity endWhaleEntity) {
        return END_WHALE_MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(EndWhaleEntity entity) {
        return entity.isSaddled() ? END_WHALE_SADDLE_TEXTURE : END_WHALE_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(EndWhaleEntity endWhaleEntity) {
        return END_WHALE_ANIMATION;
    }
}