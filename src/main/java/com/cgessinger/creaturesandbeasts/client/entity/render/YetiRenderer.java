package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.cgessinger.creaturesandbeasts.client.entity.model.YetiModel;
import com.cgessinger.creaturesandbeasts.entities.YetiEntity;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.RenderPassInfo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class YetiRenderer extends GeoEntityRenderer<YetiEntity, LivingEntityRenderState> {
    private static final float DEG_TO_RAD = (float) Math.PI / 180F;

    public YetiRenderer(EntityRendererProvider.Context context) {
        super(context, new YetiModel());
        this.shadowRadius = 0.7F;
        this.withRenderLayer(new ItemHolderLayer<>(context, this, "ItemHolder", YetiEntity::getHolding));
    }

    @Override
    public RenderType getRenderType(LivingEntityRenderState renderState, Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }

    // The baby yeti's head bone pivots about Z instead of Y (model orientation), as in 1.21.1.
    @Override
    public void adjustModelBonesForRender(RenderPassInfo<LivingEntityRenderState> info, BoneSnapshots snapshots) {
        super.adjustModelBonesForRender(info, snapshots);
        LivingEntityRenderState state = info.renderState();
        snapshots.ifPresent("head_rotation", snap -> {
            snap.setRotX(snap.getRotX() + state.xRot * DEG_TO_RAD);
            if (state.isBaby) {
                snap.setRotZ(snap.getRotZ() + state.yRot * DEG_TO_RAD);
            } else {
                snap.setRotY(snap.getRotY() + state.yRot * DEG_TO_RAD);
            }
        });
    }
}