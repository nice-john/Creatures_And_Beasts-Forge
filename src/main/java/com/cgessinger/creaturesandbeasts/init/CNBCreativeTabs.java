package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CNBCreativeTabs {

    public static final CreativeModeTab CREATURES_AND_BEASTS_TAB =
            Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
                    new ResourceLocation(CreaturesAndBeasts.MOD_ID, "creatures_and_beasts_tab"),
                    FabricItemGroup.builder()
                            .icon(() -> new ItemStack(CNBItems.LIZARD_SPAWN_EGG))
                            .title(Component.translatable("itemGroup.creatures_and_beasts_tab"))
                            .displayItems((features, output) -> CNBItems.addItemsToCreativeTab(output))
                            .build());

    public static void register() {
        CreaturesAndBeasts.LOGGER.debug("Registered CNB creative tabs");
    }
}
