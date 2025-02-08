package com.cgessinger.creaturesandbeasts.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.*;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class FlowerCrownItem extends ArmorItem implements GeoItem {
    private final Ingredient repairItems; // Mark final for immutability
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private ItemStack currentItem;

    // Updated constructor to accept repairItems
    public FlowerCrownItem(ArmorMaterial material, Ingredient repairItems, Type type, Properties properties) {
        super(material, type, properties);
        this.repairItems = repairItems; // Initialize repairItems properly
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean isValidRepairItem(ItemStack stackInput, ItemStack repairStack) {
        // Ensure repairItems is not null before testing
        return this.repairItems != null && this.repairItems.test(repairStack);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // Register animation controllers here if needed
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    // Method to get the current item stack
    public ItemStack getCurrentItem() {
        return this.currentItem;
    }

    // Method to set the current item stack
    public void setCurrentItem(ItemStack itemStack) {
        this.currentItem = itemStack;
    }
}
