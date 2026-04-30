package com.cgessinger.creaturesandbeasts.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import com.cgessinger.creaturesandbeasts.init.CNBBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;

@Environment(EnvType.CLIENT)
public class CNBClient {
    public static void init() {
        // MenuScreens.register moved to ClientEvents#onRegisterMenuScreens (RegisterMenuScreensEvent)
        // because MenuScreens.register is package-private in 1.21.1.

        // Waterlily textures have alpha. Without an explicit cutout layer they fall through
        // to the solid render type, which paints transparent pixels black.
        ItemBlockRenderTypes.setRenderLayer(CNBBlocks.PINK_WATERLILY_BLOCK, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CNBBlocks.LIGHT_PINK_WATERLILY_BLOCK, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CNBBlocks.YELLOW_WATERLILY_BLOCK, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CNBBlocks.POTTED_PINK_WATERLILY, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CNBBlocks.POTTED_LIGHT_PINK_WATERLILY, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CNBBlocks.POTTED_YELLOW_WATERLILY, RenderType.cutout());
    }
}
