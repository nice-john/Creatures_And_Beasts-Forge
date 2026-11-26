package com.cgessinger.creaturesandbeasts.client.entity.model;

import com.cgessinger.creaturesandbeasts.client.model.CnbGeoModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.CindershellEntity;
import net.minecraft.resources.Identifier;







@Environment(EnvType.CLIENT)
public class CindershellModel extends CnbGeoModel<CindershellEntity> {
    private static final Identifier CINDERSHELL_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/cindershell/cindershell");
    private static final Identifier BABY_CINDERSHELL_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/cindershell/baby_cindershell");
    private static final Identifier CINDERSHELL_FURNACE_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/cindershell/cindershell_furnace");

    private static final Identifier CINDERSHELL_TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/cindershell/cindershell.png");
    private static final Identifier BABY_CINDERSHELL_TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/cindershell/baby_cindershell.png");

    private static final Identifier CINDERSHELL_ANIMATIONS = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "cindershell");

    @Override
    protected Identifier resolveModel(CindershellEntity entity) {
        if (entity.isBaby()) {
            return BABY_CINDERSHELL_MODEL;
        } else if (entity.hasFurnace()) {
            return CINDERSHELL_FURNACE_MODEL;
        } else {
            return CINDERSHELL_MODEL;
        }
    }

    @Override
    protected Identifier resolveTexture(CindershellEntity entity) {
        return entity.isBaby() ? BABY_CINDERSHELL_TEXTURE : CINDERSHELL_TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(CindershellEntity entity) {
        return CINDERSHELL_ANIMATIONS;
    }



}