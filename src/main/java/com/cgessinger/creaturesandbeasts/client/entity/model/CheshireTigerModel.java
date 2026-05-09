package com.cgessinger.creaturesandbeasts.client.entity.model;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.CheshireTigerEntity;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.model.GeoModel;

/**
 * Resource paths the Cheshire Tiger model expects. The user is dropping in
 * the model/texture/animation files at the locations below — until they
 * exist, the entity is spawnable but rendering will throw (spawn the mob in
 * a chunk that's currently unloaded, then unload before approaching, or
 * just don't render until the assets land).
 */
@OnlyIn(Dist.CLIENT)
public class CheshireTigerModel extends GeoModel<CheshireTigerEntity> {
    private static final ResourceLocation MODEL =
            ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/cheshire_tiger/cheshire_tiger.geo.json");
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/cheshire_tiger/cheshire_tiger.png");
    // Animation file the user dropped in is `cheshire_tiger.animation.json`
    // (Blockbench's default export). Our other entities use `<name>.json`,
    // but matching the user's drop avoids a rename and the path is just a
    // string GeckoLib opens directly.
    private static final ResourceLocation ANIMATIONS =
            ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "animations/cheshire_tiger.animation.json");

    @Override
    public ResourceLocation getModelResource(CheshireTigerEntity entity) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(CheshireTigerEntity entity) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(CheshireTigerEntity entity) {
        return ANIMATIONS;
    }
}
