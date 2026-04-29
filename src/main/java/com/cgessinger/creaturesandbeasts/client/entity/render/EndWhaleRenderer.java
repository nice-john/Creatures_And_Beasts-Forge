package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.cgessinger.creaturesandbeasts.client.entity.model.EndWhaleModel;
import com.cgessinger.creaturesandbeasts.client.entity.model.LilytadModel;
import com.cgessinger.creaturesandbeasts.entities.EndWhaleEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


@Environment(EnvType.CLIENT)
public class EndWhaleRenderer extends GeoEntityRenderer<EndWhaleEntity> {

    public EndWhaleRenderer(EntityRendererProvider.Context context) {
        super(context, new EndWhaleModel());
        this.shadowRadius = 1.5F;
    }


    public RenderType getRenderType(EndWhaleEntity animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(textureLocation);
    }

    @Override
    protected void applyRotations(EndWhaleEntity endWhale, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(endWhale, matrixStackIn, ageInTicks, rotationYaw, partialTicks);

        // Only apply visual roll/tilt when ridden, not rotation changes
        Entity rider = endWhale.getFirstPassenger();

        if (rider != null) {
            // Get smooth interpolated rotations
            float whaleRotY = Mth.rotLerp(partialTicks, endWhale.yRotO, endWhale.getYRot());
            float wantedRotY = Mth.rotLerp(partialTicks, rider.yRotO, rider.getYRot());

            float whaleRotX = Mth.lerp(partialTicks, endWhale.xRotO, endWhale.getXRot());
            float wantedRotX = Mth.lerp(partialTicks, rider.xRotO, rider.getXRot());

            // Apply ONLY the difference for banking/tilting effect
            // Reduce these multipliers if it still looks too jerky
            float rollAmount = Mth.wrapDegrees(whaleRotY - wantedRotY) * 0.25F; // Reduced from /2 (0.5)
            float pitchAmount = Mth.wrapDegrees(whaleRotX - wantedRotX) * 0.5F; // Reduced from 1.0

            matrixStackIn.mulPose(Axis.ZP.rotationDegrees(rollAmount));
            matrixStackIn.mulPose(Axis.XP.rotationDegrees(pitchAmount));
        }
        // When not ridden, no additional rotations needed - let vanilla handle it
    }
}