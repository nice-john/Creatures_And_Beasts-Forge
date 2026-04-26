package com.cgessinger.creaturesandbeasts.client.gui.screens.inventory;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.containers.CinderFurnaceContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

// TODO[1.21.1 port]: re-attach the SmeltingRecipeBookComponent overlay once CinderFurnaceContainer
// extends RecipeBookMenu<SingleRecipeInput, SmeltingRecipe> again.
@OnlyIn(Dist.CLIENT)
public class CinderFurnaceScreen extends AbstractContainerScreen<CinderFurnaceContainer> {
    private final ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(
            CreaturesAndBeasts.MOD_ID, "textures/gui/container/cinder_furnace.png");

    public CinderFurnaceScreen(CinderFurnaceContainer cinderFurnaceContainer, Inventory inventory, Component component) {
        super(cinderFurnaceContainer, inventory, component);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;
        guiGraphics.blit(this.texture, x, y, 0, 0, this.imageWidth, this.imageHeight);

        int progress = this.menu.getCookingProgress();
        guiGraphics.blit(this.texture, x + 79, y + 35, 176, 14, progress + 1, 16);
    }
}
