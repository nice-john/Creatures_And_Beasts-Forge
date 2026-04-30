package com.cgessinger.creaturesandbeasts.client.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.CindershellEntity;
import com.cgessinger.creaturesandbeasts.entities.LittleGrebeEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


import static software.bernie.geckolib.constant.DataTickets.ENTITY_MODEL_DATA;

@Environment(EnvType.CLIENT)
public class LittleGrebeModel extends GeoModel<LittleGrebeEntity> {
    private static final ResourceLocation LITTLE_GREBE_MODEL = ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/little_grebe/little_grebe.geo.json");
    private static final ResourceLocation LITTLE_GREBE_CHICK_MODEL = ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/little_grebe/little_grebe_chick.geo.json");

    private static final ResourceLocation LITTLE_GREBE_TEXTURE = ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/little_grebe/little_grebe.png");
    private static final ResourceLocation LITTLE_GREBE_CHICK_TEXTURE = ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/little_grebe/little_grebe_chick.png");

    private static final ResourceLocation LITTLE_GREBE_ANIMATIONS = ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "animations/little_grebe.json");

    @Override
    public ResourceLocation getModelResource(LittleGrebeEntity entity) {
        return entity.isBaby() ? LITTLE_GREBE_CHICK_MODEL : LITTLE_GREBE_MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(LittleGrebeEntity entity) {
        return entity.isBaby() ? LITTLE_GREBE_CHICK_TEXTURE : LITTLE_GREBE_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(LittleGrebeEntity entity) {
        return LITTLE_GREBE_ANIMATIONS;
    }

    @Override
    public void setCustomAnimations(LittleGrebeEntity animatable, long instanceId, AnimationState<LittleGrebeEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        GeoBone head_rotation = this.getAnimationProcessor().getBone("head_rotation");

        if (head_rotation != null) {
            // Use the correct DataTicket for EntityModelData
            EntityModelData extraData = animationState.getData(ENTITY_MODEL_DATA);

            head_rotation.setRotX(extraData.headPitch() * ((float) Math.PI / 180F));
            head_rotation.setRotY(extraData.netHeadYaw() * ((float) Math.PI / 180F));
        }
    }

}