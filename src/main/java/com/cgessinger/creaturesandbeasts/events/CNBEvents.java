package com.cgessinger.creaturesandbeasts.events;

import com.cgessinger.creaturesandbeasts.entities.SporelingEntity;
import com.cgessinger.creaturesandbeasts.init.CNBItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

// Game-bus events only. Mod-bus events (entity attributes, etc.) live in CreaturesAndBeasts/ClientEvents.
public class CNBEvents {

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

    // TODO[1.21.1 port]: re-implement onLootingCalculate (LootingLevelEvent removed in NeoForge â€” use enchantment provider instead)
    // TODO[1.21.1 port]: re-implement onItemAttributeModifierCalculate (NBT->DataComponents migration; AttributeModifier ctor takes ResourceLocation now)
    // TODO[1.21.1 port]: re-implement onAnvilChange (NBT->DataComponents)
}
