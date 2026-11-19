package com.cgessinger.creaturesandbeasts.events;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;




/**
 * Client-side event / property registrations.
 * Called from {@link com.cgessinger.creaturesandbeasts.CreaturesAndBeastsClient}.
 */
@Environment(EnvType.CLIENT)
public class ClientEvents {

    public static void register() {
        // 26.x removed ItemProperties; the cactem spear "throwing" pose is now a `minecraft:using_item`
        // condition in assets/cnb/items/cactem_spear.json, so nothing needs registering in code.
    }
}
