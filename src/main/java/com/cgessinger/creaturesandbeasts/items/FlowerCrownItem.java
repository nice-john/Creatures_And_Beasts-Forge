package com.cgessinger.creaturesandbeasts.items;

import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class FlowerCrownItem extends ArmorItem implements GeoItem {

    private final Ingredient repairItems;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public FlowerCrownItem(Holder<ArmorMaterial> material, Ingredient repairItems,
                           Type type, Properties properties) {
        super(material, type, properties);
        this.repairItems = repairItems;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) { return false; }

    @Override
    public boolean isValidRepairItem(ItemStack input, ItemStack repair) {
        return repairItems != null && repairItems.test(repair);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return cache; }

    /**
     * GeckoLib 4.7 pattern: provide a {@link GeoRenderProvider} via {@link #createGeoRenderer(Consumer)}
     * so the cache can hand it back from {@code getRenderProvider()} on demand. The anonymous class
     * is only loaded when the consumer fires (client-side rendering), so dedicated servers won't
     * pull in the {@code @Environment(EnvType.CLIENT)} renderer class.
     */
    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public <T extends LivingEntity> HumanoidModel<?> getGeoArmorRenderer(@Nullable T livingEntity,
                                                                                  ItemStack itemStack,
                                                                                  @Nullable EquipmentSlot equipmentSlot,
                                                                                  @Nullable HumanoidModel<T> original) {
                if (this.renderer == null) {
                    this.renderer = new com.cgessinger.creaturesandbeasts.client.armor.render.FlowerCrownRenderer();
                }
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }
}
