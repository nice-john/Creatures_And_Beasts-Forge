package com.cgessinger.creaturesandbeasts.client.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.SporelingEntity;
import com.cgessinger.creaturesandbeasts.init.CNBSporelingTypes;
import com.cgessinger.creaturesandbeasts.util.SporelingType;
import net.minecraft.resources.Identifier;
import com.geckolib.model.GeoModel;

@Environment(EnvType.CLIENT)
public class SporelingModel extends GeoModel<SporelingEntity> {
    private static final Identifier SPORELING_ANIMATIONS = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "animations/sporeling.animation.json");

    private static final Identifier GOOMY_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/sporeling/sporeling_goomy.geo.json");
    private static final Identifier SHRIMPSNAIL_MODEL = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/sporeling/sporeling_shrimpsnail.geo.json");

    private static final Identifier BIT0_TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/sporeling/sporeling_bit0.png");
    private static final Identifier LISTACALISTA_TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/sporeling/sporeling_listacalista.png");
    private static final Identifier YUNGWILDER_TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/sporeling/sporeling_yungwilder.png");
    private static final Identifier GOOMY_TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/sporeling/sporeling_goomy.png");
    private static final Identifier SHRIMPSNAIL_TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/sporeling/sporeling_shrimpsnail.png");

    @Override
    public Identifier getModelResource(SporelingEntity entity) {
        if (entity.hasCustomName() && entity.getSporelingType().getHostility().equals(SporelingType.SporelingHostility.FRIENDLY)) {
            String customName = entity.getCustomName().getString();
            if (customName.equals("Bit0") || customName.equals("ListaCalista") || customName.equals("yungwilder")) {
                return CNBSporelingTypes.RED_OVERWORLD.getModelLocation();
            } else if (customName.equals("Goomy")) {
                return GOOMY_MODEL;
            } else if (customName.equals("ShrimpSnail")) {
                return SHRIMPSNAIL_MODEL;
            }
        }
        return entity.getSporelingType().getModelLocation();
    }

    @Override
    public Identifier getTextureResource(SporelingEntity entity) {
        if (entity.hasCustomName() && entity.getSporelingType().getHostility().equals(SporelingType.SporelingHostility.FRIENDLY)) {
            String customName = entity.getCustomName().getString();
            switch (customName) {
                case "Bit0":
                    return BIT0_TEXTURE;
                case "ListaCalista":
                    return LISTACALISTA_TEXTURE;
                case "yungwilder" :
                    return YUNGWILDER_TEXTURE;
                case "Goomy":
                    return GOOMY_TEXTURE;
                case "ShrimpSnail":
                    return SHRIMPSNAIL_TEXTURE;
            }
        }

        return entity.getSporelingType().getTextureLocation();
    }

    @Override
    public Identifier getAnimationResource(SporelingEntity entity) {
        return SPORELING_ANIMATIONS;
    }
}
