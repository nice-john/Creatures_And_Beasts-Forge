package com.cgessinger.creaturesandbeasts.client.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.LizardEntity;
import com.cgessinger.creaturesandbeasts.entities.YetiEntity;
import net.minecraft.resources.Identifier;
import com.geckolib.cache.model.GeoBone;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

// ENTITY_MODEL_DATA removed in GeckoLib 5

@Environment(EnvType.CLIENT)
public class YetiModel extends GeoModel<YetiEntity> {
    private static final Identifier YETI_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/yeti/yeti.geo.json");
    private static final Identifier BABY_YETI_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/yeti/baby_yeti.geo.json");

    private static final Identifier YETI_TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/yeti/yeti.png");
    private static final Identifier BABY_YETI_TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/yeti/baby_yeti.png");

    private static final Identifier YETI_ANIMATIONS = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "animations/yeti.animation.json");

    @Override
    public Identifier getModelResource(YetiEntity entity) {
        return entity.isBaby() ? BABY_YETI_MODEL : YETI_MODEL;
    }

    @Override
    public Identifier getTextureResource(YetiEntity entity) {
        return entity.isBaby() ? BABY_YETI_TEXTURE : YETI_TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(YetiEntity entity) {
        return YETI_ANIMATIONS;
    }

    @Override
    public void setCustomAnimations(YetiEntity animatable, long instanceId, AnimationTest<YetiEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        GeoBone head_rotation = this.getAnimationProcessor().getBone("head_rotation");

        EntityModelData extraData = animationState.getData(ENTITY_MODEL_DATA);

        head_rotation.setRotX(extraData.headPitch() * ((float) Math.PI / 180F));
        if (animatable.isBaby()) {
            head_rotation.setRotZ(extraData.netHeadYaw() * ((float) Math.PI / 180F));
        } else {
            head_rotation.setRotY(extraData.netHeadYaw() * ((float) Math.PI / 180F));
        }
    }
}
