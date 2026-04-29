package com.cgessinger.creaturesandbeasts.items;

import com.cgessinger.creaturesandbeasts.init.CNBItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.Objects;

public class SporelingSpawnEggItem extends SpawnEggItem {

    public SporelingSpawnEggItem(EntityType<? extends Mob> entityType,
                                  int primaryColor, int secondaryColor,
                                  Properties properties) {
        super(entityType, primaryColor, secondaryColor, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!(level instanceof ServerLevel)) return InteractionResult.SUCCESS;

        ItemStack itemstack   = context.getItemInHand();
        BlockPos  blockpos    = context.getClickedPos();
        Direction direction   = context.getClickedFace();
        BlockState blockstate = level.getBlockState(blockpos);

        if (blockstate.is(Blocks.SPAWNER)) {
            BlockEntity be = level.getBlockEntity(blockpos);
            if (be instanceof SpawnerBlockEntity spawner) {
                BaseSpawner base = spawner.getSpawner();
                EntityType<?> type = this.getType(itemstack.getTag());
                base.setEntityId(type, level, level.getRandom(), blockpos);
                be.setChanged();
                level.sendBlockUpdated(blockpos, blockstate, blockstate, 3);
                itemstack.shrink(1);
                return InteractionResult.CONSUME;
            }
        }

        BlockPos spawnPos = blockstate.getCollisionShape(level, blockpos).isEmpty()
                ? blockpos : blockpos.relative(direction);

        EntityType<?> entityType = this.getType(itemstack.getTag());
        CompoundTag itemTag = itemstack.getOrCreateTag();
        if (itemstack.is(CNBItems.SPORELING_OVERWORLD_EGG)) {
            itemTag.putString("EggType", "Overworld");
        } else if (itemstack.is(CNBItems.SPORELING_NETHER_EGG)) {
            itemTag.putString("EggType", "Nether");
        }

        if (entityType.spawn((ServerLevel) level, itemstack, context.getPlayer(),
                spawnPos, MobSpawnType.SPAWN_EGG, true,
                !Objects.equals(blockpos, spawnPos) && direction == Direction.UP) != null) {
            itemstack.shrink(1);
            level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockpos);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack   = player.getItemInHand(hand);
        HitResult hitresult   = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);

        if (hitresult.getType() != HitResult.Type.BLOCK) return InteractionResultHolder.pass(itemstack);
        if (!(level instanceof ServerLevel)) return InteractionResultHolder.success(itemstack);

        BlockHitResult blockHit = (BlockHitResult) hitresult;
        BlockPos blockpos = blockHit.getBlockPos();
        if (!(level.getBlockState(blockpos).getBlock() instanceof LiquidBlock))
            return InteractionResultHolder.pass(itemstack);

        if (!level.mayInteract(player, blockpos) || !player.mayUseItemAt(blockpos, blockHit.getDirection(), itemstack))
            return InteractionResultHolder.fail(itemstack);

        EntityType<?> entityType = this.getType(itemstack.getTag());
        CompoundTag itemTag = itemstack.getOrCreateTag();
        if (itemstack.is(CNBItems.SPORELING_OVERWORLD_EGG)) {
            itemTag.putString("EggType", "Overworld");
        } else if (itemstack.is(CNBItems.SPORELING_NETHER_EGG)) {
            itemTag.putString("EggType", "Nether");
        }

        if (entityType.spawn((ServerLevel) level, itemstack, player,
                blockpos, MobSpawnType.SPAWN_EGG, false, false) == null)
            return InteractionResultHolder.pass(itemstack);

        if (!player.getAbilities().instabuild) itemstack.shrink(1);
        player.awardStat(Stats.ITEM_USED.get(this));
        level.gameEvent(player, GameEvent.ENTITY_PLACE, player.position());
        return InteractionResultHolder.consume(itemstack);
    }
}
