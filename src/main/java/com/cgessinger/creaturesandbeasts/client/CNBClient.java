package com.cgessinger.creaturesandbeasts.client;

import com.cgessinger.creaturesandbeasts.client.gui.screens.inventory.CinderFurnaceScreen;
import com.cgessinger.creaturesandbeasts.init.CNBContainerTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.MenuScreens;

@Environment(EnvType.CLIENT)
public class CNBClient {

    public static void init() {
        // Bind the cinder furnace MenuType to its screen - without this, opening the
        // furnace throws "No screen for menu cnb:cinder_furnace_container".
        MenuScreens.register(CNBContainerTypes.CINDER_FURNACE_CONTAINER, CinderFurnaceScreen::new);

        // 26.x needs no code for the rest of the old client setup:
        //  * waterlily cutout layers are derived from the block models' sprite transparency;
        //  * spawn-egg tint overrides are expressed in the client item definitions
        //    (assets/cnb/items/*.json), where the custom full-colour eggs simply omit tints.
    }
}