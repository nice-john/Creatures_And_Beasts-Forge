package com.cgessinger.creaturesandbeasts.entities;

import com.cgessinger.creaturesandbeasts.init.CNBEntityTypes;
import com.cgessinger.creaturesandbeasts.init.CNBItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;

public class LizardEggEntity extends ThrowableItemProjectile {
    public LizardEggEntity(EntityType<LizardEggEntity> type, Level level) {
        super(type, level);
    }

    public LizardEggEntity(Level worldIn, LivingEntity throwerIn) {
        super(CNBEntityTypes.LIZARD_EGG.get(), throwerIn, worldIn);
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            // ThrowableItemProjectile#getItem() returns the synced ITEM_STACK data, which
            // defaults to ItemStack.EMPTY until setItem() is called. Normal throw flow
            // (LizardEggItem#use) sets it, but /summon without an item leaves it empty —
            // and 1.21's ItemParticleOption ctor rejects empty stacks ("Empty stacks are
            // not allowed"). Fall back to a fresh default-item stack in that case.
            net.minecraft.world.item.ItemStack particleStack = this.getItem();
            if (particleStack.isEmpty()) {
                particleStack = new net.minecraft.world.item.ItemStack(this.getDefaultItem());
            }
            for (int i = 0; i < 8; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, particleStack), this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D);
            }
        }

    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        hitResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0F);
    }

    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            if (this.random.nextFloat() > 0.3F) {
                LizardEntity lizard = CNBEntityTypes.LIZARD.get().create(this.level());
                lizard.setAge(-24000);
                lizard.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                this.level().addFreshEntity(lizard);
            }

            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }

    }

    protected Item getDefaultItem() {
        return CNBItems.LIZARD_EGG.get();
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(net.minecraft.server.level.ServerEntity entity) {
        return new net.minecraft.network.protocol.game.ClientboundAddEntityPacket(this, entity);
    }
}
