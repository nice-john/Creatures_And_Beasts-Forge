package com.cgessinger.creaturesandbeasts.mixin;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.config.CNBConfig;
import com.cgessinger.creaturesandbeasts.init.CNBDataComponents;
import com.cgessinger.creaturesandbeasts.init.CNBItems;
import com.cgessinger.creaturesandbeasts.items.HealSpellBookItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Replaces Forge's {@code AnvilUpdateEvent}:
 * – Yeti-hide reinforcement on any armor item (sets hide-layer count + bakes the armor
 *   bonus into the stack's {@link DataComponents#ATTRIBUTE_MODIFIERS} so vanilla picks
 *   it up automatically when equipped).
 * – Heal Spell Book combining.
 */
@Mixin(AnvilMenu.class)
public abstract class MixinAnvilMenu {

    /** The experience-level cost shown above the anvil output slot. */
    @Shadow
    public DataSlot cost;

    /** Stable id used to find/replace prior hide-bonus modifiers across re-anvils. */
    private static final ResourceLocation HIDE_BONUS_ID =
            ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "yeti_hide_bonus");

    @Inject(method = "createResult", at = @At("HEAD"), cancellable = true)
    private void CNB_anvilCreateResult(CallbackInfo ci) {
        AnvilMenu menu = (AnvilMenu) (Object) this;

        ItemStack left  = menu.getSlot(0).getItem();
        ItemStack right = menu.getSlot(1).getItem();
        if (left.isEmpty() || right.isEmpty()) return;

        // ── Yeti-hide reinforcement ───────────────────────────────────────────
        if (left.getItem() instanceof ArmorItem armorItem && right.is(CNBItems.YETI_HIDE)) {
            int current = left.getOrDefault(CNBDataComponents.HIDE_LAYERS, 0);
            int hideAmount = current + 1;
            if (hideAmount > CNBConfig.hideAmount) return; // cap reached → let vanilla handle

            ItemStack output = left.copy();
            output.set(CNBDataComponents.HIDE_LAYERS, hideAmount);
            applyHideBonusToStack(output, armorItem.getEquipmentSlot(), hideAmount);
            cost.set(CNBConfig.hideCost);
            menu.getSlot(2).set(output);
            ci.cancel();
            return;
        }

        // ── Heal Spell Book combining ─────────────────────────────────────────
        if (left.getItem() instanceof HealSpellBookItem
                && right.getItem() instanceof HealSpellBookItem
                && left.is(right.getItem())) {

            ItemStack output;
            int lvlCost;
            if (left.is(CNBItems.HEAL_SPELL_BOOK_1)) {
                output  = new ItemStack(CNBItems.HEAL_SPELL_BOOK_2);
                lvlCost = 3;
            } else if (left.is(CNBItems.HEAL_SPELL_BOOK_2)) {
                output  = new ItemStack(CNBItems.HEAL_SPELL_BOOK_3);
                lvlCost = 6;
            } else {
                return;
            }
            cost.set(lvlCost);
            menu.getSlot(2).set(output);
            ci.cancel();
        }
    }

    /**
     * Patches {@link DataComponents#ATTRIBUTE_MODIFIERS} so that any prior hide-bonus
     * modifier on this stack is replaced by a fresh ARMOR modifier valued at
     * {@code hideMultiplier * layers} scoped to the equipped slot.
     * {@link ItemAttributeModifiers#withModifierAdded} preserves all other entries
     * (including the item's prototype defaults like base armor) and de-dupes by
     * {@code (attribute, modifier id)}, so re-anvilling correctly bumps the bonus
     * instead of stacking duplicates.
     */
    private static void applyHideBonusToStack(ItemStack stack, EquipmentSlot slot, int layers) {
        ItemAttributeModifiers existing =
                stack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
        double bonus = CNBConfig.hideMultiplier * layers;
        ItemAttributeModifiers updated = existing.withModifierAdded(
                Attributes.ARMOR,
                new AttributeModifier(HIDE_BONUS_ID, bonus, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
                EquipmentSlotGroup.bySlot(slot));
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, updated);
    }
}
