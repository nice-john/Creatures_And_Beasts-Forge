package com.cgessinger.creaturesandbeasts.events;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import com.cgessinger.creaturesandbeasts.init.CNBItems;

/**
 * Client-side event / property registrations.
 * Called from {@link com.cgessinger.creaturesandbeasts.CreaturesAndBeastsClient}.
 */
@Environment(EnvType.CLIENT)
public class ClientEvents {

    public static void register() {
        // Cactem spear "throwing" item property – shows raised/drawn animation when in use.
        ItemProperties.register(CNBItems.CACTEM_SPEAR,
                new ResourceLocation("throwing"),
                (item, level, entity, seed) ->
                        entity != null && entity.isUsingItem() && entity.getUseItem() == item
                                ? 1.0F : 0.0F);
    }
}
