package com.cgessinger.creaturesandbeasts.items;

import com.cgessinger.creaturesandbeasts.capabilities.CinderSwordCapability;
import com.cgessinger.creaturesandbeasts.init.CNBItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class CinderSwordItem extends SwordItem {

    /** Ordered list of all imbue tiers, index 0 = base (no imbue). */
    private static List<CinderSwordItem> IMBUE_TIERS;

    private final int imbueLevel;

    public CinderSwordItem(Tier tier, int imbueLevel, int attackDamageModifier,
                           float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
        this.imbueLevel = imbueLevel;
    }

    // ─── Lazy-init the tier list after all items are registered ──────────────
    private static List<CinderSwordItem> imbueTiers() {
        if (IMBUE_TIERS == null) {
            IMBUE_TIERS = List.of(
                    CNBItems.CINDER_SWORD,   CNBItems.CINDER_SWORD_1,
                    CNBItems.CINDER_SWORD_2, CNBItems.CINDER_SWORD_3,
                    CNBItems.CINDER_SWORD_4);
        }
        return IMBUE_TIERS;
    }

    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (imbueLevel > 0) {
            target.setSecondsOnFire(2 * imbueLevel);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        int imbuedTicks = CinderSwordCapability.getImbuedTicks(stack);

        if (imbuedTicks > 0) {
            CinderSwordCapability.setImbuedTicks(stack, imbuedTicks - 1);
        } else if (imbueLevel > 0 && entity instanceof Player player) {
            // Downgrade the sword by one imbue level
            ItemStack degraded = new ItemStack(imbueTiers().get(imbueLevel - 1));
            if (imbueLevel == 1) {
                player.playSound(SoundEvents.FIRE_EXTINGUISH, 1.0F, 1.0F);
            }
            degraded.setTag(stack.getOrCreateTag().copy());
            // Carry over the imbuedTicks "buffer" into the downgraded sword
            CinderSwordCapability.setImbuedTicks(degraded, 400);
            player.getInventory().setItem(slot, degraded);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        BlockHitResult hit = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);

        if (level.getFluidState(hit.getBlockPos()).is(Fluids.LAVA)) {
            ItemStack imbuedSword = new ItemStack(imbueTiers().get(imbueTiers().size() - 1));
            imbuedSword.setTag(stack.getOrCreateTag().copy());
            CinderSwordCapability.setImbuedTicks(imbuedSword, 400);
            player.setItemInHand(hand, imbuedSword);
            player.playSound(SoundEvents.BUCKET_FILL_LAVA, 1.0F, 1.0F);
            return InteractionResultHolder.success(stack);
        }

        return super.use(level, player, hand);
    }
}
