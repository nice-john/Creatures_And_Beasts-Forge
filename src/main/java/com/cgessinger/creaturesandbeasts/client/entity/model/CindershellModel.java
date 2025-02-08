package com.cgessinger.creaturesandbeasts.client.entity.model;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.CindershellEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import static software.bernie.geckolib.constant.DataTickets.ENTITY_MODEL_DATA;

@OnlyIn(Dist.CLIENT)
public class CindershellModel extends GeoModel<CindershellEntity> {
    private static final ResourceLocation CINDERSHELL_MODEL = new ResourceLocation(CreaturesAndBeasts.MOD_ID, "geo/entity/cindershell/cindershell.geo.json");
    private static final ResourceLocation BABY_CINDERSHELL_MODEL = new ResourceLocation(CreaturesAndBeasts.MOD_ID, "geo/entity/cindershell/baby_cindershell.geo.json");
    private static final ResourceLocation CINDERSHELL_FURNACE_MODEL = new ResourceLocation(CreaturesAndBeasts.MOD_ID, "geo/entity/cindershell/cindershell_furnace.geo.json");

    private static final ResourceLocation CINDERSHELL_TEXTURE = new ResourceLocation(CreaturesAndBeasts.MOD_ID, "textures/entity/cindershell/cindershell.png");
    private static final ResourceLocation BABY_CINDERSHELL_TEXTURE = new ResourceLocation(CreaturesAndBeasts.MOD_ID, "textures/entity/cindershell/baby_cindershell.png");

    private static final ResourceLocation CINDERSHELL_ANIMATIONS = new ResourceLocation(CreaturesAndBeasts.MOD_ID, "animations/cindershell.json");

    @Override
    public ResourceLocation getModelResource(CindershellEntity entity) {
        if (entity.isBaby()) {
            return BABY_CINDERSHELL_MODEL;
        } else if (entity.hasFurnace()) {
            return CINDERSHELL_FURNACE_MODEL;
        } else {
            return CINDERSHELL_MODEL;
        }
    }

    @Override
    public ResourceLocation getTextureResource(CindershellEntity entity) {
        return entity.isBaby() ? BABY_CINDERSHELL_TEXTURE : CINDERSHELL_TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(CindershellEntity entity) {
        return CINDERSHELL_ANIMATIONS;
    }

    @Override
    public void setCustomAnimations(CindershellEntity animatable, long instanceId, AnimationState<CindershellEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        CoreGeoBone head_rotation = this.getAnimationProcessor().getBone("head_rotation");

        if (head_rotation != null) {
            // Use the correct DataTicket for EntityModelData
            EntityModelData extraData = animationState.getData(ENTITY_MODEL_DATA);

            head_rotation.setRotX(extraData.headPitch() * ((float) Math.PI / 180F));
            head_rotation.setRotY(extraData.netHeadYaw() * ((float) Math.PI / 180F));
        }
    }

}