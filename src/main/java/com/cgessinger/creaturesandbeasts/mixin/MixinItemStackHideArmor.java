package com.cgessinger.creaturesandbeasts.mixin;

import com.cgessinger.creaturesandbeasts.config.CNBConfig;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

/**
 * Replaces Forge's {@code ItemAttributeModifierEvent}: when a player wears armor that has
 * a {@code HideAmount} NBT tag (set by the anvil yeti-hide reinforcement in
 * {@link MixinAnvilMenu}), inject a bonus ARMOR attribute modifier scaled by
 * {@link CNBConfig#hideMultiplier}.
 *
 * <p>Hooks {@code ItemStack#getAttributeModifiers(EquipmentSlot)} on RETURN, copies the
 * existing immutable multimap into a mutable one, and adds the modifier. Per-slot UUIDs
 * keep the modifier deterministic (vanilla AttributeMap de-dupes by UUID within an
 * attribute, so re-equipping doesn't stack duplicates).
 */
@Mixin(ItemStack.class)
public class MixinItemStackHideArmor {

    private static final UUID HEAD_UUID  = UUID.fromString("96a6b318-81f1-475a-b4a4-b3da41d2711e");
    private static final UUID CHEST_UUID = UUID.fromString("3f3136ff-4f04-4d62-a9cc-8d1f4175c1e2");
    private static final UUID LEGS_UUID  = UUID.fromString("f49d078c-2740-4283-8255-5d1f106efea0");
    private static final UUID FEET_UUID  = UUID.fromString("b16e7c3f-508d-461d-8868-de6ee2a1314c");

    @Inject(method = "getAttributeModifiers", at = @At("RETURN"), cancellable = true)
    private void CNB_addYetiHideArmor(EquipmentSlot slot,
                                      CallbackInfoReturnable<Multimap<Attribute, AttributeModifier>> cir) {
        ItemStack stack = (ItemStack) (Object) this;
        if (!(stack.getItem() instanceof ArmorItem armorItem)) return;
        if (slot != armorItem.getEquipmentSlot()) return;

        CompoundTag tag = stack.getTag();
        if (tag == null || !tag.contains("HideAmount")) return;
        int amount = tag.getInt("HideAmount");
        if (amount <= 0) return;

        UUID uuid = uuidFor(slot);
        if (uuid == null) return;

        Multimap<Attribute, AttributeModifier> result = HashMultimap.create();
        result.putAll(cir.getReturnValue());
        result.put(Attributes.ARMOR, new AttributeModifier(
                uuid,
                "yeti_hide",
                CNBConfig.hideMultiplier * amount,
                AttributeModifier.Operation.MULTIPLY_TOTAL));
        cir.setReturnValue(result);
    }

    private static UUID uuidFor(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD  -> HEAD_UUID;
            case CHEST -> CHEST_UUID;
            case LEGS  -> LEGS_UUID;
            case FEET  -> FEET_UUID;
            default    -> null;
        };
    }
}
