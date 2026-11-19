package com.cgessinger.creaturesandbeasts.client.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.MinipadEntity;
import net.minecraft.resources.Identifier;
import com.geckolib.model.GeoModel;


@Environment(EnvType.CLIENT)
public class MinipadModel extends GeoModel<MinipadEntity> {
    private static final Identifier MINIPAD_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/minipad/minipad.geo.json");
    private static final Identifier MINIPAD_SHEARED_TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/minipad/minipad_sheared.png");
    private static final Identifier MINIPAD_ANIMATIONS = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "animations/minipad.animation.json");

    @Override
    public Identifier getModelResource(MinipadEntity entity) {
        return MINIPAD_MODEL;
    }

    @Override
    public Identifier getTextureResource(MinipadEntity entity) {
        return entity.getSheared() ? MINIPAD_SHEARED_TEXTURE : entity.getMinipadType().getTextureLocation();
    }

    @Override
    public Identifier getAnimationResource(MinipadEntity entity) {
        return MINIPAD_ANIMATIONS;
    }
}
