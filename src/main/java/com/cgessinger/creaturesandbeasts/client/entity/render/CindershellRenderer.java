package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.cgessinger.creaturesandbeasts.client.entity.model.CindershellModel;
import com.cgessinger.creaturesandbeasts.entities.CindershellEntity;
import com.geckolib.constant.DefaultAnimations;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.RenderPassInfo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class CindershellRenderer extends GeoEntityRenderer<CindershellEntity, LivingEntityRenderState> {
    public CindershellRenderer(EntityRendererProvider.Context context) {
        super(context, new CindershellModel());
        this.shadowRadius = 0.4F;
        this.withRenderLayer(new CindershellGlowLayer(this));
        this.withRenderLayer(new ItemHolderLayer<>(context, this, "itemHolder",
                cindershell -> cindershell.isInvisible() ? ItemStack.EMPTY : cindershell.getItemBySlot(EquipmentSlot.MAINHAND)));
    }

    @Override
    public void adjustModelBonesForRender(RenderPassInfo<LivingEntityRenderState> info, BoneSnapshots snapshots) {
        super.adjustModelBonesForRender(info, snapshots);
        DefaultAnimations.hardcodedHeadRotation(info, snapshots, "head_rotation");
    }
}