package com.cgessinger.creaturesandbeasts.client.armor.render;

import com.cgessinger.creaturesandbeasts.client.armor.model.SporelingBackpackModel;
import com.cgessinger.creaturesandbeasts.items.SporelingBackpackItem;
import com.geckolib.renderer.GeoArmorRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.api.distmarker.Dist;

import java.util.List;
public class SporelingBackpackRenderer extends GeoArmorRenderer<SporelingBackpackItem, HumanoidRenderState> {
    public SporelingBackpackRenderer() {
        super(new SporelingBackpackModel());
    }

    // The backpack geo uses a single "main" bone. It sits on the wearer's head (as it did on 1.21.1),
    // so attach it to the head segment even though the item occupies the chest slot.
    @Override
    public List<ArmorSegment> getSegmentsForSlot(HumanoidRenderState renderState, EquipmentSlot slot) {
        return slot == EquipmentSlot.CHEST ? List.of(ArmorSegment.HEAD) : List.of();
    }

    @Override
    public String getBoneNameForSegment(HumanoidRenderState renderState, ArmorSegment segment) {
        return "main";
    }
}
