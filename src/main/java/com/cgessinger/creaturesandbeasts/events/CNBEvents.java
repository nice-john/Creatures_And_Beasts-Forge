package com.cgessinger.creaturesandbeasts.events;

import com.cgessinger.creaturesandbeasts.entities.CactemEntity;
import com.cgessinger.creaturesandbeasts.entities.CindershellEntity;
import com.cgessinger.creaturesandbeasts.entities.EndWhaleEntity;
import com.cgessinger.creaturesandbeasts.entities.LilytadEntity;
import com.cgessinger.creaturesandbeasts.entities.LittleGrebeEntity;
import com.cgessinger.creaturesandbeasts.entities.LizardEntity;
import com.cgessinger.creaturesandbeasts.entities.MinipadEntity;
import com.cgessinger.creaturesandbeasts.entities.SporelingEntity;
import com.cgessinger.creaturesandbeasts.entities.YetiEntity;
import com.cgessinger.creaturesandbeasts.init.CNBEntityTypes;
import com.cgessinger.creaturesandbeasts.init.CNBItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class CNBEvents {

    @SubscribeEvent
    public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(CNBEntityTypes.CINDERSHELL.get(), CindershellEntity.createAttributes().build());
        event.put(CNBEntityTypes.SPORELING.get(), SporelingEntity.createAttributes().build());
        event.put(CNBEntityTypes.LITTLE_GREBE.get(), LittleGrebeEntity.createAttributes().build());
        event.put(CNBEntityTypes.LILYTAD.get(), LilytadEntity.createAttributes().build());
        event.put(CNBEntityTypes.LIZARD.get(), LizardEntity.createAttributes().build());
        event.put(CNBEntityTypes.YETI.get(), YetiEntity.createAttributes().build());
        event.put(CNBEntityTypes.MINIPAD.get(), MinipadEntity.createAttributes().build());
        event.put(CNBEntityTypes.END_WHALE.get(), EndWhaleEntity.createAttributes().build());
        event.put(CNBEntityTypes.CACTEM.get(), CactemEntity.createAttributes().build());
    }

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
