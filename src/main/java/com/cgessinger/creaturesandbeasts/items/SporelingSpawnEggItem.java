package com.cgessinger.creaturesandbeasts.items;

import com.cgessinger.creaturesandbeasts.entities.SporelingEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
 * Egg-type-tagged spawn-egg item for sporelings. Both registered eggs (Overworld / Nether)
 * spawn the same {@link SporelingEntity} type, but force a friendly-vs-hostile variant via
 * {@link SporelingEntity#applyEggType(String, net.minecraft.world.level.ServerLevelAccessor)}.
 */
public class SporelingSpawnEggItem extends DeferredSpawnEggItem {
    private final String eggType;

    public SporelingSpawnEggItem(final DeferredHolder<EntityType<?>, ? extends EntityType<? extends Mob>> entityTypeSupplier,
                                 final int primaryColor, final int secondaryColor,
                                 final Properties properties, final String eggType) {
        super(entityTypeSupplier, primaryColor, secondaryColor, properties);
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

        EntityType<?> entitytype = this.getType(itemstack);
        Entity entity = entitytype.spawn(serverLevel, itemstack, context.getPlayer(), spawnPos,
                MobSpawnType.SPAWN_EGG, true, !Objects.equals(clickedPos, spawnPos) && direction == Direction.UP);

        if (entity instanceof SporelingEntity sporeling) {
            sporeling.applyEggType(this.eggType, serverLevel);
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

        if (entity instanceof SporelingEntity sporeling) {
            sporeling.applyEggType(this.eggType, serverLevel);
        }
        if (!player.getAbilities().instabuild) itemstack.shrink(1);
        player.awardStat(Stats.ITEM_USED.get(this));
        level.gameEvent(player, GameEvent.ENTITY_PLACE, player.position());
        return InteractionResultHolder.consume(itemstack);
    }
}
