package com.cgessinger.creaturesandbeasts.init;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ToolMaterial;

public final class CNBItemTiers {
    public static final ToolMaterial CINDER = new ToolMaterial(
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 800, 8.0F, 1.0F, 10, CNBTags.Items.REPAIRS_CINDER_TOOLS);

    private CNBItemTiers() {}
}
