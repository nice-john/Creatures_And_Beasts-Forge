package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CNBCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreaturesAndBeasts.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATURES_AND_BEASTS_TAB = CREATIVE_TABS.register(
            "creatures_and_beasts_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(CNBItems.LIZARD_SPAWN_EGG.get()))
                    .title(Component.translatable("itemGroup.creatures_and_beasts_tab"))
                    .displayItems((parameters, output) -> CNBItems.addItemsToCreativeTab(output))
                    .build()
    );
}
