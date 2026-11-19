package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.cgessinger.creaturesandbeasts.client.entity.model.LittleGrebeModel;
import com.cgessinger.creaturesandbeasts.entities.LittleGrebeEntity;
import com.geckolib.constant.DefaultAnimations;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.RenderPassInfo;

import com.geckolib.renderer.GeoEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class LittleGrebeRenderer extends GeoEntityRenderer<LittleGrebeEntity, LivingEntityRenderState> {
    public LittleGrebeRenderer(EntityRendererProvider.Context context) {
        super(context, new LittleGrebeModel());
        this.shadowRadius = 0.4F;
    }

    @Override
    public RenderType getRenderType(LivingEntityRenderState renderState, Identifier texture) {
        return RenderTypes.entityCutout(texture);
    }

    @Override
    public void adjustModelBonesForRender(RenderPassInfo<LivingEntityRenderState> info, BoneSnapshots snapshots) {
        super.adjustModelBonesForRender(info, snapshots);
        DefaultAnimations.hardcodedHeadRotation(info, snapshots, "head_rotation");
    }}