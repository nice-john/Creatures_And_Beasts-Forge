package com.cgessinger.creaturesandbeasts.client.entity.model;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.CindershellEntity;
import com.cgessinger.creaturesandbeasts.entities.LilytadEntity;
import com.cgessinger.creaturesandbeasts.entities.LizardEntity;
import com.cgessinger.creaturesandbeasts.init.CNBLizardTypes;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


import static software.bernie.geckolib.constant.DataTickets.ENTITY_MODEL_DATA;

@OnlyIn(Dist.CLIENT)
public class LizardModel extends GeoModel<LizardEntity> {
    private static final ResourceLocation LIZARD_MODEL = ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/lizard/lizard.geo.json");
    private static final ResourceLocation MUSHROOM_LIZARD_MODEL = ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/lizard/mushroom_lizard.geo.json");
    private static final ResourceLocation SAD_LIZARD_MODEL = ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/lizard/sad_lizard.geo.json");
    private static final ResourceLocation SAD_MUSHROOM_LIZARD_MODEL = ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/lizard/sad_mushroom_lizard.geo.json");

    private static final ResourceLocation LIZARD_ANIMATIONS = ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "animations/lizard.json");

    @Override
    public ResourceLocation getModelResource(LizardEntity entity) {
        if (entity.getLizardType().equals(CNBLizardTypes.MUSHROOM)) {
            return entity.getSad() ? SAD_MUSHROOM_LIZARD_MODEL : MUSHROOM_LIZARD_MODEL;
        }
        
        return entity.getSad() ? SAD_LIZARD_MODEL : LIZARD_MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(LizardEntity entity) {
        return entity.getSad() ? entity.getLizardType().getSadTextureLocation() : entity.getLizardType().getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationResource(LizardEntity entity) {
        return LIZARD_ANIMATIONS;
    }

    @Override
    public void setCustomAnimations(LizardEntity animatable, long instanceId, AnimationState<LizardEntity> animationState) {
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