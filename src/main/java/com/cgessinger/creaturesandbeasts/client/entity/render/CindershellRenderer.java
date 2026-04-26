package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.cgessinger.creaturesandbeasts.client.entity.model.CindershellModel;
import com.cgessinger.creaturesandbeasts.entities.CindershellEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class CindershellRenderer extends GeoEntityRenderer<CindershellEntity> {
    private CindershellEntity cindershell;

    public CindershellRenderer(EntityRendererProvider.Context context) {
        super(context, new CindershellModel());
        this.addRenderLayer(new CindershellGlowLayer(this)); // Updated method name for layers
        this.shadowRadius = 0.4F;
    }

    @Override
    protected float getDeathMaxRotation(CindershellEntity entityLivingBaseIn) {
        return 0;
    }

    @Override
    public void preRender(PoseStack poseStack, CindershellEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
        this.cindershell = animatable;
    }

    @Override
    public void renderRecursively(PoseStack poseStack, CindershellEntity animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        if (bone.getName().equals("itemHolder") && !this.cindershell.isInvisible()) {
            poseStack.pushPose();
            poseStack.translate(0, 0.62D, -1.52D);
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F)); // Use Axis for rotations in 1.20.1

            // Ensure the world context is available
            if (Minecraft.getInstance().level != null) {
                Minecraft.getInstance().getItemRenderer().renderStatic(
                        this.cindershell.getItemBySlot(EquipmentSlot.MAINHAND), // Item to render
                        ItemDisplayContext.THIRD_PERSON_LEFT_HAND, // Updated enum
                        packedLight,
                        OverlayTexture.NO_OVERLAY, // Updated overlay handling
                        poseStack,
                        bufferSource,
                        Minecraft.getInstance().level, // World context
                        0 // Render seed
                );
            }

            poseStack.popPose();
            buffer = bufferSource.getBuffer(RenderType.entityTranslucent(getTextureLocation(animatable)));
        }

        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
    }
}