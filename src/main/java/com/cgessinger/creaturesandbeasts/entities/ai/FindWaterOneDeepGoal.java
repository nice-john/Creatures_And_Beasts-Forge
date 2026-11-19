package com.cgessinger.creaturesandbeasts.entities.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.BlockGetter;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

/**
 * Walks to the nearest one-deep patch of water. Vanilla's TryFindWaterGoal was removed in 26.x,
 * so this is a standalone goal with the same behaviour as the old subclass.
 */
public class FindWaterOneDeepGoal extends Goal {
    private final PathfinderMob creature;

    public FindWaterOneDeepGoal(PathfinderMob creature) {
        this.creature = creature;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public void start() {
        BlockPos blockpos = lookForWaterOneDeep(this.creature.level(), this.creature, 16);
        if (blockpos != null) {
            this.creature.getNavigation().moveTo(blockpos.getX(), blockpos.getY(), blockpos.getZ(), this.creature.isInWater() ? 1.5F : 1.0F);
        }
    }

    @Override
    public boolean canUse() {
        BlockPos pos = this.creature.blockPosition();
        boolean isValidPos = checkValidPos(pos);
        return this.creature.onGround() && !isValidPos;
    }

    private boolean checkValidPos(BlockPos pos) {
        return this.creature.level().getFluidState(pos).is(FluidTags.WATER) && this.creature.onGround() && this.creature.level().getBlockState(pos.above()).isAir();
    }

    @Nullable
    protected BlockPos lookForWaterOneDeep(BlockGetter blockGetter, Entity entity, int horizontalRange) {
        BlockPos origin = entity.blockPosition();
        if (!blockGetter.getBlockState(origin).getCollisionShape(blockGetter, origin).isEmpty()) {
            return null;
        }
        // withinManhattan iterates outwards from the origin, so the first match is the closest.
        for (BlockPos pos : BlockPos.withinManhattan(origin, horizontalRange)) {
            if (Math.abs(pos.getY() - origin.getY()) <= 10
                    && blockGetter.getFluidState(pos).is(FluidTags.WATER)
                    && blockGetter.getBlockState(pos.below()).isSolid()
                    && blockGetter.getBlockState(pos.above()).isAir()) {
                return pos.immutable();
            }
        }
        return null;
    }
}