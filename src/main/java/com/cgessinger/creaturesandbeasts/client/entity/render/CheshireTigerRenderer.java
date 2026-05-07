package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.cgessinger.creaturesandbeasts.client.entity.model.CheshireTigerModel;
import com.cgessinger.creaturesandbeasts.entities.CheshireTigerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class CheshireTigerRenderer extends GeoEntityRenderer<CheshireTigerEntity> {
    public CheshireTigerRenderer(EntityRendererProvider.Context context) {
        super(context, new CheshireTigerModel());
        this.shadowRadius = 0.5F;
    }
}
