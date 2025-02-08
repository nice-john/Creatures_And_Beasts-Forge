package com.cgessinger.creaturesandbeasts.client.armor.render;

import com.cgessinger.creaturesandbeasts.client.armor.model.FlowerCrownModel;
import com.cgessinger.creaturesandbeasts.items.FlowerCrownItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

@OnlyIn(Dist.CLIENT)
public class FlowerCrownRenderer extends GeoArmorRenderer<FlowerCrownItem> {
    private ItemStack armorItem; // Declare the armorItem field

    public FlowerCrownRenderer() {
        super(new FlowerCrownModel());

        // Set the bone name for the head
        this.headParts();
    }

    @Override
    public void preRender(
            PoseStack poseStack,
            FlowerCrownItem animatable,
            BakedGeoModel model,
            MultiBufferSource bufferSource,
            VertexConsumer buffer,
            boolean isReRender,
            float partialTick,
            int packedLight,
            int packedOverlay,
            float red,
            float green,
            float blue,
            float alpha
    ) {
        // Store the current item stack for later use
        this.armorItem = animatable.getCurrentItem();
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public RenderType getRenderType(FlowerCrownItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        // Use RenderType.eyes for glowing textures
        return RenderType.eyes(texture);
    }

    public ItemStack getCurrentItem() {
        return this.armorItem;
    }
}