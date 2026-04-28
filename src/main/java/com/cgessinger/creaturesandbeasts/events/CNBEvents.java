package com.cgessinger.creaturesandbeasts.events;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import com.cgessinger.creaturesandbeasts.config.CNBConfig;
import com.cgessinger.creaturesandbeasts.entities.SporelingEntity;
import com.cgessinger.creaturesandbeasts.init.CNBDataComponents;
import com.cgessinger.creaturesandbeasts.init.CNBItems;
import com.cgessinger.creaturesandbeasts.items.HealSpellBookItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.List;

// Game-bus events only. Mod-bus events (entity attributes, etc.) live in CreaturesAndBeasts/ClientEvents.
public class CNBEvents {

    /** Stable id for the yeti-hide armor bonus on a stack — used to find/replace prior bonuses. */
    private static final ResourceLocation HIDE_BONUS_ID =
            ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, "yeti_hide_bonus");

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        if (player.isSecondaryUseActive() && player.getFirstPassenger() instanceof SporelingEntity sporelingEntity) {
            sporelingEntity.stopRiding();
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onLivingTick(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof Player player
                && player.getFirstPassenger() instanceof SporelingEntity sporelingEntity
                && !player.getItemBySlot(EquipmentSlot.CHEST).is(CNBItems.SPORELING_BACKPACK.get())) {
            sporelingEntity.stopRiding();
        }
    }

    /**
     * Anvil interactions:
     *   1) Armor + Yeti Hide → +1 hide layer (capped at config). Bonus armor is baked into the
     *      output stack's {@link DataComponents#ATTRIBUTE_MODIFIERS} so it's applied by vanilla
     *      attribute resolution when the armor is equipped. Replaces the 1.19 NBT + dynamic-event
     *      pipeline since both were removed.
     *   2) HealSpellBook tier-up: lvl1+lvl1 → lvl2 (cost 3), lvl2+lvl2 → lvl3 (cost 6).
     */
    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();
        if (left.isEmpty() || right.isEmpty()) return;

        // ---- Yeti hide reinforcement ----
        if (left.getItem() instanceof ArmorItem armor && right.is(CNBItems.YETI_HIDE.get())) {
            int currentLayers = left.getOrDefault(CNBDataComponents.HIDE_LAYERS.get(), 0);
            int newLayers = currentLayers + 1;
            if (newLayers > CNBConfig.hideAmount) return; // can't reinforce further

            EquipmentSlot slot = armor.getType().getSlot();
            ItemStack output = left.copy();
            output.set(CNBDataComponents.HIDE_LAYERS.get(), newLayers);
            applyHideBonusToStack(output, slot, newLayers);

            event.setOutput(output);
            event.setCost(CNBConfig.hideCost);
            event.setMaterialCost(1);
            return;
        }

        // ---- Heal spell book tier-up ----
        if (left.getItem() instanceof HealSpellBookItem
                && right.getItem() instanceof HealSpellBookItem
                && left.is(right.getItem())) {
            ItemStack output;
            int cost;
            if (left.is(CNBItems.HEAL_SPELL_BOOK_1.get())) {
                output = new ItemStack(CNBItems.HEAL_SPELL_BOOK_2.get());
                cost = 3;
            } else if (left.is(CNBItems.HEAL_SPELL_BOOK_2.get())) {
                output = new ItemStack(CNBItems.HEAL_SPELL_BOOK_3.get());
                cost = 6;
            } else {
                return;
            }
            event.setOutput(output);
            event.setCost(cost);
            event.setMaterialCost(1);
        }
    }

    /**
     * Patches {@link DataComponents#ATTRIBUTE_MODIFIERS} so that any prior hide-bonus modifier on
     * this stack is replaced by a fresh ARMOR modifier valued at {@code hideMultiplier * layers}
     * scoped to the equipped slot. We use {@code withModifierAdded}, which preserves all other
     * entries (including the item's prototype defaults like base armor) and de-dupes by
     * {@code (attribute, modifier id)} — so re-anvilling correctly bumps the bonus instead of
     * stacking duplicates. Operation is {@code ADD_MULTIPLIED_TOTAL} (the 1.21 rename of the
     * old {@code MULTIPLY_TOTAL}) to mirror 1.19 behavior.
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

    /** Tooltip line so the player can see how many hide layers a piece has. */
    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Integer layers = stack.get(CNBDataComponents.HIDE_LAYERS.get());
        if (layers == null || layers <= 0) return;

        List<Component> tooltip = event.getToolTip();
        tooltip.add(Component.translatable("tooltip.cnb.hide_layers", layers, CNBConfig.hideAmount)
                .withStyle(ChatFormatting.GRAY));
    }

    // TODO[1.21.1 port]: re-implement onLootingCalculate (LootingLevelEvent removed in NeoForge — use enchantment provider instead)
}
