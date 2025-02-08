package com.cgessinger.creaturesandbeasts.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

public class SporelingBackpackItem extends ArmorItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public SporelingBackpackItem(ArmorMaterial material, ArmorItem.Type type, Item.Properties properties) {
        super(material, type, properties);
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // Register your animation controllers here
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<?> state) {
        // Define your animation logic here
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object itemStack) {
        return RenderUtils.getCurrentTick();
    }
}