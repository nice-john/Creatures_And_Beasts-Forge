package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.cgessinger.creaturesandbeasts.client.entity.model.SporelingModel;
import com.cgessinger.creaturesandbeasts.entities.SporelingEntity;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.base.RenderPassInfo;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.api.distmarker.Dist;
import org.jspecify.annotations.Nullable;
public class SporelingRenderer extends CnbEntityRenderer<SporelingEntity> {
    /** x tilt, z tilt (degrees) while riding a crouching player; and y twist (degrees) while the player swings. */
    private record RideTilt(float xDegrees, float zDegrees, float yTwistDegrees) {}

    private static final DataTicket<RideTilt> RIDE_TILT = DataTicket.create("cnb_sporeling_ride_tilt", RideTilt.class);

    public SporelingRenderer(EntityRendererProvider.Context context) {
        super(context, new SporelingModel(), 0.4F, 1.0F, 1.0F, false, id -> RenderTypes.entityCutout(id));
        this.withRenderLayer(new HeldItemBoneLayer<SporelingEntity, LivingEntityRenderState>(context, this, "itemHolder",
                ItemDisplayContext.THIRD_PERSON_LEFT_HAND,
                sporeling -> sporeling.getItemBySlot(EquipmentSlot.MAINHAND),
                sporeling -> HeldItemBoneLayer.Pose.at(0.3F, 0.05F, -0.05F).rotX(-90.0F).scaled(0.5F)));
    }

    @Override
    public void addRenderData(SporelingEntity animatable, @Nullable Void relatedObject, LivingEntityRenderState renderState, float partialTick) {
        super.addRenderData(animatable, relatedObject, renderState, partialTick);
        if (animatable.getVehicle() instanceof Player player) {
            float xTilt = 0.0F;
            float zTilt = 0.0F;
            float twist = 0.0F;
            if (player.isCrouching()) {
                float yRot = Mth.rotLerp(partialTick, animatable.yRotO, animatable.getYRot());
                xTilt = -25.0F * Mth.cos(yRot * Mth.DEG_TO_RAD);
                zTilt = -25.0F * Mth.sin(yRot * Mth.DEG_TO_RAD);
            }
            float attack = player.getSwingAnimation(partialTick);
            if (attack > 0) {
                twist = -Mth.sin(Mth.sqrt(attack) * ((float) Math.PI * 2F)) * 0.2F * Mth.RAD_TO_DEG;
            }
            renderState.addGeckolibData(RIDE_TILT, new RideTilt(xTilt, zTilt, twist));
        }
    }

    @Override
    public void adjustRenderPose(RenderPassInfo<LivingEntityRenderState> info) {
        super.adjustRenderPose(info);
        RideTilt tilt = info.renderState().getGeckolibData(RIDE_TILT);
        if (tilt != null) {
            info.poseStack().rotateDegrees(Axis.XP, tilt.xDegrees());
            info.poseStack().rotateDegrees(Axis.ZP, tilt.zDegrees());
            info.poseStack().rotateDegrees(Axis.YP, tilt.yTwistDegrees());
        }
    }
}