package com.cgessinger.creaturesandbeasts.client.gui.screens.inventory;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.containers.CinderFurnaceContainer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;

// TODO: re-attach the SmeltingRecipeBookComponent overlay once CinderFurnaceContainer
// extends a recipe-book menu again.
public class CinderFurnaceScreen extends AbstractContainerScreen<CinderFurnaceContainer> {
    private final Identifier texture = Identifier.fromNamespaceAndPath(
            CreaturesAndBeasts.MOD_ID, "textures/gui/container/cinder_furnace.png");

    public CinderFurnaceScreen(CinderFurnaceContainer cinderFurnaceContainer, Inventory inventory, Component component) {
        super(cinderFurnaceContainer, inventory, component);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        super.extractBackground(graphics, mouseX, mouseY, partialTicks);
        int x = this.leftPos;
        int y = this.topPos;
        graphics.blit(RenderPipelines.GUI_TEXTURED, this.texture, x, y, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
        int progress = this.menu.getCookingProgress();
        graphics.blit(RenderPipelines.GUI_TEXTURED, this.texture, x + 79, y + 35, 176.0F, 14.0F, progress + 1, 16, 256, 256);
    }
}