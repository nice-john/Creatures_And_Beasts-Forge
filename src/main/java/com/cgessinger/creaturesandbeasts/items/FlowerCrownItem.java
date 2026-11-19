package com.cgessinger.creaturesandbeasts.items;

import com.cgessinger.creaturesandbeasts.client.armor.render.FlowerCrownRenderer;
import com.geckolib.animatable.GeoItem;
import com.geckolib.animatable.client.GeoRenderProvider;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.renderer.GeoArmorRenderer;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

public class FlowerCrownItem extends Item implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private ItemStack currentItem;

    public FlowerCrownItem(ArmorMaterial material, Properties properties) {
        // Equivalent of Properties#humanoidArmor minus durability/enchantability/repair: the crowns
        // are undamageable and can't be enchanted.
        super(properties
                .attributes(material.createAttributes(ArmorType.HELMET))
                .component(DataComponents.EQUIPPABLE, Equippable.builder(ArmorType.HELMET.getSlot())
                        .setEquipSound(material.equipSound())
                        .setAsset(material.assetId())
                        .build()));
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return false;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private GeoArmorRenderer<?, ?> renderer;

            @Override
            public @Nullable GeoArmorRenderer<?, ?> getGeoArmorRenderer(ItemStack itemStack, EquipmentSlot equipmentSlot) {
                if (this.renderer == null) {
                    this.renderer = new FlowerCrownRenderer();
                }
                return this.renderer;
            }
        });
    }

    public ItemStack getCurrentItem() {
        return this.currentItem;
    }

    public void setCurrentItem(ItemStack itemStack) {
        this.currentItem = itemStack;
    }
}
