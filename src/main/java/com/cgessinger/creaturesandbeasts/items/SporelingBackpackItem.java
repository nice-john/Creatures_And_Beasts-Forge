package com.cgessinger.creaturesandbeasts.items;

import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SporelingBackpackItem extends ArmorItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    // GeckoLib Fabric singleton renderer hook. The actual GeoArmorRenderer for this
    // item lives in client/armor/render/SporelingBackpackRenderer.java but is currently
    // disabled — makeRenderer wires up the no-op default until that's re-enabled.
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public SporelingBackpackItem(ArmorMaterial material, Type type, Item.Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<?> state) {
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return cache; }

    @Override
    public Supplier<Object> getRenderProvider() { return this.renderProvider; }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        // GeoArmorRenderer wiring deferred; client/armor/render/SporelingBackpackRenderer.java
        // is the eventual target. consumer left unaccepted means GeoItem.makeRenderer
        // returns its no-op default.
    }
}
