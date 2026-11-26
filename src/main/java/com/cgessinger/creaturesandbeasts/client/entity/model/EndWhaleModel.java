package com.cgessinger.creaturesandbeasts.client.entity.model;

import com.cgessinger.creaturesandbeasts.client.model.CnbGeoModel;
import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.EndWhaleEntity;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;

import com.geckolib.util.GeckoLibUtil;

public class EndWhaleModel extends CnbGeoModel<EndWhaleEntity> {
    private static final Identifier END_WHALE_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/end_whale/end_whale");
    private static final Identifier END_WHALE_TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/end_whale/end_whale.png");
    private static final Identifier END_WHALE_SADDLE_TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/end_whale/end_whale_saddle.png");
    private static final Identifier END_WHALE_ANIMATION = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "end_whale");

    @Override
    protected Identifier resolveModel(EndWhaleEntity endWhaleEntity) {
        return END_WHALE_MODEL;
    }

    @Override
    protected Identifier resolveTexture(EndWhaleEntity entity) {
        return entity.isSaddled() ? END_WHALE_SADDLE_TEXTURE : END_WHALE_TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(EndWhaleEntity endWhaleEntity) {
        return END_WHALE_ANIMATION;
    }
}