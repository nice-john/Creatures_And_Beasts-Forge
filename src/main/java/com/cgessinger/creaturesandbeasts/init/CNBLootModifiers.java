package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class CNBLootModifiers {

    // Despite the legacy file name "nether_bridge_loot_modifier" carried over from the
    // Forge port, the actual target is the cindershell mob's death loot table. The Forge
    // JSON's loot_table_id condition explicitly says cnb:entities/cindershell — this adds
    // a 5/73 chance for 1-3 *additional* shell shards on top of the base 0-4 shards from
    // the entity's vanilla loot table.
    //
    // 1.21 changed the loot table identifier from ResourceLocation to ResourceKey<LootTable>,
    // and the loot path moved from "entities/" to "entities/" (still). We compare via key.
    private static final ResourceKey<LootTable> CINDERSHELL_LOOT = ResourceKey.create(
            net.minecraft.core.registries.Registries.LOOT_TABLE,
            ResourceLocation.fromNamespaceAndPath("cnb", "entities/cindershell"));

    public static void register() {
        // Fabric loot-api v3 in 1.21.1: lambda is (key, tableBuilder, source, registries).
        // We only need key + tableBuilder.
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (CINDERSHELL_LOOT.equals(key)) {
                tableBuilder.withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .when(LootItemRandomChanceCondition.randomChance(5f / 73f))
                                .add(LootItem.lootTableItem(CNBItems.CINDERSHELL_SHELL_SHARD)
                                        .apply(net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
                                                .setCount(UniformGenerator.between(1, 3)))));
            }
        });

        CreaturesAndBeasts.LOGGER.debug("Registered CNB loot modifiers");
    }
}
