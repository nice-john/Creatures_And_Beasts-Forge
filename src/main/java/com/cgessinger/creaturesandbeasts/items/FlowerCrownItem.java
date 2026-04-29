package com.cgessinger.creaturesandbeasts.items;

import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FlowerCrownItem extends ArmorItem implements GeoItem {

    private final Ingredient repairItems;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    // GeckoLib Fabric singleton renderer hook. The actual GeoArmorRenderer for this
    // item lives in client/armor/render/FlowerCrownRenderer.java but is currently
    // disabled — makeRenderer wires up the no-op default until that's re-enabled.
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public FlowerCrownItem(ArmorMaterial material, Ingredient repairItems,
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

    @Override
    public Supplier<Object> getRenderProvider() { return this.renderProvider; }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        // GeoArmorRenderer wiring deferred; client/armor/render/FlowerCrownRenderer.java
        // is the eventual target. consumer left unaccepted means GeoItem.makeRenderer
        // returns its no-op default.
    }
}
