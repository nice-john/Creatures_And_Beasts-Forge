package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.cgessinger.creaturesandbeasts.client.entity.model.CactemModel;
import com.cgessinger.creaturesandbeasts.entities.CactemEntity;
import com.geckolib.constant.dataticket.DataTicket;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@Environment(EnvType.CLIENT)
public class CactemRenderer extends GeoEntityRenderer<CactemEntity, LivingEntityRenderState> {
    private static final DataTicket<Boolean> SPEAR_SHOWN = DataTicket.create("cnb_cactem_spear_shown", Boolean.class);
    // The baby cactem's hitbox/age scale is 0.75 but the 1.21.1 model was drawn at a flat 0.5.
    private static final float BABY_VISUAL_FACTOR = 0.5F / 0.75F;

    public CactemRenderer(EntityRendererProvider.Context context) {
        super(context, new CactemModel());
        this.shadowRadius = 0.4F;
        this.withRenderLayer(new ItemHolderLayer<>(context, this, "ItemHolder",
                cactem -> cactem.isTrading() ? new ItemStack(Items.TOTEM_OF_UNDYING) : ItemStack.EMPTY));
    }

    @Override
    public RenderType getRenderType(LivingEntityRenderState renderState, Identifier texture) {
        return RenderTypes.entityCutout(texture);
    }

    @Override
    public void addRenderData(CactemEntity cactem, Void relatedObject, LivingEntityRenderState renderState, float partialTick) {
        super.addRenderData(cactem, relatedObject, renderState, partialTick);
        renderState.addGeckolibData(SPEAR_SHOWN, cactem.isSpearShown());
    }

    @Override
    public void scaleModelForRender(RenderPassInfo<LivingEntityRenderState> info, float widthScale, float heightScale) {
        if (info.renderState().isBaby) {
            widthScale *= BABY_VISUAL_FACTOR;
            heightScale *= BABY_VISUAL_FACTOR;
        }
        super.scaleModelForRender(info, widthScale, heightScale);
    }

    // Render the spear bone only while the cactem is holding it.
    @Override
    public void adjustModelBonesForRender(RenderPassInfo<LivingEntityRenderState> info, BoneSnapshots snapshots) {
        super.adjustModelBonesForRender(info, snapshots);
        boolean shown = info.getOrDefaultGeckolibData(SPEAR_SHOWN, true);
        snapshots.ifPresent("spear", snap -> snap.skipRender(!shown));
    }
}