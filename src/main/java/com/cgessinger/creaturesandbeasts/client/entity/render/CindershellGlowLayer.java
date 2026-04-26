package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.CindershellEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

@OnlyIn(Dist.CLIENT)
public class CindershellGlowLayer extends GeoRenderLayer<CindershellEntity> {
    private static final ResourceLocation GLOW_LAYER = ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/cindershell/cindershell_glow.png");

    public CindershellGlowLayer(GeoRenderer<CindershellEntity> renderer) {
        super(renderer);
    }


    public void render(
            PoseStack poseStack,
            CindershellEntity entity,
            BakedGeoModel model,
            RenderType renderType,
            MultiBufferSource bufferSource,
            MultiBufferSource.BufferSource buffer,
            float partialTick,
            int packedLight,
            int packedOverlay
    ) {
        if (!entity.isBaby()) {
            // Create a glow render type for the glow texture
            RenderType glowRenderType = RenderType.eyes(GLOW_LAYER);

            // Push the pose stack for transformations
            poseStack.pushPose();

            // Use the baked model for rendering
            this.getRenderer().reRender(
                    model,
                    poseStack,
                    bufferSource,
                    entity,
                    glowRenderType,
                    bufferSource.getBuffer(glowRenderType),
                    partialTick,
                    packedLight,
                    packedOverlay,
                    0xFFFFFFFF
            );

            // Pop the pose stack after rendering
            poseStack.popPose();
        }
    }
}


