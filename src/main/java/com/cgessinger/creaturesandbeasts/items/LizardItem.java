package com.cgessinger.creaturesandbeasts.items;

import com.cgessinger.creaturesandbeasts.entities.LizardEntity;
import com.cgessinger.creaturesandbeasts.util.LizardType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Objects;

/**
 * Variant-tagged spawn-egg item for lizards. Each registered LizardItem (desert, jungle,
 * mushroom, etc.) carries a fixed {@link LizardType} that is forced onto the spawned entity,
 * overriding the biome-based variant pick in {@link LizardEntity#finalizeSpawn}. Net-captured
 * lizards also restore their previous state via {@link DataComponents#CUSTOM_DATA}.
 */
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

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResult.SUCCESS;
        }
        ItemStack itemstack = context.getItemInHand();
        BlockPos clickedPos = context.getClickedPos();
        Direction direction = context.getClickedFace();

        // Adjust spawn position based on collision shape (mirrors vanilla SpawnEggItem.useOn).
        BlockPos spawnPos = level.getBlockState(clickedPos).getCollisionShape(level, clickedPos).isEmpty()
                ? clickedPos
                : clickedPos.relative(direction);

        EntityType<?> entitytype = this.getType(itemstack);
        Entity entity = entitytype.spawn(serverLevel, itemstack, context.getPlayer(), spawnPos,
                MobSpawnType.SPAWN_EGG, true, !Objects.equals(clickedPos, spawnPos) && direction == Direction.UP);

        if (entity instanceof LizardEntity lizard) {
            applyVariantAndStoredData(lizard, itemstack);
            itemstack.shrink(1);
            level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, clickedPos);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        HitResult hit = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (hit.getType() != HitResult.Type.BLOCK) return InteractionResultHolder.pass(itemstack);
        if (!(level instanceof ServerLevel serverLevel)) return InteractionResultHolder.success(itemstack);

        BlockHitResult bhr = (BlockHitResult) hit;
        BlockPos pos = bhr.getBlockPos();
        if (!(level.getBlockState(pos).getBlock() instanceof LiquidBlock)) return InteractionResultHolder.pass(itemstack);
        if (!level.mayInteract(player, pos) || !player.mayUseItemAt(pos, bhr.getDirection(), itemstack)) {
            return InteractionResultHolder.fail(itemstack);
        }

        EntityType<?> entitytype = this.getType(itemstack);
        Entity entity = entitytype.spawn(serverLevel, itemstack, player, pos, MobSpawnType.SPAWN_EGG, false, false);
        if (entity == null) return InteractionResultHolder.pass(itemstack);

        if (entity instanceof LizardEntity lizard) {
            applyVariantAndStoredData(lizard, itemstack);
        }
        if (!player.getAbilities().instabuild) itemstack.shrink(1);
        player.awardStat(Stats.ITEM_USED.get(this));
        level.gameEvent(player, GameEvent.ENTITY_PLACE, player.position());
        return InteractionResultHolder.consume(itemstack);
    }

    /**
     * Forces the lizard's variant from this item's bound type, then restores any saved net data
     * (health, name, AI flags, etc.) from the ItemStack's CUSTOM_DATA component.
     */
    private void applyVariantAndStoredData(LizardEntity lizard, ItemStack stack) {
        if (this.type != null) {
            lizard.setLizardType(this.type);
        }
        CustomData stored = stack.get(DataComponents.CUSTOM_DATA);
        if (stored != null && !stored.isEmpty()) {
            CompoundTag tag = stored.copyTag();
            if (!tag.isEmpty()) {
                lizard.loadFromNetTag(tag);
            }
        }
    }
}
