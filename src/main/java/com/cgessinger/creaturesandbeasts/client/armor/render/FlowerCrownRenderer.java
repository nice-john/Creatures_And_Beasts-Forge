package com.cgessinger.creaturesandbeasts.client.armor.render;

import com.cgessinger.creaturesandbeasts.client.armor.model.FlowerCrownModel;
import com.cgessinger.creaturesandbeasts.items.FlowerCrownItem;
import com.cgessinger.creaturesandbeasts.items.GlowingFlowerCrownItem;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.GeoArmorRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class FlowerCrownRenderer extends GeoArmorRenderer<FlowerCrownItem, HumanoidRenderState> {
    private static final DataTicket<Boolean> GLOWING = DataTicket.create("cnb_glowing_crown", Boolean.class);

    public FlowerCrownRenderer() {
        super(new FlowerCrownModel());
    }

    @Override
    public void addRenderData(FlowerCrownItem item, GeoArmorRenderer.RenderData relatedObject, HumanoidRenderState renderState, float partialTick) {
        super.addRenderData(item, relatedObject, renderState, partialTick);
        renderState.addGeckolibData(GLOWING, item instanceof GlowingFlowerCrownItem);
    }

    /**
     * Only the {@link GlowingFlowerCrownItem} variant renders full-bright at night; the plain crown
     * uses the default armor render type.
     */
    @Override
    public RenderType getRenderType(HumanoidRenderState renderState, Identifier texture) {
        if (renderState.getOrDefaultGeckolibData(GLOWING, false)) {
            return RenderTypes.eyes(texture);
        }
        return super.getRenderType(renderState, texture);
    }
}