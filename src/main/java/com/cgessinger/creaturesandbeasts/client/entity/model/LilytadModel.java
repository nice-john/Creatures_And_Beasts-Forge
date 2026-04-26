package com.cgessinger.creaturesandbeasts.client.entity.model;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.LilytadEntity;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.model.GeoModel;

@OnlyIn(Dist.CLIENT)
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
