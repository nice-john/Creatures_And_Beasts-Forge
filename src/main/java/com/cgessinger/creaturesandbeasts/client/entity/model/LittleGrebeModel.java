package com.cgessinger.creaturesandbeasts.client.entity.model;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.CindershellEntity;
import com.cgessinger.creaturesandbeasts.entities.LittleGrebeEntity;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
public class LittleGrebeModel extends CnbGeoModel<LittleGrebeEntity> {
    private static final Identifier LITTLE_GREBE_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/little_grebe/little_grebe.geo.json");
    private static final Identifier LITTLE_GREBE_CHICK_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/little_grebe/little_grebe_chick.geo.json");

    private static final Identifier LITTLE_GREBE_TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/little_grebe/little_grebe.png");
    private static final Identifier LITTLE_GREBE_CHICK_TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/little_grebe/little_grebe_chick.png");

    private static final Identifier LITTLE_GREBE_ANIMATIONS = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "animations/little_grebe.animation.json");

    @Override
    protected Identifier selectModel(LittleGrebeEntity entity) {
        return entity.isBaby() ? LITTLE_GREBE_CHICK_MODEL : LITTLE_GREBE_MODEL;
    }

    @Override
    protected Identifier selectTexture(LittleGrebeEntity entity) {
        return entity.isBaby() ? LITTLE_GREBE_CHICK_TEXTURE : LITTLE_GREBE_TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(LittleGrebeEntity entity) {
        return LITTLE_GREBE_ANIMATIONS;
    }


}