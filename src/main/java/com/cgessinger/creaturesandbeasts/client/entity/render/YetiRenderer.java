package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.cgessinger.creaturesandbeasts.client.entity.model.YetiModel;
import com.cgessinger.creaturesandbeasts.entities.YetiEntity;
import com.geckolib.renderer.base.RenderPassInfo;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.api.distmarker.Dist;
public class YetiRenderer extends CnbEntityRenderer<YetiEntity> {
    public YetiRenderer(EntityRendererProvider.Context context) {
        super(context, new YetiModel(), 0.7F, 1.0F, 1.0F, true, id -> RenderTypes.entityTranslucent(id));
        this.withRenderLayer(new HeldItemBoneLayer<YetiEntity, LivingEntityRenderState>(context, this, "ItemHolder",
                ItemDisplayContext.THIRD_PERSON_LEFT_HAND, YetiEntity::getHolding,
                yeti -> yeti.isBaby()
                        ? HeldItemBoneLayer.Pose.at(0.05F, 0.3F, 0.15F).rotZ(-10.0F).rotY(-43.0F).rotZ(10.0F)
                        : HeldItemBoneLayer.Pose.at(0.3F, 1.0F, 0.0F)));
    }

    // The baby yeti's head bone is modelled lying down, so it yaws around Z.
    @Override
    protected boolean headYawOnZ(RenderPassInfo<LivingEntityRenderState> info) {
        return isBaby(info);
    }
}