package com.cgessinger.creaturesandbeasts.init;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ToolMaterial;

/**
 * 26.x replaced the {@code Tier} interface with the {@link ToolMaterial} record. Cinder mirrors
 * vanilla diamond-tier mining so it uses the diamond "incorrect" tag (anything diamond can't mine).
 */
public final class CNBItemTiers {
    public static final ToolMaterial CINDER = new ToolMaterial(
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
            800,    // durability
            8.0F,   // speed
            1.0F,   // attack damage bonus
            10,     // enchantment value
            CNBTags.Items.REPAIRS_CINDER);

    private CNBItemTiers() {}
}
