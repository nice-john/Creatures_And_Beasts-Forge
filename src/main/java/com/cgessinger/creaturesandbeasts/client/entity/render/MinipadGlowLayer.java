package com.cgessinger.creaturesandbeasts.client.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.MinipadEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

@Environment(EnvType.CLIENT)
public class MinipadGlowLayer extends GeoRenderLayer<MinipadEntity> {
    private static final ResourceLocation MINIPAD_MODEL = ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "geo/entity/minipad/minipad.geo.json");

    public MinipadGlowLayer(GeoRenderer<MinipadEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(
            PoseStack poseStack,
            MinipadEntity entity,
            BakedGeoModel model,
            RenderType renderType,
            MultiBufferSource bufferSource,
            VertexConsumer buffer,
            float partialTick,
            int packedLight,
            int packedOverlay
    ) {
        long time = entity.level().getDayTime();

        if (entity.isGlowing()) {
            RenderType eyesTexture = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/minipad/minipad_eyes_glow.png"));
            RenderType flowerGlow = RenderType.eyes(entity.getMinipadType().getGlowTextureLocation());
            RenderType flowerTranslucent = RenderType.entityTranslucent(entity.getMinipadType().getGlowTextureLocation());

            poseStack.pushPose();

            if (!entity.getSheared()) {
                this.getRenderer().reRender(
                        model,
                        poseStack,
                        bufferSource,
                        entity,
                        flowerGlow,
                        bufferSource.getBuffer(flowerGlow),
                        partialTick,
                        packedLight,
                        packedOverlay,
                        0xFFFFFFFF
                );

                int translucentAlpha = (int) (Math.pow((time - 18000) / 5000f, 2) * 0xFF);
                int translucentColor = (Math.max(0, Math.min(0xFF, translucentAlpha)) << 24) | 0x00FFFFFF;
                this.getRenderer().reRender(
                        model,
                        poseStack,
                        bufferSource,
                        entity,
                        flowerTranslucent,
                        bufferSource.getBuffer(flowerTranslucent),
                        partialTick,
                        packedLight,
                        packedOverlay,
                        translucentColor
                );
            }

            int eyesAlpha = (int) (((float) -Math.pow((time - 18000) / 5000f, 2) + 1) * 0xFF);
            int eyesColor = (Math.max(0, Math.min(0xFF, eyesAlpha)) << 24) | 0x00FFFFFF;
            this.getRenderer().reRender(
                    model,
                    poseStack,
                    bufferSource,
                    entity,
                    eyesTexture,
                    bufferSource.getBuffer(eyesTexture),
                    partialTick,
                    packedLight,
                    packedOverlay,
                    eyesColor
            );

            poseStack.popPose();
        }
    }
}
