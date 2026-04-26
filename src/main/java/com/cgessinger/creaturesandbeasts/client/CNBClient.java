package com.cgessinger.creaturesandbeasts.client;

import com.cgessinger.creaturesandbeasts.client.gui.screens.inventory.CinderFurnaceScreen;
import com.cgessinger.creaturesandbeasts.init.CNBBlocks;
import com.cgessinger.creaturesandbeasts.init.CNBContainerTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CNBClient {
    public static void init() {
        MenuScreens.register(CNBContainerTypes.CINDER_FURNACE_CONTAINER.get(), CinderFurnaceScreen::new);

        // Waterlily textures have alpha. Without an explicit cutout layer they fall through
        // to the solid render type, which paints transparent pixels black.
        ItemBlockRenderTypes.setRenderLayer(CNBBlocks.PINK_WATERLILY_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CNBBlocks.LIGHT_PINK_WATERLILY_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CNBBlocks.YELLOW_WATERLILY_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CNBBlocks.POTTED_PINK_WATERLILY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CNBBlocks.POTTED_LIGHT_PINK_WATERLILY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CNBBlocks.POTTED_YELLOW_WATERLILY.get(), RenderType.cutout());
    }
}
