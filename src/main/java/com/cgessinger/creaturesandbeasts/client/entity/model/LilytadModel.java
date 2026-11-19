package com.cgessinger.creaturesandbeasts.client.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.LilytadEntity;
import net.minecraft.resources.Identifier;
import com.geckolib.model.GeoModel;

@Environment(EnvType.CLIENT)
public class LilytadModel extends GeoModel<LilytadEntity> {
    private static final Identifier LILYTAD_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/lilytad/lilytad.geo.json");
    private static final Identifier LILYTAD_SHEARED_TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/lilytad/lilytad_sheared.png");
    private static final Identifier LILYTAD_ANIMATIONS = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "animations/lilytad.animation.json");

    @Override
    public Identifier getModelResource(LilytadEntity entity) {
        return LILYTAD_MODEL;
    }

    @Override
    public Identifier getTextureResource(LilytadEntity entity) {
        return entity.getSheared() ? LILYTAD_SHEARED_TEXTURE : entity.getLilytadType().getTextureLocation();
    }

    @Override
    public Identifier getAnimationResource(LilytadEntity entity) {
        return LILYTAD_ANIMATIONS;
    }
}
