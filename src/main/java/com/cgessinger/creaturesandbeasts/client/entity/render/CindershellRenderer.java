package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.client.entity.model.CindershellModel;
import com.cgessinger.creaturesandbeasts.entities.CindershellEntity;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
public class CindershellRenderer extends CnbEntityRenderer<CindershellEntity> {
    private static final Identifier GLOW_LAYER = Identifier.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "textures/entity/cindershell/cindershell_glow.png");

    public CindershellRenderer(EntityRendererProvider.Context context) {
        super(context, new CindershellModel(), 0.4F, 1.0F, 1.0F, true, id -> RenderTypes.entityTranslucent(id));
        // Emissive overlay, adults only.
        this.withRenderLayer(new TintedTextureLayer<CindershellEntity, LivingEntityRenderState>(this,
                cindershell -> cindershell.isBaby() ? null : new TintedTextureLayer.Pass(GLOW_LAYER, 0xFFFFFFFF),
                RenderTypes::eyes, 1));
        this.withRenderLayer(new HeldItemBoneLayer<CindershellEntity, LivingEntityRenderState>(context, this, "itemHolder",
                ItemDisplayContext.THIRD_PERSON_LEFT_HAND,
                cindershell -> cindershell.isInvisible() ? ItemStack.EMPTY : cindershell.getHolding(),
                cindershell -> HeldItemBoneLayer.Pose.at(0.0F, 0.62F, -1.52F).rotX(90.0F)));
    }

    @Override
    protected float getDeathMaxRotation(GeoRenderState renderState) {
        return 0;
    }
}