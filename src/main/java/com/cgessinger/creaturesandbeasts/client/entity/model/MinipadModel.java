package com.cgessinger.creaturesandbeasts.client.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.MinipadEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;


@Environment(EnvType.CLIENT)
public class MinipadModel extends GeoModel<MinipadEntity> {
    private static final ResourceLocation MINIPAD_MODEL = ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/minipad/minipad.geo.json");
    private static final ResourceLocation MINIPAD_SHEARED_TEXTURE = ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/minipad/minipad_sheared.png");
    private static final ResourceLocation MINIPAD_ANIMATIONS = ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "animations/minipad.json");

    @Override
    public ResourceLocation getModelResource(MinipadEntity entity) {
        return MINIPAD_MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(MinipadEntity entity) {
        return entity.getSheared() ? MINIPAD_SHEARED_TEXTURE : entity.getMinipadType().getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationResource(MinipadEntity entity) {
        return MINIPAD_ANIMATIONS;
    }
}
