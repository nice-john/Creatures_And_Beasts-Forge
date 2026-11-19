package com.cgessinger.creaturesandbeasts.client.entity.model;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.CactemEntity;
import net.minecraft.resources.Identifier;

public class CactemModel extends CnbGeoModel<CactemEntity> {
    private static final Identifier ELDER_CACTEM_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/cactem/elder_cactem.geo.json");
    private static final Identifier WARRIOR_CACTEM_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/cactem/warrior_cactem.geo.json");
    private static final Identifier BABY_CACTEM_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/cactem/baby_cactem.geo.json");

    private static final Identifier ELDER_CACTEM_TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/cactem/elder_cactem.png");
    private static final Identifier WARRIOR_CACTEM_TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/cactem/warrior_cactem.png");
    private static final Identifier BABY_CACTEM_TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/cactem/baby_cactem.png");

    private static final Identifier CACTEM_ANIMATIONS = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "animations/cactem.animation.json");

    @Override
    protected Identifier selectModel(CactemEntity entity) {
        if (entity.isBaby()) {
            return BABY_CACTEM_MODEL;
        } else if (entity.isElder()) {
            return ELDER_CACTEM_MODEL;
        } else {
            return WARRIOR_CACTEM_MODEL;
        }
    }

    @Override
    protected Identifier selectTexture(CactemEntity entity) {
        if (entity.isBaby()) {
            return BABY_CACTEM_TEXTURE;
        } else if (entity.isElder()) {
            return ELDER_CACTEM_TEXTURE;
        } else {
            return WARRIOR_CACTEM_TEXTURE;
        }
    }

    @Override
    public Identifier getAnimationResource(CactemEntity entity) {
        return CACTEM_ANIMATIONS;
    }
}
