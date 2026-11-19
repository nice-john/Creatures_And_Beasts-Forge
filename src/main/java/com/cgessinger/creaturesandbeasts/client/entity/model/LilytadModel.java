package com.cgessinger.creaturesandbeasts.client.entity.model;

import com.cgessinger.creaturesandbeasts.client.model.CnbGeoModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.LilytadEntity;
import net.minecraft.resources.Identifier;


@Environment(EnvType.CLIENT)
public class LilytadModel extends CnbGeoModel<LilytadEntity> {
    private static final Identifier LILYTAD_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/lilytad/lilytad.geo.json");
    private static final Identifier LILYTAD_SHEARED_TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/lilytad/lilytad_sheared.png");
    private static final Identifier LILYTAD_ANIMATIONS = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "animations/lilytad.animation.json");

    @Override
    protected Identifier resolveModel(LilytadEntity entity) {
        return LILYTAD_MODEL;
    }

    @Override
    protected Identifier resolveTexture(LilytadEntity entity) {
        return entity.getSheared() ? LILYTAD_SHEARED_TEXTURE : entity.getLilytadType().getTextureLocation();
    }

    @Override
    public Identifier getAnimationResource(LilytadEntity entity) {
        return LILYTAD_ANIMATIONS;
    }
}
