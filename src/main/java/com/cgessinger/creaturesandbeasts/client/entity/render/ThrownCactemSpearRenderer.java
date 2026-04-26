package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.client.entity.model.CactemSpearModel;
import com.cgessinger.creaturesandbeasts.entities.ThrownCactemSpearEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ThrownCactemSpearRenderer extends EntityRenderer<ThrownCactemSpearEntity> {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/cactem_spear.png");
    private final CactemSpearModel model;

    public ThrownCactemSpearRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new CactemSpearModel(context.bakeLayer(CactemSpearModel.LAYER_LOCATION));
    }

    public void render(ThrownCactemSpearEntity spearEntity, float p_116112_, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLightIn) {
        poseStack.pushPose();

        // Rotate the spear based on its yaw and pitch
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, spearEntity.yRotO, spearEntity.getYRot()) + 180.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(partialTicks, spearEntity.xRotO, spearEntity.getXRot())));

        // Render the spear
        VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(
                buffer,
                this.model.renderType(this.getTextureLocation(spearEntity)),
                false,
                spearEntity.isFoil()
        );
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLightIn, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);

        poseStack.popPose();

        // Call the super method
        super.render(spearEntity, p_116112_, partialTicks, poseStack, buffer, packedLightIn);
    }


    public ResourceLocation getTextureLocation(ThrownCactemSpearEntity spearEntity) {
        return TEXTURE;
    }
}
