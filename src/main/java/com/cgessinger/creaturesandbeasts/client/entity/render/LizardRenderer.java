package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.cgessinger.creaturesandbeasts.client.entity.model.LizardModel;
import com.cgessinger.creaturesandbeasts.entities.LizardEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.neoforged.api.distmarker.Dist;
public class LizardRenderer extends CnbEntityRenderer<LizardEntity> {
    public LizardRenderer(EntityRendererProvider.Context context) {
        super(context, new LizardModel(), 0.3F, 0.5F, 1.0F, true, id -> RenderTypes.entityCutout(id));
    }
}