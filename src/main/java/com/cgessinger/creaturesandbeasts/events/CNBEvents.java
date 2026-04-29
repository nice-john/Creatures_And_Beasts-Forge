package com.cgessinger.creaturesandbeasts.events;

import com.cgessinger.creaturesandbeasts.config.CNBConfig;
import com.cgessinger.creaturesandbeasts.entities.SporelingEntity;
import com.cgessinger.creaturesandbeasts.init.CNBItems;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;

/**
 * Fabric event registrations for common (server + client) game events.
 *
 * Events that require mixins are handled in the mixin classes directly
 * (anvil crafting → MixinAnvilMenu, player tick → MixinPlayerTick,
 * item attribute modifiers → MixinItemStack, looting → no direct equivalent needed).
 */
public class CNBEvents {

    public static void register() {
        // When a player right-clicks a block while sneaking and has a Sporeling passenger,
        // eject the Sporeling instead of interacting with the block.
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            if (player.isSecondaryUseActive()
                    && player.getFirstPassenger() instanceof SporelingEntity sporelingEntity) {
                sporelingEntity.stopRiding();
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        });
    }
}
