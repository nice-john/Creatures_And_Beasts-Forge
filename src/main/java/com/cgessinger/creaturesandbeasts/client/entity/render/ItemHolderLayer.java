package com.cgessinger.creaturesandbeasts.client.entity.render;

import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.layer.builtin.BlockAndItemGeoLayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Function;

/**
 * Draws an item at a named bone of the GeckoLib model (sporeling/cindershell/yeti/cactem
 * "itemHolder" bones). Replaces the 1.21.1 per-bone {@code renderRecursively} overrides: GeckoLib 5
 * positions the item at the bone's pivot using the display context's transform, so the old
 * hand-tuned offsets are not carried over.
 */
@Environment(EnvType.CLIENT)
public class ItemHolderLayer<T extends LivingEntity & GeoAnimatable> extends BlockAndItemGeoLayer<T, Void, LivingEntityRenderState> {
    private final String boneName;
    private final Function<T, ItemStack> stackGetter;

    public ItemHolderLayer(EntityRendererProvider.Context context, GeoRenderer<T, Void, LivingEntityRenderState> renderer,
                           String boneName, Function<T, ItemStack> stackGetter) {
        super(context, renderer);
        this.boneName = boneName;
        this.stackGetter = stackGetter;
    }

    @Override
    protected List<RenderData> getRelevantBones(T entity, Void relatedObject, LivingEntityRenderState renderState, float partialTick) {
        ItemStack stack = this.stackGetter.apply(entity);
        if (stack == null || stack.isEmpty()) {
            return List.of();
        }
        ItemStackRenderState itemState = new ItemStackRenderState();
        this.itemModelResolver.updateForLiving(itemState, stack, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, entity);
        return List.of(RenderData.item(this.boneName, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, itemState));
    }

    @Override
    public void addRenderData(T entity, Void relatedObject, LivingEntityRenderState renderState, float partialTick) {
        renderState.addGeckolibData(CONTENTS, this.getRelevantBones(entity, relatedObject, renderState, partialTick));
    }
}