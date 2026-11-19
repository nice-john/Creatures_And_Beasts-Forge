package com.cgessinger.creaturesandbeasts.client.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.CindershellEntity;
import net.minecraft.resources.Identifier;
import com.geckolib.cache.model.GeoBone;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

// ENTITY_MODEL_DATA removed in GeckoLib 5

@Environment(EnvType.CLIENT)
public class CindershellModel extends GeoModel<CindershellEntity> {
    private static final Identifier CINDERSHELL_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/cindershell/cindershell.geo.json");
    private static final Identifier BABY_CINDERSHELL_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/cindershell/baby_cindershell.geo.json");
    private static final Identifier CINDERSHELL_FURNACE_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/cindershell/cindershell_furnace.geo.json");

    private static final Identifier CINDERSHELL_TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/cindershell/cindershell.png");
    private static final Identifier BABY_CINDERSHELL_TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/cindershell/baby_cindershell.png");

    private static final Identifier CINDERSHELL_ANIMATIONS = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "animations/cindershell.animation.json");

    @Override
    public Identifier getModelResource(CindershellEntity entity) {
        if (entity.isBaby()) {
            return BABY_CINDERSHELL_MODEL;
        } else if (entity.hasFurnace()) {
            return CINDERSHELL_FURNACE_MODEL;
        } else {
            return CINDERSHELL_MODEL;
        }
    }

    @Override
    public Identifier getTextureResource(CindershellEntity entity) {
        return entity.isBaby() ? BABY_CINDERSHELL_TEXTURE : CINDERSHELL_TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(CindershellEntity entity) {
        return CINDERSHELL_ANIMATIONS;
    }

    @Override
    public void setCustomAnimations(CindershellEntity animatable, long instanceId, AnimationTest<CindershellEntity> animationState) {
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