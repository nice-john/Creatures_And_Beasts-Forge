package com.cgessinger.creaturesandbeasts.client;

import com.cgessinger.creaturesandbeasts.init.CNBBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

@Environment(EnvType.CLIENT)
public class CNBClient {
    public static void init() {
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
