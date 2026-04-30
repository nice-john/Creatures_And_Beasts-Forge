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
        // Bind the cinder furnace MenuType to its screen — without this, opening the
        // furnace throws "No screen for menu cnb:cinder_furnace_container" and crashes
        // the client back to title.
        MenuScreens.register(CNBContainerTypes.CINDER_FURNACE_CONTAINER, CinderFurnaceScreen::new);

        // Waterlily textures have alpha. Without an explicit cutout layer they fall through
        // to the solid render type, which paints transparent pixels black. On Fabric we use
        // BlockRenderLayerMap (the Forge ItemBlockRenderTypes.setRenderLayer equivalent).
        BlockRenderLayerMap rlm = BlockRenderLayerMap.INSTANCE;
        rlm.putBlock(CNBBlocks.PINK_WATERLILY_BLOCK,         RenderType.cutout());
        rlm.putBlock(CNBBlocks.LIGHT_PINK_WATERLILY_BLOCK,   RenderType.cutout());
        rlm.putBlock(CNBBlocks.YELLOW_WATERLILY_BLOCK,       RenderType.cutout());
        rlm.putBlock(CNBBlocks.POTTED_PINK_WATERLILY,        RenderType.cutout());
        rlm.putBlock(CNBBlocks.POTTED_LIGHT_PINK_WATERLILY,  RenderType.cutout());
        rlm.putBlock(CNBBlocks.POTTED_YELLOW_WATERLILY,      RenderType.cutout());
    }
}
