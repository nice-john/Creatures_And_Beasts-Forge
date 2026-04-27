package com.cgessinger.creaturesandbeasts.entities;

import com.cgessinger.creaturesandbeasts.init.CNBEntityTypes;
import com.cgessinger.creaturesandbeasts.init.CNBItems;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
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
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

/**
 * Thrown spear projectile. Honors Loyalty (return-to-owner), Fire Aspect (ignite on hit), and
 * Knockback (push on hit) by querying enchantment levels on the carried spear ItemStack.
 */
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
        this.entityData.set(SPEAR, itemStack.copy());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(IS_FOIL, false);
        builder.define(SPEAR, new ItemStack(CNBItems.CACTEM_SPEAR.get()));
    }

    // ---- Save/load (1.21 ItemStack save/load API takes a HolderLookup.Provider) ----
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        ItemStack spear = this.getSpear();
        if (!spear.isEmpty()) {
            tag.put("CactemSpear", spear.save(this.registryAccess(), new CompoundTag()));
        }
        tag.putBoolean("DealtDamage", this.dealtDamage);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("CactemSpear", 10)) {
            ItemStack.parse(this.registryAccess(), tag.getCompound("CactemSpear"))
                    .ifPresent(stack -> this.entityData.set(SPEAR, stack));
        }
        this.dealtDamage = tag.getBoolean("DealtDamage");
    }

    @Override
    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }

        // Loyalty: return-to-owner once we've stuck or are no-clipping back.
        Entity owner = this.getOwner();
        int loyaltyLevel = enchantLevel(Enchantments.LOYALTY);
        if (loyaltyLevel > 0 && (this.dealtDamage || this.isNoPhysics()) && owner != null) {
            if (!isAcceptibleReturnOwner()) {
                if (!this.level().isClientSide && this.pickup == AbstractArrow.Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }
                this.discard();
            } else {
                this.setNoPhysics(true);
                Vec3 toOwner = owner.getEyePosition().subtract(this.position());
                this.setPosRaw(this.getX(),
                        this.getY() + toOwner.y * 0.015D * (double) loyaltyLevel,
                        this.getZ());
                if (this.level().isClientSide) {
                    this.yOld = this.getY();
                }
                double d0 = 0.05D * (double) loyaltyLevel;
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(toOwner.normalize().scale(d0)));
                if (this.clientSideReturnSpearTickCount == 0) {
                    this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
                }
                ++this.clientSideReturnSpearTickCount;
            }
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

    private boolean isAcceptibleReturnOwner() {
        Entity owner = this.getOwner();
        if (owner != null && owner.isAlive()) {
            return !(owner instanceof ServerPlayer) || !owner.isSpectator();
        }
        return false;
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        Entity hit = hitResult.getEntity();
        float damage = 5.0F;

        Entity thrower = this.getOwner();
        DamageSource source = this.level().damageSources().thrown(this, thrower == null ? this : thrower);
        this.dealtDamage = true;
        SoundEvent soundEvent = SoundEvents.TRIDENT_HIT;

        if (hit.hurt(source, damage)) {
            if (hit.getType() == EntityType.ENDERMAN) return;

            if (hit instanceof LivingEntity hitLiving) {
                // Fire Aspect: 4 sec/level burn (mirrors 1.20.1 behavior).
                int fireAspect = enchantLevel(Enchantments.FIRE_ASPECT);
                if (fireAspect > 0) {
                    hit.igniteForSeconds(fireAspect * 4);
                }

                // Knockback: scaled by enchant level, projected onto horizontal velocity vector.
                int knockback = enchantLevel(Enchantments.KNOCKBACK);
                if (knockback > 0) {
                    Vec3 push = this.getDeltaMovement()
                            .multiply(1.0D, 0.0D, 1.0D)
                            .normalize()
                            .scale(knockback * 0.6D);
                    if (push.lengthSqr() > 0.0D) {
                        hit.push(push.x, 0.1D, push.z);
                    }
                }
                this.doPostHurtEffects(hitLiving);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        this.playSound(soundEvent, 1.0F, 1.0F);
    }

    @Override
    protected boolean tryPickup(Player player) {
        return super.tryPickup(player)
                || (this.isNoPhysics() && this.ownedBy(player) && player.getInventory().add(this.getPickupItem()));
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

    /** Look up an enchantment level on the carried spear ItemStack via the level's registry. */
    private int enchantLevel(ResourceKey<Enchantment> key) {
        Holder<Enchantment> holder = this.level().registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .getOrThrow(key);
        return EnchantmentHelper.getItemEnchantmentLevel(holder, this.getSpear());
    }
}
