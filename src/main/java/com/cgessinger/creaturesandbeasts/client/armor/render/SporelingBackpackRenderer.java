package com.cgessinger.creaturesandbeasts.client.armor.render;

import com.cgessinger.creaturesandbeasts.client.armor.model.SporelingBackpackModel;
import com.cgessinger.creaturesandbeasts.items.SporelingBackpackItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

@OnlyIn(Dist.CLIENT)
public class SporelingBackpackRenderer extends GeoArmorRenderer<SporelingBackpackItem> {

    public SporelingBackpackRenderer() {
        super(new SporelingBackpackModel());

        this.headParts();
    }
}
