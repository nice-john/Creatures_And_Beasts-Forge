package com.cgessinger.creaturesandbeasts.events;

import com.cgessinger.creaturesandbeasts.entities.SporelingEntity;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.world.InteractionResult;

/**
 * Game-bus event handlers ported from the Forge {@code CNBEvents} class.
 *
 * <p>The other Forge handlers — anvil combine, yeti-hide armor bonus, spear looting,
 * sporeling backpack drop — moved to mixins (see {@link com.cgessinger.creaturesandbeasts.mixin}).
 * Only the right-click-to-dismount sporeling logic stayed at the API-event layer because
 * Fabric exposes a clean callback ({@link UseBlockCallback}) for it.
 */
public final class CNBEvents {

    private CNBEvents() {}

    public static void register() {
        // Sneak-right-click on a block while a sporeling is riding the player → dismount it.
        // Forge's PlayerInteractEvent.RightClickBlock had the same logic. Returning SUCCESS
        // cancels the block interaction so vanilla doesn't also try to use the block.
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            if (player.isSecondaryUseActive()
                    && player.getFirstPassenger() instanceof SporelingEntity sporeling) {
                sporeling.stopRiding();
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        });
    }
}
