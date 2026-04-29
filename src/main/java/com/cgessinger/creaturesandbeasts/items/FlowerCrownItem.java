package com.cgessinger.creaturesandbeasts.items;

import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FlowerCrownItem extends ArmorItem implements GeoItem {

    private final Ingredient repairItems;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
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

    /**
     * Wire GeckoLib's {@link RenderProvider} so the GeoArmorRenderer in
     * {@code client/armor/render/FlowerCrownRenderer} drives the in-world armor model.
     *
     * <p>The anonymous {@link RenderProvider} is only loaded when GeoItem invokes the
     * supplier returned by {@link GeoItem#makeRenderer(GeoItem)}, which only happens
     * during client rendering — so dedicated servers never reference the
     * {@code @Environment(EnvType.CLIENT)} renderer class.
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
                    this.renderer = new com.cgessinger.creaturesandbeasts.client.armor.render.FlowerCrownRenderer();
                }
                this.renderer.prepForRender(living, stack, slot, original);
                return (HumanoidModel<LivingEntity>) (HumanoidModel<?>) this.renderer;
            }
        });
    }
}
