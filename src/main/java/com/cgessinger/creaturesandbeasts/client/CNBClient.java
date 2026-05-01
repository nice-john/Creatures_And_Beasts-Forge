package com.cgessinger.creaturesandbeasts.client;

import com.cgessinger.creaturesandbeasts.client.gui.screens.inventory.CinderFurnaceScreen;
import com.cgessinger.creaturesandbeasts.init.CNBBlocks;
import com.cgessinger.creaturesandbeasts.init.CNBContainerTypes;
import com.cgessinger.creaturesandbeasts.init.CNBItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
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

        // Disable vanilla SpawnEggItem two-color tint on the spawn eggs that have
        // custom full-color sprites. Vanilla's bulk ItemColor handler multiplies
        // layer 0 by the egg's backgroundColor (and layer 1 by highlightColor),
        // so a textured layer0 with item/generated parent gets washed-out by the
        // primary color even though it's a solid sprite. Registering a per-item
        // passthrough (0xFFFFFFFF = white = no tint) over the top wins because
        // ItemColors is a last-write-wins map keyed by Item.
        ColorProviderRegistry.ITEM.register((stack, layer) -> 0xFFFFFFFF,
                CNBItems.CACTEM_SPAWN_EGG,
                CNBItems.CINDERSHELL_SPAWN_EGG,
                CNBItems.END_WHALE_SPAWN_EGG,
                CNBItems.LILYTAD_SPAWN_EGG,
                CNBItems.MINIPAD_SPAWN_EGG,
                CNBItems.YETI_SPAWN_EGG,
                CNBItems.SPORELING_NETHER_EGG,
                CNBItems.SPORELING_OVERWORLD_EGG);
    }
}
