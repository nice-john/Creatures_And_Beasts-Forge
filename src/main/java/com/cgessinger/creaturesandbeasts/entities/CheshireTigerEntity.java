package com.cgessinger.creaturesandbeasts.entities;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

/**
 * Cheshire Tiger — placeholder mob skeleton.
 *
 * <p>Intentionally bare-bones: no goals, no synched data, no behavior beyond
 * what {@link PathfinderMob} provides by default. Spawnable via the spawn egg
 * registered in {@code CNBItems#CHESHIRE_TIGER_SPAWN_EGG} for testing /
 * iteration on the model + texture + animation files (which live at the paths
 * referenced in {@code client.entity.model.CheshireTigerModel}).
 *
 * <p>Add goals, synched data, and extra attributes here as the design firms up.
 */
public class CheshireTigerEntity extends PathfinderMob implements GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public CheshireTigerEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 24.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.30D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // Animations TBD — controller list intentionally empty until the
        // animation file is dropped in and we know which animation names exist.
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
