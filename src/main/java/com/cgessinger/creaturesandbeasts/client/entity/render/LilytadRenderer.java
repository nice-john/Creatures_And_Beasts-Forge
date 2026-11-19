package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.cgessinger.creaturesandbeasts.client.entity.model.LilytadModel;
import com.cgessinger.creaturesandbeasts.entities.LilytadEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.neoforged.api.distmarker.Dist;
public class LilytadRenderer extends CnbEntityRenderer<LilytadEntity> {
    public LilytadRenderer(EntityRendererProvider.Context context) {
        super(context, new LilytadModel(), 0.7F, 0.5F, 1.0F, false, id -> RenderTypes.entityTranslucent(id));
    }
}