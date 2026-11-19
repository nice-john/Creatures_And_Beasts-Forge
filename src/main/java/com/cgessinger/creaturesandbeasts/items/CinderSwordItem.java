package com.cgessinger.creaturesandbeasts.items;

import net.minecraft.world.InteractionResult;
import com.cgessinger.creaturesandbeasts.init.CNBDataComponents;
import net.minecraft.world.InteractionResult;
import com.cgessinger.creaturesandbeasts.init.CNBItems;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;

import net.minecraft.world.InteractionResult;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;


import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;

import net.minecraft.world.InteractionResult;
import java.util.List;

/**
 * Sword that progressively heats up to imbue tiers (0â€“4). Hitting an enemy ignites them for
 * 2Ã— imbueLevel seconds. Right-clicking while looking at lava replaces the sword with the max-
 * tier variant and stamps an {@link CNBDataComponents#IMBUED_TICKS} component (400). Each tick
 * inventoryTick decrements the counter; on zero the sword swaps to the next-lower tier (which
 * gets its own fresh 400-tick window) until imbueLevel 0 is reached.
 */
public class CinderSwordItem extends Item {
    /** Imbue tier countdown in ticks. */
    private static final int IMBUE_DURATION = 400;

    private final int imbueLevel;

    public CinderSwordItem(int imbueLevel, Properties properties) {
        super(properties);
        this.imbueLevel = imbueLevel;
    }

    public int getImbueLevel() {
        return this.imbueLevel;
    }

    /** Ordered list of cinder-sword item suppliers, indexed by imbueLevel (0 = base, 4 = max). */
    private static List<CinderSwordItem> tiers() {
        return List.of(
                CNBItems.CINDER_SWORD,
                CNBItems.CINDER_SWORD_1,
                CNBItems.CINDER_SWORD_2,
                CNBItems.CINDER_SWORD_3,
                CNBItems.CINDER_SWORD_4
        );
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity targetEntity, LivingEntity attackingEntity) {
        if (this.imbueLevel > 0) {
            targetEntity.igniteForSeconds(2 * this.imbueLevel);
        }
        super.hurtEnemy(stack, targetEntity, attackingEntity);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, EquipmentSlot equipmentSlot) {
        super.inventoryTick(stack, level, entity, equipmentSlot);
        if (!(entity instanceof Player player)) return;
        int slot = -1;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            if (player.getInventory().getItem(i) == stack) { slot = i; break; }
        }

        Integer ticks = stack.get(CNBDataComponents.IMBUED_TICKS);
        if (ticks == null) return;

        if (ticks > 0) {
            stack.set(CNBDataComponents.IMBUED_TICKS, ticks - 1);
        } else if (this.imbueLevel > 0) {
            // Degrade to next lower tier; that tier gets its own 400-tick window.
            Item lowerTier = tiers().get(this.imbueLevel - 1);
            ItemStack lowered = stack.transmuteCopy(lowerTier);
            lowered.set(CNBDataComponents.IMBUED_TICKS, IMBUE_DURATION);
            if (this.imbueLevel == 1) {
                player.playSound(SoundEvents.FIRE_EXTINGUISH, 1.0F, 1.0F);
            }
            if (slot >= 0) player.getInventory().setItem(slot, lowered);
        } else {
            // imbueLevel == 0: the cooldown finished on the base tier â€” clear the component
            // so future inventoryTicks don't keep re-running this branch.
            stack.remove(CNBDataComponents.IMBUED_TICKS);
        }
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        BlockHitResult hit = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        BlockPos pos = hit.getBlockPos();

        if (level.getFluidState(pos).is(Fluids.LAVA)) {
            // Lava-dip: jump straight to max tier with a fresh 400-tick countdown.
            Item maxTier = tiers().get(tiers().size() - 1);
            ItemStack imbued = itemstack.transmuteCopy(maxTier);
            imbued.set(CNBDataComponents.IMBUED_TICKS, IMBUE_DURATION);
            player.setItemInHand(hand, imbued);
            player.playSound(SoundEvents.BUCKET_FILL_LAVA, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        }
        return super.use(level, player, hand);
    }
}
