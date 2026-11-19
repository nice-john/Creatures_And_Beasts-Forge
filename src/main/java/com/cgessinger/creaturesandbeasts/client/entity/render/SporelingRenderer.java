package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.cgessinger.creaturesandbeasts.client.entity.model.SporelingModel;
import com.cgessinger.creaturesandbeasts.entities.SporelingEntity;
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
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;

@Environment(EnvType.CLIENT)
public class SporelingRenderer extends GeoEntityRenderer<SporelingEntity, LivingEntityRenderState> {
    private static final DataTicket<Float> TILT_X = DataTicket.create("cnb_sporeling_tilt_x", Float.class);
    private static final DataTicket<Float> TILT_Z = DataTicket.create("cnb_sporeling_tilt_z", Float.class);
    private static final DataTicket<Float> SWAY_Y = DataTicket.create("cnb_sporeling_sway_y", Float.class);

    public SporelingRenderer(EntityRendererProvider.Context context) {
        super(context, new SporelingModel());
        this.shadowRadius = 0.4F;
        this.withRenderLayer(new ItemHolderLayer<>(context, this, "itemHolder", e -> e.getItemBySlot(EquipmentSlot.MAINHAND)));
    }

    @Override
    public RenderType getRenderType(LivingEntityRenderState renderState, Identifier texture) {
        return RenderTypes.entityCutout(texture);
    }

    @Override
    public void addRenderData(SporelingEntity entity, Void relatedObject, LivingEntityRenderState renderState, float partialTick) {
        super.addRenderData(entity, relatedObject, renderState, partialTick);
        float tiltX = 0.0F;
        float tiltZ = 0.0F;
        float swayY = 0.0F;
        // Lean with a crouching rider and sway with the rider's attack swing.
        if (entity.getVehicle() instanceof Player player) {
            if (player.isCrouching()) {
                float yRotLerped = Mth.rotLerp(partialTick, entity.yRotO, entity.getYRot());
                tiltX = -25.0F * Mth.cos(yRotLerped * Mth.DEG_TO_RAD);
                tiltZ = -25.0F * Mth.sin(yRotLerped * Mth.DEG_TO_RAD);
            }
            float rotation = player.getSwingAnimation(partialTick);
            if (rotation > 0) {
                swayY = -Mth.sin(Mth.sqrt(rotation) * ((float) Math.PI * 2F)) * 0.2F * Mth.RAD_TO_DEG;
            }
        }
        renderState.addGeckolibData(TILT_X, tiltX);
        renderState.addGeckolibData(TILT_Z, tiltZ);
        renderState.addGeckolibData(SWAY_Y, swayY);
    }

    @Override
    public void adjustRenderPose(RenderPassInfo<LivingEntityRenderState> info) {
        super.adjustRenderPose(info);
        float tiltX = info.getOrDefaultGeckolibData(TILT_X, 0.0F);
        float tiltZ = info.getOrDefaultGeckolibData(TILT_Z, 0.0F);
        float swayY = info.getOrDefaultGeckolibData(SWAY_Y, 0.0F);
        if (tiltX != 0.0F) info.poseStack().rotateDegrees(Axis.XP, tiltX);
        if (tiltZ != 0.0F) info.poseStack().rotateDegrees(Axis.ZP, tiltZ);
        if (swayY != 0.0F) info.poseStack().rotateDegrees(Axis.YP, swayY);
    }
}