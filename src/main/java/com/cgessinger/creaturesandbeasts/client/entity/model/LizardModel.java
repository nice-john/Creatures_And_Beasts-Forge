package com.cgessinger.creaturesandbeasts.client.entity.model;

import com.cgessinger.creaturesandbeasts.client.model.CnbGeoModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.CindershellEntity;
import com.cgessinger.creaturesandbeasts.entities.LilytadEntity;
import com.cgessinger.creaturesandbeasts.entities.LizardEntity;
import com.cgessinger.creaturesandbeasts.init.CNBLizardTypes;
import net.minecraft.resources.Identifier;








@Environment(EnvType.CLIENT)
public class LizardModel extends CnbGeoModel<LizardEntity> {
    private static final Identifier LIZARD_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/lizard/lizard.geo.json");
    private static final Identifier MUSHROOM_LIZARD_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/lizard/mushroom_lizard.geo.json");
    private static final Identifier SAD_LIZARD_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/lizard/sad_lizard.geo.json");
    private static final Identifier SAD_MUSHROOM_LIZARD_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/lizard/sad_mushroom_lizard.geo.json");

    private static final Identifier LIZARD_ANIMATIONS = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "animations/lizard.animation.json");

    @Override
    protected Identifier resolveModel(LizardEntity entity) {
        if (entity.getLizardType().equals(CNBLizardTypes.MUSHROOM)) {
            return entity.getSad() ? SAD_MUSHROOM_LIZARD_MODEL : MUSHROOM_LIZARD_MODEL;
        }
        
        return entity.getSad() ? SAD_LIZARD_MODEL : LIZARD_MODEL;
    }

    @Override
    protected Identifier resolveTexture(LizardEntity entity) {
        return entity.getSad() ? entity.getLizardType().getSadTextureLocation() : entity.getLizardType().getTextureLocation();
    }

    @Override
    public Identifier getAnimationResource(LizardEntity entity) {
        return LIZARD_ANIMATIONS;
    }


}