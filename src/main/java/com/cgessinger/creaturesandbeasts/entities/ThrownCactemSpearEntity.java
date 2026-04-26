package com.cgessinger.creaturesandbeasts.entities;

import com.cgessinger.creaturesandbeasts.init.CNBEntityTypes;
import com.cgessinger.creaturesandbeasts.init.CNBItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

// TODO[1.21.1 port]: re-implement loyalty/foil/fire-aspect/knockback enchantment behaviors via the
// 1.21 enchantment effect system. Spear save/load needs the HolderLookup.Provider variants of
// ItemStack.parseOptional / ItemStack.save.
public class ThrownCactemSpearEntity extends AbstractArrow {
    private static final EntityDataAccessor<Boolean> IS_FOIL = SynchedEntityData.defineId(ThrownCactemSpearEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<ItemStack> SPEAR = SynchedEntityData.defineId(ThrownCactemSpearEntity.class, EntityDataSerializers.ITEM_STACK);
    private boolean dealtDamage;
    public int clientSideReturnSpearTickCount;

    public ThrownCactemSpearEntity(EntityType<? extends ThrownCactemSpearEntity> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownCactemSpearEntity(Level level, LivingEntity entity, ItemStack itemStack) {
        super(CNBEntityTypes.THROWN_CACTEM_SPEAR.get(), entity, level, itemStack, null);
        this.entityData.set(IS_FOIL, itemStack.hasFoil());
        this.entityData.set(SPEAR, itemStack);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(IS_FOIL, false);
        builder.define(SPEAR, new ItemStack(CNBItems.CACTEM_SPEAR.get()));
    }

    @Override
    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }
        super.tick();
    }

    @Override
    protected ItemStack getPickupItem() {
        return this.getSpear().copy();
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(CNBItems.CACTEM_SPEAR.get());
    }

    public boolean isFoil() {
        return this.entityData.get(IS_FOIL);
    }

    public ItemStack getSpear() {
        return this.entityData.get(SPEAR);
    }

    @Nullable
    @Override
    protected EntityHitResult findHitEntity(Vec3 vec1, Vec3 vec2) {
        return this.dealtDamage ? null : super.findHitEntity(vec1, vec2);
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        Entity hitEntity = hitResult.getEntity();
        float f = 5.0F;
        Entity projectileThrower = this.getOwner();
        DamageSource damagesource = this.level().damageSources().thrown(this, (projectileThrower == null ? this : projectileThrower));
        this.dealtDamage = true;
        SoundEvent soundevent = SoundEvents.TRIDENT_HIT;
        if (hitEntity.hurt(damagesource, f)) {
            if (hitEntity.getType() == EntityType.ENDERMAN) {
                return;
            }
            if (hitEntity instanceof LivingEntity hitLivingEntity) {
                this.doPostHurtEffects(hitLivingEntity);
            }
        }
        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        this.playSound(soundevent, 1.0F, 1.0F);
    }

    @Override
    protected boolean tryPickup(Player player) {
        return super.tryPickup(player) || this.isNoPhysics() && this.ownedBy(player) && player.getInventory().add(this.getPickupItem());
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    @Override
    public void playerTouch(Player player) {
        if (this.ownedBy(player) || this.getOwner() == null) {
            super.playerTouch(player);
        }
    }

    @Override
    public boolean shouldRender(double x, double y, double z) {
        return true;
    }
}
