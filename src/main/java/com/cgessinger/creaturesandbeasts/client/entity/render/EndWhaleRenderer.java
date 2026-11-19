package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.cgessinger.creaturesandbeasts.client.entity.model.EndWhaleModel;
import com.cgessinger.creaturesandbeasts.entities.EndWhaleEntity;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.base.RenderPassInfo;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import org.jspecify.annotations.Nullable;
public class EndWhaleRenderer extends CnbEntityRenderer<EndWhaleEntity> {
    /** Banking (roll) and pitch offsets between the whale and its rider, in degrees. */
    private record RiderTilt(float rollDegrees, float pitchDegrees) {}

    private static final DataTicket<RiderTilt> RIDER_TILT = DataTicket.create("cnb_end_whale_rider_tilt", RiderTilt.class);

    public EndWhaleRenderer(EntityRendererProvider.Context context) {
        super(context, new EndWhaleModel(), 1.5F, 1.0F, 1.0F, false, id -> RenderTypes.entityCutout(id));
    }

    @Override
    public void addRenderData(EndWhaleEntity animatable, @Nullable Void relatedObject, LivingEntityRenderState renderState, float partialTick) {
        super.addRenderData(animatable, relatedObject, renderState, partialTick);
        // Only apply visual roll/tilt when ridden, not rotation changes
        Entity rider = animatable.getFirstPassenger();
        if (rider != null) {
            float whaleRotY = Mth.rotLerp(partialTick, animatable.yRotO, animatable.getYRot());
            float wantedRotY = Mth.rotLerp(partialTick, rider.yRotO, rider.getYRot());
            float whaleRotX = Mth.lerp(partialTick, animatable.xRotO, animatable.getXRot());
            float wantedRotX = Mth.lerp(partialTick, rider.xRotO, rider.getXRot());
            // Apply only the difference for a banking/tilting effect
            float roll = Mth.wrapDegrees(whaleRotY - wantedRotY) * 0.25F;
            float pitch = Mth.wrapDegrees(whaleRotX - wantedRotX) * 0.5F;
            renderState.addGeckolibData(RIDER_TILT, new RiderTilt(roll, pitch));
        }
    }

    @Override
    public void adjustRenderPose(RenderPassInfo<LivingEntityRenderState> info) {
        super.adjustRenderPose(info);
        RiderTilt tilt = info.renderState().getGeckolibData(RIDER_TILT);
        if (tilt != null) {
            info.poseStack().rotateDegrees(Axis.ZP, tilt.rollDegrees());
            info.poseStack().rotateDegrees(Axis.XP, tilt.pitchDegrees());
        }
    }
}