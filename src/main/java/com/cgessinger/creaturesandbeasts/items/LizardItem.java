package com.cgessinger.creaturesandbeasts.items;

import com.cgessinger.creaturesandbeasts.util.LizardType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;

// TODO[1.21.1 port]: re-implement variant tagging (LizardType). Old logic stuffed "LizardType" string into
// ItemStack NBT and LizardEntity loaded it back via loadFromNetTag. Migrate to a custom DataComponent.
public class LizardItem extends DeferredSpawnEggItem {
    private final LizardType type;

    public LizardItem(DeferredHolder<EntityType<?>, ? extends EntityType<? extends Mob>> entityTypeSupplier,
                      int primaryColor, int secondaryColor, Properties properties, LizardType type) {
        super(entityTypeSupplier, primaryColor, secondaryColor, properties);
        this.type = type;
    }

    public LizardType getType() {
        return this.type;
    }
}
