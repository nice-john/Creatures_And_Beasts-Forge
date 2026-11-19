package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.client.entity.model.CactemSpearModel;
import com.cgessinger.creaturesandbeasts.entities.ThrownCactemSpearEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;

@Environment(EnvType.CLIENT)
public class ThrownCactemSpearRenderer extends EntityRenderer<ThrownCactemSpearEntity, ThrownCactemSpearRenderState> {
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/cactem_spear.png");

    private final CactemSpearModel model;

    public ThrownCactemSpearRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new CactemSpearModel(context.bakeLayer(CactemSpearModel.LAYER_LOCATION));
    }

    @Override
    public ThrownCactemSpearRenderState createRenderState() {
        return new ThrownCactemSpearRenderState();
    }

    @Override
    public void extractRenderState(ThrownCactemSpearEntity spear, ThrownCactemSpearRenderState state, float partialTick) {
        super.extractRenderState(spear, state, partialTick);
        state.yRot = spear.getYRot(partialTick);
        state.xRot = spear.getXRot(partialTick);
        state.isFoil = spear.isFoil();
    }

    @Override
    public void submit(ThrownCactemSpearRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        poseStack.pushPose();
        // Rotate the spear based on its yaw and pitch (same orientation as the 1.21.1 renderer).
        poseStack.rotateDegrees(Axis.YP, state.yRot + 180.0F);
        poseStack.rotateDegrees(Axis.XP, state.xRot);
        if (state.isFoil) {
            collector.submitModel(this.model, Unit.INSTANCE, poseStack, RenderTypes.entitySolidGlint(TEXTURE),
                    state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
        } else {
            collector.submitModel(this.model, Unit.INSTANCE, poseStack, TEXTURE,
                    state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
        }
        poseStack.popPose();
        super.submit(state, poseStack, collector, camera);
    }
}