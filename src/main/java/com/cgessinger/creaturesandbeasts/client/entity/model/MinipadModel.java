package com.cgessinger.creaturesandbeasts.client.entity.model;

import com.cgessinger.creaturesandbeasts.client.model.CnbGeoModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.MinipadEntity;
import net.minecraft.resources.Identifier;



@Environment(EnvType.CLIENT)
public class MinipadModel extends CnbGeoModel<MinipadEntity> {
    private static final Identifier MINIPAD_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/minipad/minipad");
    private static final Identifier MINIPAD_SHEARED_TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/minipad/minipad_sheared.png");
    private static final Identifier MINIPAD_ANIMATIONS = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "minipad");

    @Override
    protected Identifier resolveModel(MinipadEntity entity) {
        return MINIPAD_MODEL;
    }

    @Override
    protected Identifier resolveTexture(MinipadEntity entity) {
        return entity.getSheared() ? MINIPAD_SHEARED_TEXTURE : entity.getMinipadType().getTextureLocation();
    }

    @Override
    public Identifier getAnimationResource(MinipadEntity entity) {
        return MINIPAD_ANIMATIONS;
    }
}
