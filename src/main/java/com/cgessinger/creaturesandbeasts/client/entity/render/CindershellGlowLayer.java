package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.CindershellEntity;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.layer.builtin.TextureLayerGeoLayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class CindershellGlowLayer extends TextureLayerGeoLayer<CindershellEntity, Void, LivingEntityRenderState> {
    private static final Identifier GLOW_LAYER = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/cindershell/cindershell_glow.png");

    public CindershellGlowLayer(GeoRenderer<CindershellEntity, Void, LivingEntityRenderState> renderer) {
        super(renderer, GLOW_LAYER, RenderTypes::eyes);
    }

    @Override
    public void submitRenderTask(RenderPassInfo<LivingEntityRenderState> info, SubmitNodeCollector collector) {
        // Babies have no glow layer.
        if (!info.renderState().isBaby) {
            super.submitRenderTask(info, collector);
        }
    }
}