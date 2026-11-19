package com.cgessinger.creaturesandbeasts.client.entity.model;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.CindershellEntity;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
public class CindershellModel extends CnbGeoModel<CindershellEntity> {
    private static final Identifier CINDERSHELL_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/cindershell/cindershell.geo.json");
    private static final Identifier BABY_CINDERSHELL_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/cindershell/baby_cindershell.geo.json");
    private static final Identifier CINDERSHELL_FURNACE_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/cindershell/cindershell_furnace.geo.json");

    private static final Identifier CINDERSHELL_TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/cindershell/cindershell.png");
    private static final Identifier BABY_CINDERSHELL_TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/cindershell/baby_cindershell.png");

    private static final Identifier CINDERSHELL_ANIMATIONS = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "animations/cindershell.animation.json");

    @Override
    protected Identifier selectModel(CindershellEntity entity) {
        if (entity.isBaby()) {
            return BABY_CINDERSHELL_MODEL;
        } else if (entity.hasFurnace()) {
            return CINDERSHELL_FURNACE_MODEL;
        } else {
            return CINDERSHELL_MODEL;
        }
    }

    @Override
    protected Identifier selectTexture(CindershellEntity entity) {
        return entity.isBaby() ? BABY_CINDERSHELL_TEXTURE : CINDERSHELL_TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(CindershellEntity entity) {
        return CINDERSHELL_ANIMATIONS;
    }


}