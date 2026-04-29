package com.cgessinger.creaturesandbeasts.mixin;

import com.cgessinger.creaturesandbeasts.entities.SporelingEntity;
import com.cgessinger.creaturesandbeasts.init.CNBItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Replaces Forge's {@code LivingEvent.LivingTickEvent}:
 * Ejects the Sporeling if the player removes the Sporeling Backpack from the chest slot.
 */
@Mixin(Player.class)
public class MixinPlayerTick {

    @Inject(method = "tick", at = @At("TAIL"))
    private void CNB_checkSporelingBackpack(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (player.getFirstPassenger() instanceof SporelingEntity sporelingEntity
                && !player.getItemBySlot(EquipmentSlot.CHEST).is(CNBItems.SPORELING_BACKPACK)) {
            sporelingEntity.stopRiding();
        }
    }
}
