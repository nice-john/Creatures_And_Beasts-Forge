package com.cgessinger.creaturesandbeasts.client.entity.model;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.LizardEntity;
import com.cgessinger.creaturesandbeasts.entities.YetiEntity;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
public class YetiModel extends CnbGeoModel<YetiEntity> {
    private static final Identifier YETI_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/yeti/yeti");
    private static final Identifier BABY_YETI_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/yeti/baby_yeti");

    private static final Identifier YETI_TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/yeti/yeti.png");
    private static final Identifier BABY_YETI_TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/yeti/baby_yeti.png");

    private static final Identifier YETI_ANIMATIONS = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "yeti");

    @Override
    protected Identifier selectModel(YetiEntity entity) {
        return entity.isBaby() ? BABY_YETI_MODEL : YETI_MODEL;
    }

    @Override
    protected Identifier selectTexture(YetiEntity entity) {
        return entity.isBaby() ? BABY_YETI_TEXTURE : YETI_TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(YetiEntity entity) {
        return YETI_ANIMATIONS;
    }

}
