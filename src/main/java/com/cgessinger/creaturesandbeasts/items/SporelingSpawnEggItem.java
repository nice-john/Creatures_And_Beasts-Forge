package com.cgessinger.creaturesandbeasts.items;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;

// TODO[1.21.1 port]: re-implement variant tagging (Overworld/Nether). Old logic stuffed an "EggType" string
// in the ItemStack NBT and SporelingEntity.finalizeSpawn read it back. Migrate to a custom DataComponent.
public class SporelingSpawnEggItem extends DeferredSpawnEggItem {
    public SporelingSpawnEggItem(final DeferredHolder<EntityType<?>, ? extends EntityType<? extends Mob>> entityTypeSupplier,
                                 final int primaryColor, final int secondaryColor, final Properties properties) {
        super(entityTypeSupplier, primaryColor, secondaryColor, properties);
    }
}
