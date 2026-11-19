package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.client.entity.model.CactemSpearModel;
import com.cgessinger.creaturesandbeasts.entities.ThrownCactemSpearEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;
import net.neoforged.api.distmarker.Dist;
public class ThrownCactemSpearRenderer extends EntityRenderer<ThrownCactemSpearEntity, ThrownCactemSpearRenderer.State> {
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/cactem_spear.png");

    public static class State extends EntityRenderState {
        public float xRot;
        public float yRot;
        public boolean isFoil;
    }

    private final CactemSpearModel model;

    public ThrownCactemSpearRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new CactemSpearModel(context.bakeLayer(CactemSpearModel.LAYER_LOCATION));
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(ThrownCactemSpearEntity entity, State state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.yRot = entity.getYRot(partialTicks);
        state.xRot = entity.getXRot(partialTicks);
        state.isFoil = entity.isFoil();
    }

    @Override
    public void submit(State state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        poseStack.pushPose();
        // Rotate the spear based on its yaw and pitch
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