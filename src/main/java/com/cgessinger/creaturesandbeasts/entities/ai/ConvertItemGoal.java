package com.cgessinger.creaturesandbeasts.entities.ai;

import com.cgessinger.creaturesandbeasts.entities.SporelingEntity;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.pathfinder.Path;

import java.util.List;

public class ConvertItemGoal extends Goal {
    protected Path path;
    protected ItemEntity itemInstance;
    protected double convertTime;
    protected double convertDelay;

    protected final double speed;
    protected final SporelingEntity entityIn;
    protected final double range;
    protected final PathNavigation navigation;

    public ConvertItemGoal(SporelingEntity entityIn, double range, double speedIn) {
        this.entityIn = entityIn;
        this.range = range;
        this.speed = speedIn;
        this.navigation = entityIn.getNavigation();
    }

    @Override
    public boolean canUse() {
        if (this.entityIn.isInSittingPose()) {
            return false;
        }
        if (this.itemInstance == null) {
            List<ItemEntity> list = this.entityIn.level().getEntitiesOfClass(ItemEntity.class, this.entityIn.getBoundingBox().inflate(this.range, 3.0D, this.range));

            for (ItemEntity item : list) {
                if (item.getItem().is(Items.DIRT) || (item.getItem().isEnchanted() && hasCurse(item.getItem()))) {
                    this.path = this.navigation.createPath(item.getOnPos(), 0);
                    this.itemInstance = item;
                    return path != null;
                }
            }
        }

        return false;
    }

    /**
     * Curse detection in 1.21: {@code Enchantment.isCurse()} is gone, replaced by the
     * {@code #minecraft:curse} enchantment tag ({@link EnchantmentTags#CURSE}). Each enchantment
     * is now a {@link Holder} whose {@code is(TagKey)} resolves tag membership.
     */
    private boolean hasCurse(ItemStack stack) {
        for (Holder<Enchantment> holder : stack.getEnchantments().keySet()) {
            if (holder.is(EnchantmentTags.CURSE)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void stop() {
        this.itemInstance = null;
        this.path = null;
        this.navigation.stop();
        entityIn.setHolding(ItemStack.EMPTY);
        entityIn.setInspecting(false);
    }

    @Override
    public void start() {
        this.navigation.moveTo(this.path, this.speed);
    }

    @Override
    public boolean canContinueToUse() {
        return (!this.navigation.isDone() || this.convertTime > 0) && (!this.itemInstance.isRemoved() || this.entityIn.isInspecting());
    }

    public void convertItem() {
        entityIn.setInspecting(false);

        if (entityIn.getHolding().is(Items.DIRT)) {
            entityIn.spawnAtLocation(new ItemStack(Items.MYCELIUM, 1));
        } else {
            ItemStack returnItem = entityIn.getHolding().copy();

            // Curse-strip: random damage cost (capped at 90% durability), then strip every curse
            // in one pass. 1.19 stripped one curse per pickup as a side-effect of avoiding a CME;
            // with the 1.21 ItemEnchantments mutator that's no longer required, so the player
            // doesn't have to feed a doubly-cursed item back twice.
            if (returnItem.isEnchanted() && hasCurse(returnItem)) {
                if (returnItem.isDamageableItem()) {
                    float percent = entityIn.getRandom().nextFloat() * 0.5F;
                    int damage = (int) (percent * returnItem.getMaxDamage()) + returnItem.getDamageValue();
                    int capped = Math.min(damage, (int) (returnItem.getMaxDamage() * 0.9F));
                    returnItem.setDamageValue(Math.max(returnItem.getDamageValue(), capped));
                }
                EnchantmentHelper.updateEnchantments(returnItem, mut ->
                        mut.removeIf(holder -> holder.is(EnchantmentTags.CURSE)));
            }

            entityIn.spawnAtLocation(returnItem);
            if (entityIn.level() instanceof ServerLevel serverLevel) {
                ExperienceOrb.award(serverLevel, entityIn.position(), entityIn.getRandom().nextInt(16) + 1);
            }
        }

        entityIn.setHolding(ItemStack.EMPTY);
    }

    @Override
    public void tick() {
        if (convertDelay <= 0) {
            if (this.entityIn.distanceToSqr(itemInstance) < 2.0D || this.entityIn.isInspecting()) {
                this.navigation.setSpeedModifier(0.0D);

                if (!this.entityIn.isInspecting() && !itemInstance.isRemoved()) {
                    this.entityIn.setHolding(itemInstance.getItem().copy());
                    itemInstance.getItem().shrink(1);

                    if (itemInstance.getItem().isEmpty()) {
                        itemInstance.discard();
                    }

                    entityIn.setInspecting(true);
                    entityIn.lookAt(EntityAnchorArgument.Anchor.EYES, itemInstance.position());
                    this.convertTime = 54;

                } else {
                    if (--this.convertTime <= 0) {
                        convertItem();
                        convertDelay = 20;

                    } else if (convertTime % 3 == 0) {
                        entityIn.lookAt(EntityAnchorArgument.Anchor.EYES, itemInstance.position());
                        // Use the sporeling's holding stack (the copy made at line 132
                        // before we shrank the source) for the particle. itemInstance.getItem()
                        // here is the ALREADY-SHRUNK source — count==0 if the original stack
                        // had only one item, which trips 1.21's ItemParticleOption empty-stack
                        // guard ("Empty stacks are not allowed"). 1.20 had no such check, so
                        // this only crashes on the 1.21 line.
                        ItemStack particleStack = entityIn.getHolding();
                        if (!particleStack.isEmpty()) {
                            entityIn.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, particleStack), entityIn.getRandomX(0.5F) + entityIn.getLookAngle().x / 2.0D, entityIn.getRandomY(), entityIn.getRandomZ(0.5F) + entityIn.getLookAngle().z / 2.0D, 4D, 0D, 0D);
                        }
                    }
                }

            } else {
                this.navigation.setSpeedModifier(speed);
            }

        } else {
            convertDelay--;
        }
    }
}
