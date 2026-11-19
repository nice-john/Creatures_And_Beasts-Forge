package com.cgessinger.creaturesandbeasts.util;

import net.minecraft.core.BlockPos;

import java.util.Optional;
import java.util.function.Predicate;

public final class BlockSearch {
    private BlockSearch() {}

    /** Closest position (by Manhattan distance) inside the given box that matches the filter. */
    public static Optional<BlockPos> findClosestMatch(BlockPos origin, int horizontalRange, int verticalRange, Predicate<BlockPos> filter) {
        BlockPos best = null;
        int bestDist = Integer.MAX_VALUE;
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        for (int dy = -verticalRange; dy <= verticalRange; dy++) {
            for (int dx = -horizontalRange; dx <= horizontalRange; dx++) {
                for (int dz = -horizontalRange; dz <= horizontalRange; dz++) {
                    int dist = Math.abs(dx) + Math.abs(dy) + Math.abs(dz);
                    if (dist >= bestDist) continue;
                    cursor.set(origin.getX() + dx, origin.getY() + dy, origin.getZ() + dz);
                    if (filter.test(cursor)) {
                        best = cursor.immutable();
                        bestDist = dist;
                    }
                }
            }
        }
        return Optional.ofNullable(best);
    }
}
