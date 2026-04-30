package com.cgessinger.creaturesandbeasts.client.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.LilytadEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@Environment(EnvType.CLIENT)
public class LilytadModel extends GeoModel<LilytadEntity> {
    private static final ResourceLocation LILYTAD_MODEL = ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/lilytad/lilytad.geo.json");
    private static final ResourceLocation LILYTAD_SHEARED_TEXTURE = ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/lilytad/lilytad_sheared.png");
    private static final ResourceLocation LILYTAD_ANIMATIONS = ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "animations/lilytad.json");

    @Override
    public ResourceLocation getModelResource(LilytadEntity entity) {
        return LILYTAD_MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(LilytadEntity entity) {
        return entity.getSheared() ? LILYTAD_SHEARED_TEXTURE : entity.getLilytadType().getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationResource(LilytadEntity entity) {
        return LILYTAD_ANIMATIONS;
    }
}
