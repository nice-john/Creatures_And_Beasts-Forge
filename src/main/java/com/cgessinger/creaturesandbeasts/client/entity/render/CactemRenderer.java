package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.cgessinger.creaturesandbeasts.client.entity.model.CactemModel;
import com.cgessinger.creaturesandbeasts.entities.CactemEntity;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.RenderPassInfo;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import org.jspecify.annotations.Nullable;
public class CactemRenderer extends CnbEntityRenderer<CactemEntity> {
    private static final DataTicket<Boolean> SPEAR_SHOWN = DataTicket.create("cnb_cactem_spear_shown", Boolean.class);

    public CactemRenderer(EntityRendererProvider.Context context) {
        super(context, new CactemModel(), 0.4F, 0.5F, 1.0F, false, id -> RenderTypes.entityCutout(id));
        // A Totem of Undying is shown in the cactem's hand while it trades.
        this.withRenderLayer(new HeldItemBoneLayer<CactemEntity, LivingEntityRenderState>(context, this, "ItemHolder",
                ItemDisplayContext.THIRD_PERSON_LEFT_HAND,
                cactem -> cactem.isTrading() ? new ItemStack(Items.TOTEM_OF_UNDYING) : ItemStack.EMPTY,
                cactem -> HeldItemBoneLayer.Pose.at(-0.2F, 0.15F, -0.05F).rotX(-90.0F).scaled(0.5F)));
    }

    @Override
    public void addRenderData(CactemEntity animatable, @Nullable Void relatedObject, LivingEntityRenderState renderState, float partialTick) {
        super.addRenderData(animatable, relatedObject, renderState, partialTick);
        renderState.addGeckolibData(SPEAR_SHOWN, animatable.isSpearShown());
    }

    // The spear is only drawn while the cactem is still holding it.
    @Override
    public void adjustModelBonesForRender(RenderPassInfo<LivingEntityRenderState> info, BoneSnapshots snapshots) {
        super.adjustModelBonesForRender(info, snapshots);
        if (!info.renderState().getOrDefaultGeckolibData(SPEAR_SHOWN, true)) {
            snapshots.get("spear").ifPresent(bone -> bone.setScale(0.0F, 0.0F, 0.0F));
        }
    }
}