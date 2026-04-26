package com.cgessinger.creaturesandbeasts.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

// TODO[1.21.1 port]: re-implement imbued-ticks via DataComponent (item-stack capabilities removed in NeoForge).
// Old behavior: dipping in lava upgraded the sword to a higher imbue tier for 400 ticks; tier countdown ticked
// on inventoryTick and downgraded back. Port flow: register a DataComponentType<Integer> for imbuedTicks,
// retrofit inventoryTick + use(lava-dip) to read/write that component.
public class CinderSwordItem extends SwordItem {
    private final int imbueLevel;

    public CinderSwordItem(Tier tier, int imbueLevel, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, properties.attributes(SwordItem.createAttributes(tier, attackDamageModifier, attackSpeedModifier)));
        this.imbueLevel = imbueLevel;
    }

    public int getImbueLevel() {
        return this.imbueLevel;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity targetEntity, LivingEntity attackingEntity) {
        if (this.imbueLevel > 0) {
            targetEntity.igniteForSeconds(2 * this.imbueLevel);
        }
        return super.hurtEnemy(stack, targetEntity, attackingEntity);
    }
}
