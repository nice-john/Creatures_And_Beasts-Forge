package com.cgessinger.creaturesandbeasts.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = "yourmodid", bus = Mod.EventBusSubscriber.Bus.MOD)
public class CNBCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "cnb");

    public static final RegistryObject<CreativeModeTab> CREATURES_AND_BEASTS_TAB = CREATIVE_TABS.register(
            "creatures_and_beasts_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(CNBItems.LIZARD_SPAWN_EGG.get())) // Replace with a representative item
                    .title(Component.translatable("itemGroup.creatures_and_beasts_tab"))
                    .displayItems((features, output) -> CNBItems.addItemsToCreativeTab(output))
                    .build()
    );


}
