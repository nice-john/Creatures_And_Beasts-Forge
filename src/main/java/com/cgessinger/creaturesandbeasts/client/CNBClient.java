package com.cgessinger.creaturesandbeasts.client;

import com.cgessinger.creaturesandbeasts.client.gui.screens.inventory.CinderFurnaceScreen;
import com.cgessinger.creaturesandbeasts.init.CNBBlocks;
import com.cgessinger.creaturesandbeasts.init.CNBContainerTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;

@Environment(EnvType.CLIENT)
public class CNBClient {
    public static void init() {
        MenuScreens.register(CNBContainerTypes.CINDER_FURNACE_CONTAINER, CinderFurnaceScreen::new);

        // Waterlily textures have alpha — must use cutout so transparent pixels aren't painted black.
        BlockRenderLayerMap.INSTANCE.putBlock(CNBBlocks.PINK_WATERLILY_BLOCK,       RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CNBBlocks.LIGHT_PINK_WATERLILY_BLOCK, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CNBBlocks.YELLOW_WATERLILY_BLOCK,     RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CNBBlocks.POTTED_PINK_WATERLILY,      RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CNBBlocks.POTTED_LIGHT_PINK_WATERLILY, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CNBBlocks.POTTED_YELLOW_WATERLILY,    RenderType.cutout());
    }
}
