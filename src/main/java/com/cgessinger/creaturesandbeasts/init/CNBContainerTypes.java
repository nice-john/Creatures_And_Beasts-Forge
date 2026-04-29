package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.containers.CinderFurnaceContainer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;

public class CNBContainerTypes {

    public static final MenuType<CinderFurnaceContainer> CINDER_FURNACE_CONTAINER =
            Registry.register(BuiltInRegistries.MENU,
                    new ResourceLocation(CreaturesAndBeasts.MOD_ID, "cinder_furnace_container"),
                    new MenuType<>(CinderFurnaceContainer::new));

    public static void register() {
        CreaturesAndBeasts.LOGGER.debug("Registered CNB container types");
    }
}
