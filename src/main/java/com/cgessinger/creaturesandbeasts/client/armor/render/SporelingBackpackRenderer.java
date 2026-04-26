package com.cgessinger.creaturesandbeasts.client.armor.render;

import com.cgessinger.creaturesandbeasts.client.armor.model.SporelingBackpackModel;
import com.cgessinger.creaturesandbeasts.items.SporelingBackpackItem;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.GeoItemRenderer;

@OnlyIn(Dist.CLIENT)
public class SporelingBackpackRenderer extends GeoArmorRenderer<SporelingBackpackItem> {

    public SporelingBackpackRenderer() {
        super(new SporelingBackpackModel());
    }
}


