package com.cgessinger.creaturesandbeasts.entities.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

/** Copy of the vanilla goal that was removed from the game; kept so the mobs behave as before. */
public class TryFindWaterGoal extends Goal {
    private final PathfinderMob mob;

    public TryFindWaterGoal(PathfinderMob mob) {
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        return this.mob.onGround() && !this.mob.level().getFluidState(this.mob.blockPosition()).is(FluidTags.WATER);
    }

    @Override
    public void start() {
        BlockPos found = null;

        for (BlockPos pos : BlockPos.betweenClosed(
                Mth.floor(this.mob.getX() - 2.0D), Mth.floor(this.mob.getY() - 2.0D), Mth.floor(this.mob.getZ() - 2.0D),
                Mth.floor(this.mob.getX() + 2.0D), this.mob.getBlockY(), Mth.floor(this.mob.getZ() + 2.0D))) {
            if (this.mob.level().getFluidState(pos).is(FluidTags.WATER)) {
                found = pos;
                break;
            }
        }

        if (found != null) {
            this.mob.getMoveControl().setWantedPosition(found.getX(), found.getY(), found.getZ(), 1.0D);
        }
    }
}
