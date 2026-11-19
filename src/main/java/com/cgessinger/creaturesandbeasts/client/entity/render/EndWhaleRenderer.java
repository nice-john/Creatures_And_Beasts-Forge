package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.cgessinger.creaturesandbeasts.client.entity.model.EndWhaleModel;
import com.cgessinger.creaturesandbeasts.entities.EndWhaleEntity;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.RenderPassInfo;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

@Environment(EnvType.CLIENT)
public class EndWhaleRenderer extends GeoEntityRenderer<EndWhaleEntity, LivingEntityRenderState> {
    private static final DataTicket<Float> RIDER_ROLL = DataTicket.create("cnb_whale_roll", Float.class);
    private static final DataTicket<Float> RIDER_PITCH = DataTicket.create("cnb_whale_pitch", Float.class);

    public EndWhaleRenderer(EntityRendererProvider.Context context) {
        super(context, new EndWhaleModel());
        this.shadowRadius = 1.5F;
    }

    @Override
    public RenderType getRenderType(LivingEntityRenderState renderState, Identifier texture) {
        return RenderTypes.entityCutout(texture);
    }

    @Override
    public void addRenderData(EndWhaleEntity endWhale, Void relatedObject, LivingEntityRenderState renderState, float partialTick) {
        super.addRenderData(endWhale, relatedObject, renderState, partialTick);
        float roll = 0.0F;
        float pitch = 0.0F;
        // Only apply visual roll/tilt when ridden: the difference between where the rider looks and
        // where the whale points gives a banking effect.
        Entity rider = endWhale.getFirstPassenger();
        if (rider != null) {
            float whaleRotY = Mth.rotLerp(partialTick, endWhale.yRotO, endWhale.getYRot());
            float wantedRotY = Mth.rotLerp(partialTick, rider.yRotO, rider.getYRot());
            float whaleRotX = Mth.lerp(partialTick, endWhale.xRotO, endWhale.getXRot());
            float wantedRotX = Mth.lerp(partialTick, rider.xRotO, rider.getXRot());
            roll = Mth.wrapDegrees(whaleRotY - wantedRotY) * 0.25F;
            pitch = Mth.wrapDegrees(whaleRotX - wantedRotX) * 0.5F;
        }
        renderState.addGeckolibData(RIDER_ROLL, roll);
        renderState.addGeckolibData(RIDER_PITCH, pitch);
    }

    @Override
    public void adjustRenderPose(RenderPassInfo<LivingEntityRenderState> info) {
        super.adjustRenderPose(info);
        float roll = info.getOrDefaultGeckolibData(RIDER_ROLL, 0.0F);
        float pitch = info.getOrDefaultGeckolibData(RIDER_PITCH, 0.0F);
        if (roll != 0.0F || pitch != 0.0F) {
            info.poseStack().rotateDegrees(Axis.ZP, roll);
            info.poseStack().rotateDegrees(Axis.XP, pitch);
        }
    }
}