package com.cgessinger.creaturesandbeasts.items;

import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SporelingBackpackItem extends ArmorItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
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

    /**
     * Wire GeckoLib's {@link RenderProvider} so the GeoArmorRenderer in
     * {@code client/armor/render/SporelingBackpackRenderer} drives the in-world armor model.
     */
    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public HumanoidModel<LivingEntity> getHumanoidArmorModel(LivingEntity living, ItemStack stack,
                                                                     EquipmentSlot slot,
                                                                     HumanoidModel<LivingEntity> original) {
                if (this.renderer == null) {
                    this.renderer = new com.cgessinger.creaturesandbeasts.client.armor.render.SporelingBackpackRenderer();
                }
                this.renderer.prepForRender(living, stack, slot, original);
                return (HumanoidModel<LivingEntity>) (HumanoidModel<?>) this.renderer;
            }
        });
    }
}
