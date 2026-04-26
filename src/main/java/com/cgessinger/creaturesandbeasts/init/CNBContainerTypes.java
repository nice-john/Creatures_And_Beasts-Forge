package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.containers.CinderFurnaceContainer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CNBContainerTypes {
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES =
            DeferredRegister.create(Registries.MENU, CreaturesAndBeasts.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<CinderFurnaceContainer>> CINDER_FURNACE_CONTAINER =
            CONTAINER_TYPES.register("cinder_furnace_container",
                    () -> IMenuTypeExtension.create((windowId, inv, data) -> new CinderFurnaceContainer(windowId, inv)));
}
