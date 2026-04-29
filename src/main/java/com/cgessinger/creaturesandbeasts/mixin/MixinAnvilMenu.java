package com.cgessinger.creaturesandbeasts.mixin;

import com.cgessinger.creaturesandbeasts.config.CNBConfig;
import com.cgessinger.creaturesandbeasts.init.CNBItems;
import com.cgessinger.creaturesandbeasts.items.HealSpellBookItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Replaces Forge's {@code AnvilUpdateEvent}:
 * – Yeti-hide reinforcement on any armor item.
 * – Heal Spell Book combining.
 */
@Mixin(AnvilMenu.class)
public abstract class MixinAnvilMenu {

    /** The experience-level cost shown above the anvil output slot. */
    @Shadow
    public DataSlot cost;

    @Inject(method = "createResult", at = @At("HEAD"), cancellable = true)
    private void CNB_anvilCreateResult(CallbackInfo ci) {
        AnvilMenu menu = (AnvilMenu) (Object) this;

        ItemStack left  = menu.getSlot(0).getItem();
        ItemStack right = menu.getSlot(1).getItem();
        if (left.isEmpty() || right.isEmpty()) return;

        // ── Yeti-hide reinforcement ───────────────────────────────────────────
        if (left.getItem() instanceof ArmorItem && right.is(CNBItems.YETI_HIDE)) {
            ItemStack output = left.copy();
            CompoundTag nbt = output.getOrCreateTag();
            int hideAmount = nbt.contains("HideAmount") ? nbt.getInt("HideAmount") + 1 : 1;
            if (hideAmount > CNBConfig.hideAmount) return; // cap reached → let vanilla handle

            nbt.putInt("HideAmount", hideAmount);
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
            output.setTag(left.getOrCreateTag().copy());
            cost.set(lvlCost);
            menu.getSlot(2).set(output);
            ci.cancel();
        }
    }
}
