package com.cgessinger.creaturesandbeasts.client.armor.render;

import com.cgessinger.creaturesandbeasts.client.armor.model.FlowerCrownModel;
import com.cgessinger.creaturesandbeasts.items.FlowerCrownItem;
import com.cgessinger.creaturesandbeasts.items.GlowingFlowerCrownItem;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.GeoArmorRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import org.jspecify.annotations.Nullable;
public class FlowerCrownRenderer extends GeoArmorRenderer<FlowerCrownItem, HumanoidRenderState> {
    private static final DataTicket<Boolean> GLOWING = DataTicket.create("cnb_glowing_crown", Boolean.class);

    public FlowerCrownRenderer() {
        // The crown's only bone is named "armorHead", which GeckoLib binds to the wearer's head.
        super(new FlowerCrownModel());
    }

    @Override
    public void captureDefaultRenderState(FlowerCrownItem animatable, RenderData renderData, HumanoidRenderState renderState, float partialTick) {
        super.captureDefaultRenderState(animatable, renderData, renderState, partialTick);
        renderState.addGeckolibData(GLOWING, animatable instanceof GlowingFlowerCrownItem);
    }

    /** Only the glowing variant renders full-bright; the plain crown uses the default armor render type. */
    @Override
    public @Nullable RenderType getRenderType(HumanoidRenderState renderState, Identifier texture) {
        if (renderState.getOrDefaultGeckolibData(GLOWING, false)) {
            return RenderTypes.eyes(texture);
        }
        return super.getRenderType(renderState, texture);
    }
}
