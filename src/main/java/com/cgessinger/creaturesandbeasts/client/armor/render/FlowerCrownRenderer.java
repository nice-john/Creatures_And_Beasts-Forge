package com.cgessinger.creaturesandbeasts.client.armor.render;

import com.cgessinger.creaturesandbeasts.client.armor.model.FlowerCrownModel;
import com.cgessinger.creaturesandbeasts.items.FlowerCrownItem;
import com.cgessinger.creaturesandbeasts.items.GlowingFlowerCrownItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

@OnlyIn(Dist.CLIENT)
public class FlowerCrownRenderer extends GeoArmorRenderer<FlowerCrownItem> {
    private ItemStack armorItem;

    public FlowerCrownRenderer() {
        super(new FlowerCrownModel());
        // Bone-to-slot binding is handled by GeckoLib via the `armorHead` bone name in
        // flower_crown.geo.json — see grabRelevantBones() in GeoArmorRenderer (it scans
        // for the literal strings armorHead/armorBody/armorRight*/armorLeft* and pins
        // each to the matching HumanoidModel part). Renaming the model's only bone from
        // "group" to "armorHead" is what makes the crown follow the player's head
        // rotation and inherit the head's transform / scale.
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

    /**
     * Only the {@link GlowingFlowerCrownItem} variant should render full-bright at
     * night. The previous implementation unconditionally returned
     * {@code RenderType.eyes(texture)} (the same render type vanilla uses for
     * spider/enderman eyes) for the regular crown too, which made it glow visibly
     * in the dark even though it isn't the glowing variant. Defer to the default
     * armor render type for the plain crown.
     */
    @Override
    public RenderType getRenderType(FlowerCrownItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        if (animatable instanceof GlowingFlowerCrownItem) {
            return RenderType.eyes(texture);
        }
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    public ItemStack getCurrentItem() {
        return this.armorItem;
    }
}