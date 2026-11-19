package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.cgessinger.creaturesandbeasts.client.entity.model.LittleGrebeModel;
import com.cgessinger.creaturesandbeasts.entities.LittleGrebeEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.neoforged.api.distmarker.Dist;
public class LittleGrebeRenderer extends CnbEntityRenderer<LittleGrebeEntity> {
    public LittleGrebeRenderer(EntityRendererProvider.Context context) {
        super(context, new LittleGrebeModel(), 0.4F, 0.5F, 1.0F, true, id -> RenderTypes.entityCutout(id));
    }
}