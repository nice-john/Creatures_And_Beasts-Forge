package com.cgessinger.creaturesandbeasts.items;

import com.cgessinger.creaturesandbeasts.entities.SporelingEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.item.SpawnEggItem;

import java.util.Objects;

/**
 * Egg-type-tagged spawn-egg item for sporelings. Both registered eggs (Overworld / Nether)
 * spawn the same {@link SporelingEntity} type, but force a friendly-vs-hostile variant via
 * {@link SporelingEntity#applyEggType(String, net.minecraft.world.level.ServerLevelAccessor)}.
 */
public class SporelingSpawnEggItem extends SpawnEggItem {
    private final String eggType;

    public SporelingSpawnEggItem(final Properties properties, final String eggType) {
        super(properties);
        this.eggType = eggType;
    }

    public String getEggType() {
        return this.eggType;
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

        BlockPos spawnPos = level.getBlockState(clickedPos).getCollisionShape(level, clickedPos).isEmpty()
                ? clickedPos
                : clickedPos.relative(direction);

        EntityType<?> entitytype = getType(itemstack);
        Entity entity = entitytype.spawn(serverLevel, itemstack, context.getPlayer(), spawnPos,
                EntitySpawnReason.SPAWN_ITEM_USE, true, !Objects.equals(clickedPos, spawnPos) && direction == Direction.UP);

        if (entity instanceof SporelingEntity sporeling) {
            sporeling.applyEggType(this.eggType, serverLevel);
            itemstack.shrink(1);
            level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, clickedPos);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        HitResult hit = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (hit.getType() != HitResult.Type.BLOCK) return InteractionResult.PASS;
        if (!(level instanceof ServerLevel serverLevel)) return InteractionResult.SUCCESS;

        BlockHitResult bhr = (BlockHitResult) hit;
        BlockPos pos = bhr.getBlockPos();
        if (!(level.getBlockState(pos).getBlock() instanceof LiquidBlock)) return InteractionResult.PASS;
        if (!level.mayInteract(player, pos) || !player.mayUseItemAt(pos, bhr.getDirection(), itemstack)) {
            return InteractionResult.FAIL;
        }

        EntityType<?> entitytype = getType(itemstack);
        Entity entity = entitytype.spawn(serverLevel, itemstack, player, pos, EntitySpawnReason.SPAWN_ITEM_USE, false, false);
        if (entity == null) return InteractionResult.PASS;

        if (entity instanceof SporelingEntity sporeling) {
            sporeling.applyEggType(this.eggType, serverLevel);
        }
        if (!player.getAbilities().instabuild) itemstack.shrink(1);
        player.awardStat(Stats.ITEM_USED.get(this));
        level.gameEvent(player, GameEvent.ENTITY_PLACE, player.position());
        return InteractionResult.CONSUME;
    }
}
