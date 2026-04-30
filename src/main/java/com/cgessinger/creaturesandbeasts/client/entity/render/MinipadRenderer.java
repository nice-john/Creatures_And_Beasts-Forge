package com.cgessinger.creaturesandbeasts.client.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import com.cgessinger.creaturesandbeasts.client.entity.model.MinipadModel;
import com.cgessinger.creaturesandbeasts.entities.CactemEntity;
import com.cgessinger.creaturesandbeasts.entities.MinipadEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class MinipadRenderer extends GeoEntityRenderer<MinipadEntity> {
    public MinipadRenderer(EntityRendererProvider.Context context) {
        super(context, new MinipadModel());
        this.addRenderLayer(new MinipadGlowLayer(this)); // Updated to `addRenderLayer`
        this.shadowRadius = 0.4F;
    }

    @Override
    public RenderType getRenderType(MinipadEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        // Updated signature and implementation
        return RenderType.entityTranslucent(texture);
    }

    @Override
    public void preRender(
            PoseStack poseStack,
            MinipadEntity animatable,
            BakedGeoModel model,
            MultiBufferSource bufferSource,
            VertexConsumer buffer,
            boolean isReRender,
            float partialTick,
            int packedLight,
            int packedOverlay,
            int colour) {
        // Scale the model based on the entity's size
        if (animatable.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        } else {
            poseStack.scale(0.8F, 0.8F, 0.8F);
        }

        // Call the superclass implementation
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
    }


}

