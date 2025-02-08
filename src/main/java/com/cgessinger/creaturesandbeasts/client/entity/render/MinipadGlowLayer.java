package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.entities.MinipadEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

@OnlyIn(Dist.CLIENT)
public class MinipadGlowLayer extends GeoRenderLayer<MinipadEntity> {
    private static final ResourceLocation MINIPAD_MODEL = new ResourceLocation(CreaturesAndBeasts.MOD_ID, "geo/entity/minipad/minipad.geo.json");

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
            RenderType eyesTexture = RenderType.eyes(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "textures/entity/minipad/minipad_eyes_glow.png"));
            RenderType flowerGlow = RenderType.eyes(entity.getMinipadType().getGlowTextureLocation());
            RenderType flowerTranslucent = RenderType.entityTranslucent(entity.getMinipadType().getGlowTextureLocation());

            poseStack.pushPose();

            if (!entity.getSheared()) {
                // Render the glowing flower
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
                        1f, 1f, 1f, 1.0f
                );

                // Render the translucent flower with fading glow
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
                        1f, 1f, 1f, (float) Math.pow((time - 18000) / 5000f, 2)
                );
            }

            // Render the glowing eyes with fading
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
                    1f, 1f, 1f, (float) -Math.pow((time - 18000) / 5000f, 2) + 1
            );

            poseStack.popPose();
        }
    }
}
