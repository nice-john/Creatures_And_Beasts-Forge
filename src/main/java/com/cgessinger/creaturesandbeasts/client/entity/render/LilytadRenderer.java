package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.cgessinger.creaturesandbeasts.client.entity.model.LilytadModel;
import com.cgessinger.creaturesandbeasts.entities.LilytadEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class LilytadRenderer extends GeoEntityRenderer<LilytadEntity> {
    public LilytadRenderer(EntityRendererProvider.Context context) {
        super(context, new LilytadModel());
        this.shadowRadius = 0.7F; // Set shadow size
    }

    @Override
    public RenderType getRenderType(LilytadEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }

    @Override
    public void preRender(
            PoseStack poseStack,
            LilytadEntity animatable,
            BakedGeoModel model,
            MultiBufferSource bufferSource,
            VertexConsumer buffer,
            boolean isReRender,
            float partialTick,
            int packedLight,
            int packedOverlay,
            float red,
            float green,
            float blue,
            float alpha
    ) {
        // Example: Adjust scale based on entity's state (e.g., baby size)
        if (animatable.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F); // Scale down for baby entities
        }
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}

