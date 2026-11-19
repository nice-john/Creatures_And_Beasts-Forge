package com.cgessinger.creaturesandbeasts.entities;

import com.cgessinger.creaturesandbeasts.init.CNBEntityTypes;
import com.cgessinger.creaturesandbeasts.init.CNBItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;

public class LizardEggEntity extends ThrowableItemProjectile {
    public LizardEggEntity(EntityType<LizardEggEntity> type, Level level) {
        super(type, level);
    }

    public LizardEggEntity(Level worldIn, LivingEntity throwerIn) {
        super(CNBEntityTypes.LIZARD_EGG.get(), throwerIn, worldIn, new ItemStack(CNBItems.LIZARD_EGG.get()));
    }
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            // ThrowableItemProjectile#getItem() returns the synced ITEM_STACK data, which
            // defaults to ItemStack.EMPTY until setItem() is called. Normal throw flow
            // (LizardEggItem#use) sets it, but /summon without an item leaves it empty —
            // and 1.21's ItemParticleOption ctor rejects empty stacks ("Empty stacks are
            // not allowed"). Fall back to a fresh default-item stack in that case.
            ItemStack particleStack = this.getItem();
            if (particleStack.isEmpty()) {
                particleStack = new ItemStack(this.getDefaultItem());
            }
            ItemParticleOption breakParticle = new ItemParticleOption(ParticleTypes.ITEM, ItemStackTemplate.fromNonEmptyStack(particleStack));
            for (int i = 0; i < 8; ++i) {
                this.level().addParticle(breakParticle, this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D);
            }
        }

    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        hitResult.getEntity().hurtOrSimulate(this.damageSources().thrown(this, this.getOwner()), 0.0F);
    }

    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide()) {
            if (this.random.nextFloat() > 0.3F) {
                LizardEntity lizard = CNBEntityTypes.LIZARD.get().create(this.level(), EntitySpawnReason.TRIGGERED);
                lizard.setAge(-24000);
                lizard.snapTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                this.level().addFreshEntity(lizard);
            }

            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }

    }

    protected Item getDefaultItem() {
        return CNBItems.LIZARD_EGG.get();
    }

}
